package com.github.avpcm.troyvoice.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Update {

    @JsonProperty("update_id")
    @SerializedName("update_id")
    public long updateId;

    @JsonProperty("message")
    @SerializedName("message")
    public Message message;
}
