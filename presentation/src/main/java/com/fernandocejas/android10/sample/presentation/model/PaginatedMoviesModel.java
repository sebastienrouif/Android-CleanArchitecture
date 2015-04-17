
package com.fernandocejas.android10.sample.presentation.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PaginatedMoviesModel extends BaseObject {

    @SerializedName("dates")
    @Expose
    private DatesModel datesModel;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<MovieModel> movies = new ArrayList<MovieModel>();

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    public DatesModel getDatesModel() {
        return datesModel;
    }

    public void setDatesModel(DatesModel datesModel) {
        this.datesModel = datesModel;
    }

    public PaginatedMoviesModel withDates(DatesModel datesModel) {
        this.datesModel = datesModel;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PaginatedMoviesModel withPage(int page) {
        this.page = page;
        return this;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }


    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    public PaginatedMoviesModel withResults(List<MovieModel> movies) {
        this.movies = movies;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PaginatedMoviesModel withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }


    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public PaginatedMoviesModel withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

}
