
package com.fernandocejas.android10.sample.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates extends BaseObject {

    @Expose
    @SerializedName("minimum")
    private String minimum;

    @Expose
    @SerializedName("maximum")
    private String maximum;

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public Dates withMinimum(String minimum) {
        this.minimum = minimum;
        return this;
    }

    public String getMaximum() {
        return maximum;
    }


    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public Dates withMaximum(String maximum) {
        this.maximum = maximum;
        return this;
    }

}
