package com.github.avpcm.troyvoice;

import com.github.avpcm.troyvoice.service.OggToWavConverter;
import com.github.avpcm.troyvoice.telegram.client.TelegramBotClient;
import com.github.avpcm.troyvoice.telegram.model.Update;
import com.github.avpcm.troyvoice.telegram.model.Voice;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Application {

    public final static OperationSystem OS = System.getProperty("os.name").contains("indows")
            ? OperationSystem.WINDOWS
            : OperationSystem.LINUX;

    public static void main(String[] args) throws Exception {
        Properties settings = new Properties();
        settings.load(Application.class.getClassLoader().getResourceAsStream("credentials.properties"));
        String botToken = settings.getProperty("bot.token");

        InetSocketAddress proxy = new InetSocketAddress("178.238.232.35", 5836);
        TelegramBotClient botClient = new TelegramBotClient(botToken, proxy);

        Update[] updates = botClient.getUpdates();
        List<Voice> voices = Stream.of(updates)
                .filter(upd -> upd.message.voice != null)
                .map(upd -> upd.message.voice)
                .collect(toList());

        Path downloadDir = Paths.get("c:\\download");

        List<Path> oggFiles = new ArrayList<>();
        for (Voice voice: voices)
            oggFiles.add(botClient.downloadVoiceMessage(voice.fileId, downloadDir));
        System.out.println("загружены голосовые сообщения: " + oggFiles);

        OggToWavConverter converter = new OggToWavConverter();

        List<Path> wavFiles = new ArrayList<>();
        for (Path oggFile : oggFiles)
            wavFiles.add(converter.convert(oggFile));
        System.out.println("сконвертированы в wav голосовые сообщения: " + oggFiles);

    }

    public enum OperationSystem {
        WINDOWS,
        LINUX
    }
}
