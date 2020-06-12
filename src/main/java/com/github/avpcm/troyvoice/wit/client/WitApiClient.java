package com.github.avpcm.troyvoice.wit.client;

import com.github.avpcm.troyvoice.wit.model.TextResponse;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class WitApiClient {

    private static final String POST_SPEECH_URI = "https://api.wit.ai/speech";

    private String appToken;

    private HttpClient client;

    public WitApiClient(String appToken) {
        this.appToken = appToken;
        this.client = HttpClient.newBuilder().build();
    }

    public TextResponse postSpeech(Path wav) throws Exception {
        var request = HttpRequest.newBuilder(URI.create(POST_SPEECH_URI))
                .setHeader("Authorization", "Bearer " + appToken)
                .setHeader("Content-Type", "audio/wav")
                .POST(HttpRequest.BodyPublishers.ofFile(wav))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new Gson().fromJson(response.body(), TextResponse.class);
    }
}
