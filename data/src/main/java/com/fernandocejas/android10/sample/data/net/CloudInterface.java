package com.fernandocejas.android10.sample.data.net;


import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.fernandocejas.android10.sample.data.entity.PaginatedMoviesEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CloudInterface {

    @GET("/movie/now_playing")
    PaginatedMoviesEntity getNowPlaying(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);

    @GET("/movie/{id}")
    MovieEntity getMovieDetail(@Query("api_key") String apiKey, @Path("id") int id);

}
