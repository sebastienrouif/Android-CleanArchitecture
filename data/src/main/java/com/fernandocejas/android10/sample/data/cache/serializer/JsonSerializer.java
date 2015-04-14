/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache.serializer;

import com.fernandocejas.android10.sample.data.entity.MovieEntity;
import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class movie as Serializer/Deserializer for movie entities.
 */
@Singleton
public class JsonSerializer {

  private final Gson gson = new Gson();

  @Inject
  public JsonSerializer() {}

  /**
   * Serialize an object to Json.
   *
   * @param object {@link Object} to serialize.
   */
  public String serialize(Object object) {
    String jsonString = gson.toJson(object, object.getClass());
    return jsonString;
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param jsonString A json string to deserialize.
   * @return {@link MovieEntity}
   */
  public <T> T  deserialize(String jsonString, Class<T> classOfT) {
    return (T) gson.fromJson(jsonString, classOfT);
  }
}
