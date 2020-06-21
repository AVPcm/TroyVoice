package com.github.avpcm.troyvoice.service;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;

public class WavCuttingSilenceReader extends AudioInputStream {

    private final byte SILENCE_LEVEL = 10;

    private final int SILENCE_SEQUENTIAL_INTERVALS_THRESHOLD = 10;

    private int silenceIntervalCounter = 0;

    public WavCuttingSilenceReader(AudioInputStream original) {
        super(original, original.getFormat(), original.getFrameLength());
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);

        if (result == -1)
            return result;

        if (!isSilence(b)) {
            silenceIntervalCounter = 0;
            return result;
        }

        if (silenceIntervalCounter < SILENCE_SEQUENTIAL_INTERVALS_THRESHOLD) {
            silenceIntervalCounter++;
            return result;
        }

        return read(b, off, len);
    }

    private boolean isSilence(byte[] bytes) {
        byte avg = avgAbs(bytes);

        return avg <= SILENCE_LEVEL;
    }

    private byte avgAbs(byte[] bytes) {
        long tmp = 0;
        for (byte b : bytes)
            tmp += Math.abs(b);

        return (byte) (tmp / bytes.length);
    }
}
