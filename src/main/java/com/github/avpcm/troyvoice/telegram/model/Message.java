package com.github.avpcm.troyvoice.telegram.model;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("message_id")
    public long messageId;

    @SerializedName("from")
    public User from;

    @SerializedName("chat")
    public Chat chat;

    @SerializedName("date")
    public long date;

    @SerializedName("text")
    public String text;

    @SerializedName("voice")
    public Voice voice;
}
