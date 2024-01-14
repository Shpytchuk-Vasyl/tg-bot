package org.telegram.mybot.processing.voice;


public class Converter {
        public static String convert(String filePath) throws Exception {
            String newFilePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".wav";

            String command = "ffmpeg -i " + filePath + " " + newFilePath;

            Process process = new ProcessBuilder(command.split(" ")).start();

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
            return newFilePath;
        }
}
