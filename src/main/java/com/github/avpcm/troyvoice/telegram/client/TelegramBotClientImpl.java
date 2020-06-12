package com.github.avpcm.troyvoice.telegram.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avpcm.troyvoice.telegram.model.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.codec.json.Jackson2JsonDecoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TelegramBotClientImpl implements TelegramBotClient {

    private final String botToken;

    private final HttpClient client;

    @Value("${ogg.dir}")
    private Path downloadDir;

    public TelegramBotClientImpl(String botToken) {
        this(botToken, null);
    }

    public TelegramBotClientImpl(String botToken, InetSocketAddress proxy) {
        this.botToken = botToken;

        HttpClient.Builder builder = HttpClient.newBuilder();
        if (proxy != null)
            builder.proxy(ProxySelector.of(proxy));

        client = builder.build();
    }

    @Override
    public Path downloadVoiceMessage(String fileId) throws IOException, InterruptedException {
        FileInfo fileInfo = getFileInfo(fileId);
        return downloadFile(fileInfo, downloadDir);
    }

    @Override
    public Message postMessage(long chatId, String message) throws IOException, InterruptedException {
        String text = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String uri = String.format(POST_MESSAGE_TEMPLATE, botToken, chatId, text);

        var request = HttpRequest.newBuilder(URI.create(uri)).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Message body = new Gson().fromJson(response.body(), Message.class);

        return body;
    }

    private FileInfo getFileInfo(String fileId) throws IOException, InterruptedException {
        String uri = String.format(GET_FILE_TEMPLATE, botToken, fileId);

        var request = HttpRequest.newBuilder(URI.create(uri)).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        FileInfoResponse body = new Gson().fromJson(response.body(), FileInfoResponse.class);

        return body.result;
    }

    private Path downloadFile(FileInfo fileInfo, Path downloadDir) throws IOException, InterruptedException {
        String uri = String.format(DOWNLOAD_FILE_TEMPLATE, botToken, fileInfo.filePath);
        Path result = Paths.get(downloadDir.toString(), fileInfo.fileId + ".ogg");

        var request = HttpRequest.newBuilder(URI.create(uri)).build();
        client.send(request, HttpResponse.BodyHandlers.ofFile(result));

        return result;
    }
}
