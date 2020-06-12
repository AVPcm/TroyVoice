package com.github.avpcm.troyvoice.telegram.client;

import com.github.avpcm.troyvoice.telegram.model.Message;

import java.io.IOException;
import java.nio.file.Path;

public interface TelegramBotClient {

    String POST_MESSAGE_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    String GET_FILE_TEMPLATE = "https://api.telegram.org/bot%s/getFile?file_id=%s";

    String DOWNLOAD_FILE_TEMPLATE = "https://api.telegram.org/file/bot%s/%s";

    Path downloadVoiceMessage(String fileId) throws Exception;

    Message postMessage(long chatId, String message) throws Exception;
}
