package org.telegram.mybot.processing.voice;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Voice;
import java.io.*;
import java.nio.file.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Downloader {


    public static String downloadVoice(Voice voice, String token) throws IOException {
        String fileId = voice.getFileId();

        String filePathUrl = "https://api.telegram.org/bot" + token + "/getFile?file_id=" + fileId;
        JSONObject json;

        try (InputStream inputStream = new URL(filePathUrl).openStream()) {
            json = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
        }

        String filePath = ((JSONObject) json.get("result")).get("file_path").toString();

        String downloadPath = "https://api.telegram.org/file/bot" + token + "/" + filePath;

        return downloadToFile(downloadPath);
    }

    private static String downloadToFile(String url) throws IOException {
        try (InputStream inputStream = new URL(url).openStream()) {
            Path filePath = Paths.get(".", Thread.currentThread().getName() + ".oga");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        }
    }



}
