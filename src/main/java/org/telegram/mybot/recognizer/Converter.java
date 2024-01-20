package org.telegram.mybot.recognizer;


import java.util.concurrent.TimeUnit;

public class Converter {
        public static String convert(String filePath) throws Exception {
            String newFilePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".wav";

            String command = "ffmpeg -i " + filePath + " -vn " + newFilePath;

            Process process = new ProcessBuilder(command.split(" ")).start();

            boolean exitCode = process.waitFor(120, TimeUnit.SECONDS);
            System.out.println(exitCode ? "Success " + command : "Fail " + command);
            return newFilePath;
        }
}
