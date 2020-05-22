package com.github.avpcm.troyvoice.telegram.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    public long id;

    @SerializedName("is_bot")
    public boolean isBot;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("user_name")
    public String userName;

    @SerializedName("language_code")
    public String languageCode;
}
