package com.github.avpcm.troyvoice.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Chat {

    @JsonProperty("id")
    @SerializedName("id")
    public long id;

    @JsonProperty("first_name")
    @SerializedName("first_name")
    public String firstName;

    @JsonProperty("last_name")
    @SerializedName("last_name")
    public String lastName;

    @JsonProperty("user_name")
    @SerializedName("user_name")
    public String userName;

    @JsonProperty("type")
    @SerializedName("type")
    public String type;
}
