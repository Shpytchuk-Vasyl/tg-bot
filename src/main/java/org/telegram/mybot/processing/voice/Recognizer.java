package org.telegram.mybot.processing.voice;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.mybot.MyBotApplication;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import org.telegram.telegrambots.meta.api.objects.Voice;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Properties;

@Service
@Setter
public class Recognizer {
    private String token;

    public Recognizer() {
        Properties prop = new Properties();
        try {
            prop.load(MyBotApplication.class.getClassLoader().getResourceAsStream("application.properties"));
            this.token = prop.getProperty("telegram.bot.token");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String analyze(String filePath) {
        try {
            String wavFilePath = Converter.convert(filePath);

            String result = recognize(wavFilePath);

            FileUtils.forceDelete(new File(filePath));
            FileUtils.forceDelete(new File(wavFilePath));

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
            return "Error";
    }

    private String recognize(String fileName) throws Exception {
            CredentialsProvider credentialsProvider = FixedCredentialsProvider
                    .create(ServiceAccountCredentials.fromStream(new FileInputStream("src/main/resources/service-account.json")));

            SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
            StringBuilder resultText = new StringBuilder();
            try (SpeechClient speech = SpeechClient.create(settings)) {

                ByteString audioBytes = ByteString.copyFrom(Files.readAllBytes(Paths.get(fileName)));

                RecognitionConfig config = RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                        .setEnableAutomaticPunctuation(true)
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



    public String analyzeVoice(Voice voice) {
        return analyze(Downloader.downloadVoice(voice, token));
    }


    public String analyzeVideo(VideoNote videoNote) {
        return analyze(Downloader.downloadVideo(videoNote, token));
    }
}
