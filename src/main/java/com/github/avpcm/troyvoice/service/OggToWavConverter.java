package com.github.avpcm.troyvoice.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OggToWavConverter {

    private final String COMMAND_TEMPLATE = "ffmpeg -i %s %s";

    private final Path wavDir;

    public OggToWavConverter(Path wavDir) {
        this.wavDir = wavDir;
    }

    public Path convert(Path ogg) {
        Path wav = Paths.get(wavDir.toString(), ogg.getFileName().toString().replace(".ogg", ".wav"));

        String command = String.format(COMMAND_TEMPLATE, ogg.toAbsolutePath(), wav.toAbsolutePath());
        try {
            Process execution = Runtime.getRuntime().exec(command);
            execution.waitFor();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return wav;
    }
}
