package com.github.avpcm.troyvoice.telegram.model;

import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("id")
    public long id;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("user_name")
    public String userName;

    @SerializedName("type")
    public String type;
}
