package com.github.avpcm.troyvoice;

import com.github.avpcm.troyvoice.service.OggToWavConverter;
import com.github.avpcm.troyvoice.telegram.client.TelegramBotClientImpl;
import com.github.avpcm.troyvoice.telegram.model.Message;
import com.github.avpcm.troyvoice.telegram.model.Update;
import com.github.avpcm.troyvoice.telegram.model.User;
import com.github.avpcm.troyvoice.wit.client.WitApiClient;
import com.github.avpcm.troyvoice.wit.model.TextResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class MainController {

    @Autowired
    private WitApiClient witApiClient;

    @Autowired
    private TelegramBotClientImpl telegramBotClientImpl;

    @Autowired
    private OggToWavConverter oggToWavConverter;

    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public String hello() {
        return "hello!";
    }

    @RequestMapping(path = "/telegram/updates", method = RequestMethod.POST)
    public HttpStatus recieveUpdate(@RequestBody Update update) throws Exception {
        Message message = update.message;

        if (message.voice == null)
            return HttpStatus.OK;

        if (message.voice.duration >= 20)
            return HttpStatus.OK;

        Path ogg = telegramBotClientImpl.downloadVoiceMessage(message.voice.fileId);
        Path wav = oggToWavConverter.convert(ogg);
        TextResponse witResponse = witApiClient.postSpeech(wav);

        String sender = getUserAlias(message.from);
        String botMessage = witResponse.error != null
                ? sender + ": <err: " + witResponse.error + ">"
                : sender + ": " + witResponse.text;

        telegramBotClientImpl.postMessage(message.chat.id, botMessage);
        Files.delete(ogg);
        Files.delete(wav);

        return HttpStatus.OK;
    }

    private String getUserAlias(User user) {
        // vasyan777
        if (user.userName != null)
            return user.userName;

        return user.lastName == null
                ? user.firstName // Василий
                : user.firstName + " " + user.lastName.substring(0, 1) + "."; // Василий И.
    }
}
