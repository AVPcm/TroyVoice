package com.github.avpcm.troyvoice.telegram.model;

import com.google.gson.annotations.SerializedName;

public class Update {

    @SerializedName("update_id")
    public long updateId;

    @SerializedName("message")
    public Message message;
}
