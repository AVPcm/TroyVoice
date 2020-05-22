package com.github.avpcm.troyvoice.wit.model;

import com.google.gson.annotations.SerializedName;

public class TextResponse {

    @SerializedName("code")
    public String code;

    @SerializedName("error")
    public String error;

    @SerializedName("text")
    public String text;
}
