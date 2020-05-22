package com.github.avpcm.troyvoice.telegram.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("ok")
    public boolean ok;

    @SerializedName("result")
    public T result;
}
