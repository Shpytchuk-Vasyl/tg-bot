package org.telegram.mybot.processing.voice;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Voice;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class Recognizer {

    @Value("${telegram.bot.token}")
    private String token;
    private String recognize(String fileName) throws Exception {
        CredentialsProvider credentialsProvider = FixedCredentialsProvider
                .create(ServiceAccountCredentials.fromStream(new FileInputStream("src/main/resources/service-account.json")));

        SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
        StringBuilder resultText = new StringBuilder();
        try(SpeechClient speech = SpeechClient.create(settings)) {

            ByteString audioBytes = ByteString.copyFrom(Files.readAllBytes(Paths.get(fileName)));

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("uk-UA")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognizeResponse response = speech.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();


            for (SpeechRecognitionResult result : results) {
                List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
                for (SpeechRecognitionAlternative alternative : alternatives) {
                    resultText.append(alternative.getTranscript());
                }
            }
        }

        return resultText.toString();
    }



    public String analyzeVoice(Voice voice) throws Exception {
        String filePath = Downloader.downloadVoice(voice, token);

        String wavFilePath = Converter.convert(filePath);

        String result = recognize(wavFilePath);

        //cleanup
        FileUtils.forceDelete(new File(filePath));
        FileUtils.forceDelete(new File(wavFilePath));

        return result;
    }





}
