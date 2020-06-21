package com.github.avpcm.troyvoice.service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WavProcessingService {

    public Path cutSilence(Path wav) throws IOException, UnsupportedAudioFileException {
        Path dst = Paths.get(wav.getParent().toString(), wav.getFileName().toString() + "_cutted");

        AudioInputStream original = AudioSystem.getAudioInputStream(wav.toFile());
        AudioSystem.write(new WavCuttingSilenceReader(original), AudioFileFormat.Type.WAVE, dst.toFile());

        return dst;
    }
}
