package com.s4n.corrientazo.util;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    private static final Logger LOGGER = Logger.getLogger(FileUtils.class);

    public static List<String> readFileInList(String fileName) {
        List<String> lines = Collections.emptyList();
        try {
            ClassLoader loader = FileUtils.class.getClassLoader();
            File file = new File(loader.getResource(fileName).getFile());
            lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String writeResult(String fileName, List<String> result) {
        File file = new File("./results/" + fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(Constants.OUTPUT_FIRST_LINE + "\n");
            result.forEach(e -> {
                try {
                    bw.write(e + "\n");
                } catch (IOException ioException) {
                    LOGGER.error(
                            String.format("An error occurred writing the result file. Details:%s",
                                    ioException.getMessage()), ioException);
                }
            });
            bw.close();
        } catch (IOException e) {
            LOGGER.error(
                    String.format("An error occurred writing the result file. Details:%s",
                            e.getMessage()), e);
        }
        return "file created successfully";
    }
}
