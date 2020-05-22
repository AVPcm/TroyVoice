package com.github.avpcm.troyvoice;

import com.github.avpcm.troyvoice.service.OggToWavConverter;
import com.github.avpcm.troyvoice.telegram.client.TelegramBotClient;
import com.github.avpcm.troyvoice.telegram.model.Update;
import com.github.avpcm.troyvoice.telegram.model.Voice;
import com.github.avpcm.troyvoice.wit.client.WitApiClient;
import com.github.avpcm.troyvoice.wit.model.TextResponse;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

public class Application {

    public final static OperationSystem OS = System.getProperty("os.name").contains("indows")
            ? OperationSystem.WINDOWS
            : OperationSystem.LINUX;

    public static void main(String[] args) throws Exception {
        Properties settings = new Properties();
        settings.load(Application.class.getClassLoader().getResourceAsStream("credentials.properties"));

        String botToken = settings.getProperty("bot.token");
        String witToken = settings.getProperty("wit.token");
        Path downloadDir = Paths.get("c:\\download");

        InetSocketAddress proxy = new InetSocketAddress("178.238.232.35", 5836);
        TelegramBotClient botClient = new TelegramBotClient(botToken, proxy);
        WitApiClient witApiClient = new WitApiClient(witToken);

        OggToWavConverter converter = new OggToWavConverter();

        long latestUpdateId = 0L;
        while (true) {
            System.out.println("собираем обновления");
            List<Update> updates = Arrays.asList(botClient.getUpdates(latestUpdateId));

            long maxId = getMaxId(updates);
            if (maxId != 0L)
                latestUpdateId = maxId + 1;

            List<Voice> voices = getVoiceMessages(updates);
            for (Voice voice : voices) {
                if (voice.duration > 20) {
                    System.out.println("сообщение " + voice.fileId + " пропущено, так как превышает лимит в 20 сек.");
                    continue;
                }

                Path ogg = botClient.downloadVoiceMessage(voice.fileId, downloadDir);
                Path wav = converter.convert(ogg);

                TextResponse response = witApiClient.postSpeech(wav);

                String message = updates.get(0).message.from.firstName + ": ";
                if (response.error == null && response.code == null && response.text == null)
                    message = message + "<empty message>";
                else if (response.error != null)
                    message = message + "<error: " + response.error + ">";
                else
                    message = message + response.text;

                botClient.postMessage(updates.get(0).message.chat.id, message);
            }

            Thread.sleep(10_000L);
        }
    }

    private static long getMaxId(List<Update> updates) {
        return updates
                .stream()
                .map(upd -> upd.updateId)
                .max(comparingLong(id -> id))
                .orElse(0L);
    }

    private static List<Voice> getVoiceMessages(List<Update> updates) {
        return updates
                .stream()
                .map(upd -> upd.message.voice)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public enum OperationSystem {
        WINDOWS,
        LINUX
    }
}
