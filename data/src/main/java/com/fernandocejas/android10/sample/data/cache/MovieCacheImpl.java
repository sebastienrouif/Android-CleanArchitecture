/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import android.content.Context;

import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;
import com.fernandocejas.android10.sample.data.exception.MovieNotFoundException;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;

import org.joda.time.DateTime;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link MovieCache} implementation.
 */
@Singleton
public class MovieCacheImpl implements MovieCache {

    private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_MOVIE_FILE_NAME = "movie_";
    private static final String DEFAULT_PAGINATED_MOVIE_FILE_NAME = "paginated_movie_";

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link MovieCacheImpl}.
     *
     * @param context              A
     * @param movieCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager          {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public MovieCacheImpl(Context context, JsonSerializer movieCacheSerializer,
                          FileManager fileManager, ThreadExecutor executor) {
        if (context == null || movieCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = movieCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    /**
     * {@inheritDoc}
     *
     * @param movieId  The movie id to retrieve data.
     * @param callback The {@link MovieCacheCallback} to notify the client.
     */
    @Override
    public synchronized void getMovie(int movieId, MovieCacheCallback callback) {
        File movieEntitiyFile = this.buildMovieFile(movieId);
        String fileContent = this.fileManager.readFileContent(movieEntitiyFile);
        MovieEntity movieEntity = this.serializer.deserialize(fileContent, MovieEntity.class);

        if (movieEntity != null) {
            callback.onMovieEntityLoaded(movieEntity);
        } else {
            callback.onError(new MovieNotFoundException());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param movieEntity Element to insert in the cache.
     */
    @Override
    public synchronized void putMovie(MovieEntity movieEntity) {
        if (movieEntity != null) {
            File movieEntitiyFile = this.buildMovieFile(movieEntity.getId());
            if (!isMovieCached(movieEntity.getId())) {
                String jsonString = this.serializer.serialize(movieEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, movieEntitiyFile,
                        jsonString));
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param movieId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    @Override
    public boolean isMovieCached(int movieId) {
        File movieEntitiyFile = this.buildMovieFile(movieId);
        return this.fileManager.exists(movieEntitiyFile);
    }

    /**
     * {@inheritDoc}
     *
     * @return true, the cache is expired, otherwise false.
     */
    @Override
    public boolean isMovieExpired() {
        // movie never expires
        return false;
    }

    @Override
    public void getPaginatedMovies(int page, PaginatedMovieCacheCallback callback) {
        File paginatedMovieEntitiyFile = this.buildPaginatedMovieFile(page);
        String fileContent = this.fileManager.readFileContent(paginatedMovieEntitiyFile);
        PaginatedMoviesEntity paginatedMoviesEntity = this.serializer.deserialize(fileContent, PaginatedMoviesEntity.class);

        if (paginatedMoviesEntity != null) {
            callback.onMovieEntityLoaded(paginatedMoviesEntity);
        } else {
            callback.onError(new MovieNotFoundException());
        }
    }

    @Override
    public void putPaginatedMovie(PaginatedMoviesEntity paginatedMoviesEntity) {
        if (paginatedMoviesEntity != null) {
            File movieEntitiyFile = buildPaginatedMovieFile(paginatedMoviesEntity.getPage());
            String jsonString = serializer.serialize(paginatedMoviesEntity);
            executeAsynchronously(new CacheWriter(fileManager, movieEntitiyFile, jsonString));
            setLastCacheListUpdateDate();
        }
    }

    @Override
    public boolean isMoviePageCached(int page) {
        File movieEntitiyFile = this.buildPaginatedMovieFile(page);
        return fileManager.exists(movieEntitiyFile);
    }

    @Override
    public boolean isMoviePageExpired() {
        long currentDate = getTodayInMillis();
        long lastCacheListUpdateDate = getLastCacheListUpdateDate();
        boolean expired = currentDate > lastCacheListUpdateDate;

        if (expired) {
            evictAll();
        }

        return expired;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param movieId The id movie to build the file.
     * @return A valid file.
     */
    private File buildMovieFile(int movieId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_MOVIE_FILE_NAME);
        fileNameBuilder.append(movieId);

        return new File(fileNameBuilder.toString());
    }

    private File buildPaginatedMovieFile(int page) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_PAGINATED_MOVIE_FILE_NAME);
        fileNameBuilder.append(page);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }


    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheListUpdateDate() {
        long currentMillis = getTodayInMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }


    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheListUpdateDate() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    private long getTodayInMillis() {
        DateTime midnight = new DateTime().withTimeAtStartOfDay();
        return midnight.getMillis();
    }

}
