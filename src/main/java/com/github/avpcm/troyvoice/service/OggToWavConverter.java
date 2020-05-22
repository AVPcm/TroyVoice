package com.github.avpcm.troyvoice.service;

import com.github.avpcm.troyvoice.Application;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.avpcm.troyvoice.Application.OperationSystem.WINDOWS;

public class OggToWavConverter {

    public Path convert(Path ogg) throws Exception {
        Path library = getLibraryPath();
        Path wav = Paths.get(ogg.getParent().toString(), ogg.getFileName().toString() + ".wav");

        String command = "cmd /c " + library + " -i " + ogg + " -vn " + wav;

        System.out.println("начинаем конвертацию файла " + ogg + " в " + wav);
        Process execution = Runtime.getRuntime().exec(command);
        execution.waitFor();

        if (execution.exitValue() == 0) {
            System.out.println("конвертация успешно завершена");
        } else {
            System.out.println("конвертация завершилась с ошибкой: ");
            execution.getErrorStream().transferTo(System.out);
        }

        return wav;
    }

    private Path getLibraryPath() {
        if (Application.OS == WINDOWS)
            return Paths.get("lib/ffmpeg.exe");

        throw new UnsupportedOperationException("отсутствует библиотека ffmpeg для данной ОС");
    }
}
