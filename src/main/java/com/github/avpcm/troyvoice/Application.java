package com.github.avpcm.troyvoice;

import com.github.avpcm.troyvoice.telegram.client.TelegramBotClient;
import com.github.avpcm.troyvoice.telegram.model.BaseResponse;
import com.github.avpcm.troyvoice.telegram.model.Update;
import com.github.avpcm.troyvoice.telegram.model.Voice;
import com.google.gson.GsonBuilder;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Application {

    public static void main(String[] args) throws Exception {
        Properties settings = new Properties();
        settings.load(Application.class.getClassLoader().getResourceAsStream("credentials.properties"));
        String botToken = settings.getProperty("bot.token");

        InetSocketAddress proxy = new InetSocketAddress("82.165.244.38", 3128);
        TelegramBotClient botClient = new TelegramBotClient(botToken, proxy);

        Update[] updates = botClient.getUpdates();

        List<Voice> voices = Stream.of(updates)
                .filter(upd -> upd.message.voice != null)
                .map(upd -> upd.message.voice)
                .collect(toList());

        Path downloadDir = Paths.get("c:\\download");

        List<Path> downloads = new ArrayList<>();
        for (Voice voice: voices)
            downloads.add(botClient.downloadVoiceMessage(voice.fileId, downloadDir));

        System.out.println("скачены голосовые сообщения: " + downloads);
    }
}
