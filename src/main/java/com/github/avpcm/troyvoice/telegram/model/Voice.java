package com.github.avpcm.troyvoice.telegram.model;

import com.google.gson.annotations.SerializedName;

public class Voice {

    @SerializedName("duration")
    public long duration;

    @SerializedName("mime_type")
    public String mimeType;

    @SerializedName("file_id")
    public String fileId;

    @SerializedName("file_unique_id")
    public String fileUniqueId;

    @SerializedName("file_size")
    public long fileSize;
}