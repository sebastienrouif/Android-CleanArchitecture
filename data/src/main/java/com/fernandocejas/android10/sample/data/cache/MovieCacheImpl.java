/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import android.content.Context;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.exception.MovieNotFoundException;
import com.fernandocejas.android10.sample.domain.Movie;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
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

  private static final String DEFAULT_FILE_NAME = "movie_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final JsonSerializer serializer;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link MovieCacheImpl}.
   *
   * @param context A
   * @param movieCacheSerializer {@link JsonSerializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
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
   * @param movieId The movie id to retrieve data.
   * @param callback The {@link MovieCacheCallback} to notify the client.
   */
  @Override public synchronized void get(int movieId, MovieCacheCallback callback) {
    File movieEntitiyFile = this.buildFile(movieId);
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
  @Override public synchronized void put(MovieEntity movieEntity) {
    if (movieEntity != null) {
      File movieEntitiyFile = this.buildFile(movieEntity.getId());
      if (!isCached(movieEntity.getId())) {
        String jsonString = this.serializer.serialize(movieEntity);
        this.executeAsynchronously(new CacheWriter(this.fileManager, movieEntitiyFile,
            jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param movieId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  @Override public boolean isCached(int movieId) {
    File movieEntitiyFile = this.buildFile(movieId);
    return this.fileManager.exists(movieEntitiyFile);
  }

  /**
   * {@inheritDoc}
   *
   * @return true, the cache is expired, otherwise false.
   */
  @Override public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  /**
   * {@inheritDoc}
   */
  @Override public synchronized void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @param movieId The id movie to build the file.
   * @return A valid file.
   */
  private File buildFile(int movieId) {
    StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);
    fileNameBuilder.append(movieId);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
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

    @Override public void run() {
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

    @Override public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}
