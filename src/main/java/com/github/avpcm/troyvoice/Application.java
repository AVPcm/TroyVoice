package com.github.avpcm.troyvoice;

import com.github.avpcm.troyvoice.service.OggToWavConverter;
import com.github.avpcm.troyvoice.service.WavProcessingService;
import com.github.avpcm.troyvoice.telegram.client.TelegramBotClientImpl;
import com.github.avpcm.troyvoice.wit.client.WitApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Paths;

@SpringBootApplication
@PropertySource("classpath:app.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public OggToWavConverter oggToWavConverter(@Value("${wav.dir}") String wavDir) {
        return new OggToWavConverter(Paths.get(wavDir));
    }

    @Bean
    public WitApiClient witApiClient(@Value("${wit.token}") String token) {
        return new WitApiClient(token);
    }

    @Bean
    public TelegramBotClientImpl telegramBotClient(@Value("${bot.token}") String token) {
        return new TelegramBotClientImpl(token);
    }

    @Bean
    public WavProcessingService wavProcessingService() {
        return new WavProcessingService();
    }
}
