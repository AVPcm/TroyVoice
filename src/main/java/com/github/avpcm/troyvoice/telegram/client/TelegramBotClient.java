package com.github.avpcm.troyvoice.telegram.client;

import com.github.avpcm.troyvoice.telegram.model.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class TelegramBotClient {

    private final String botToken;

    private final HttpClient client;

    public TelegramBotClient(String botToken) {
        this(botToken, null);
    }

    public TelegramBotClient(String botToken, InetSocketAddress proxy) {
        this.botToken = botToken;

        HttpClient.Builder builder = HttpClient.newBuilder();
        if (proxy != null)
            builder.proxy(ProxySelector.of(proxy));

        client = builder.connectTimeout(Duration.ofSeconds(10)).build();
    }

    public Update[] getUpdates() throws IOException, InterruptedException {
        URI uri = URI.create("https://api.telegram.org/bot" + botToken + "/getUpdates");

        var request = HttpRequest.newBuilder(uri).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GetUpdatesResponse body = new Gson().fromJson(response.body(), GetUpdatesResponse.class);

        return body.result;
    }

    public Path downloadVoiceMessage(String fileId, Path downloadDirectory) throws IOException, InterruptedException {
        FileInfo fileInfo = getFileInfo(fileId);
        return downloadFile(fileInfo, downloadDirectory);
    }

    private FileInfo getFileInfo(String fileId) throws IOException, InterruptedException {
        URI uri = URI.create("https://api.telegram.org/bot" + botToken + "/getFile?file_id=" + fileId);

        var request = HttpRequest.newBuilder(uri).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        FileInfoResponse body = new Gson().fromJson(response.body(), FileInfoResponse.class);

        return body.result;
    }

    private Path downloadFile(FileInfo fileInfo, Path downloadDir) throws IOException, InterruptedException {
        URI uri = URI.create("https://api.telegram.org/file/bot" + botToken + "/" + fileInfo.filePath);
        Path result = Paths.get(downloadDir.toString(), fileInfo.fileUniqueId);

        var request = HttpRequest.newBuilder(uri).build();
        client.send(request, HttpResponse.BodyHandlers.ofFile(result));

        return result;
    }
}
