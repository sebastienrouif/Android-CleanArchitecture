/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain;

/**
 * Class that represents a Movie in the domain layer.
 */
public class Movie {

  private final int movieId;

  public Movie(int movieId) {
    this.movieId = movieId;
  }

  private String coverUrl;
  private String fullName;
  private String email;
  private String description;
  private int followers;

  public int getMovieId() {
    return movieId;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** Movie Details *****\n");
    stringBuilder.append("id=" + this.getMovieId() + "\n");
    stringBuilder.append("cover url=" + this.getCoverUrl() + "\n");
    stringBuilder.append("fullname=" + this.getFullName() + "\n");
    stringBuilder.append("email=" + this.getEmail() + "\n");
    stringBuilder.append("description=" + this.getDescription() + "\n");
    stringBuilder.append("followers=" + this.getFollowers() + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
