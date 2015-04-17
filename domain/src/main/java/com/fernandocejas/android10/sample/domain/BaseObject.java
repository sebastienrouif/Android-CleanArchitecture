package com.fernandocejas.android10.sample.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseObject {

    public String toString() {
        return new Gson().toJson(this);
    }

    public String toPrettyString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
