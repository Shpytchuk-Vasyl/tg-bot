package org.telegram.mybot.processing.voice;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Voice;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Downloader {


    public static String downloadVoice(Voice voice, String token) throws Exception {
        String fileId = voice.getFileId();

        String filePathUrl = "https://api.telegram.org/bot" + token + "/getFile?file_id=" + fileId;
        JSONObject json = new JSONObject(IOUtils.toString(new URL(filePathUrl), StandardCharsets.UTF_8));

        String filePath = ((JSONObject) json.get("result")).get("file_path").toString();

        String downloadPath = "https://api.telegram.org/file/bot" + token +
                "/" + filePath;

        downloadToFile(downloadPath, filePath);

        return filePath;
    }


    private static void downloadToFile(String url, String filePath) throws Exception {
        try {
            File file = new File(filePath);
            FileUtils.copyURLToFile(new URL(url), file);
        } catch (Exception e) {
            throw new IOException();
        }
    }


}
