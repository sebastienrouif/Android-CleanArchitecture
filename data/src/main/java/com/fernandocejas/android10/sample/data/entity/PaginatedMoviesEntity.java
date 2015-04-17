
package com.fernandocejas.android10.sample.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PaginatedMoviesEntity extends BaseObjectEntity {

    @SerializedName("dates")
    @Expose
    private DatesEntity datesEntity;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<MovieEntity> movies = new ArrayList<MovieEntity>();

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    public DatesEntity getDatesEntity() {
        return datesEntity;
    }

    public void setDatesEntity(DatesEntity datesEntity) {
        this.datesEntity = datesEntity;
    }

    public PaginatedMoviesEntity withDates(DatesEntity datesEntity) {
        this.datesEntity = datesEntity;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PaginatedMoviesEntity withPage(int page) {
        this.page = page;
        return this;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }


    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    public PaginatedMoviesEntity withResults(List<MovieEntity> movies) {
        this.movies = movies;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PaginatedMoviesEntity withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }


    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public PaginatedMoviesEntity withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

}
