
package com.fernandocejas.android10.sample.domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PaginatedMovies extends BaseObject {

    @SerializedName("dates")
    @Expose
    private Dates dates;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public PaginatedMovies withDates(Dates dates) {
        this.dates = dates;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PaginatedMovies withPage(int page) {
        this.page = page;
        return this;
    }

    public List<Movie> getMovies() {
        return movies;
    }


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public PaginatedMovies withResults(List<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PaginatedMovies withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }


    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public PaginatedMovies withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

}
