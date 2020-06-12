package com.github.avpcm.troyvoice.wit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class TextResponse {

    @JsonProperty("code")
    @SerializedName("code")
    public String code;

    @JsonProperty("error")
    @SerializedName("error")
    public String error;

    @JsonProperty("text")
    @SerializedName("text")
    public String text;
}
