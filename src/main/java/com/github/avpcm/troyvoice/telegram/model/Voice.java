package com.github.avpcm.troyvoice.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Voice {

    @JsonProperty("duration")
    @SerializedName("duration")
    public long duration;

    @JsonProperty("mime_type")
    @SerializedName("mime_type")
    public String mimeType;

    @JsonProperty("file_id")
    @SerializedName("file_id")
    public String fileId;

    @JsonProperty("file_unique_id")
    @SerializedName("file_unique_id")
    public String fileUniqueId;

    @JsonProperty("file_size")
    @SerializedName("file_size")
    public long fileSize;
}
