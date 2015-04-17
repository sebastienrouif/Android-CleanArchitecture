package com.fernandocejas.android10.sample.data.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseObjectEntity {

    public String toString() {
        return new Gson().toJson(this);
    }

    public String toPrettyString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
