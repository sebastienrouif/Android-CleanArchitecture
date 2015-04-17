/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Movie Entity used in the data layer.
 */
public class MovieEntity extends BaseObjectEntity {

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

//    @SerializedName("belongs_to_collection")
//    @Expose
//    private BelongsToCollection belongsToCollection;

    @SerializedName("budget")
    @Expose
    private int budget;

//    @SerializedName("genres")
//    @Expose
//    private List<Genre> genres = new ArrayList<Genre>();

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private float popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

//    @SerializedName("production_companies")
//    @Expose
//    private List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();

//    @SerializedName("production_countries")
//    @Expose
//    private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("revenue")
    @Expose
    private int revenue;

    @SerializedName("runtime")
    @Expose
    private int runtime;

//    @SerializedName("spoken_languages")
//    @Expose
//    private List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

//    public BelongsToCollection getBelongsToCollection() {
//        return belongsToCollection;
//    }
//
//    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
//        this.belongsToCollection = belongsToCollection;
//    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

//    public List<Genre> getGenres() {
//        return genres;
//    }
//
//    public void setGenres(List<Genre> genres) {
//        this.genres = genres;
//    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

//    public List<ProductionCompany> getProductionCompanies() {
//        return productionCompanies;
//    }
//
//    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
//        this.productionCompanies = productionCompanies;
//    }
//
//    public List<ProductionCountry> getProductionCountries() {
//        return productionCountries;
//    }
//
//    public void setProductionCountries(List<ProductionCountry> productionCountries) {
//        this.productionCountries = productionCountries;
//    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

//    public List<SpokenLanguage> getSpokenLanguages() {
//        return spokenLanguages;
//    }
//
//    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
//        this.spokenLanguages = spokenLanguages;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
