package com.github.avpcm.troyvoice.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Message {

    @JsonProperty("message_id")
    @SerializedName("message_id")
    public long messageId;

    @JsonProperty("from")
    @SerializedName("from")
    public User from;

    @JsonProperty("chat")
    @SerializedName("chat")
    public Chat chat;

    @JsonProperty("date")
    @SerializedName("date")
    public long date;

    @JsonProperty("text")
    @SerializedName("text")
    public String text;

    @JsonProperty("voice")
    @SerializedName("voice")
    public Voice voice;
}
