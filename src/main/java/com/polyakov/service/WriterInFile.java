package com.polyakov.service;

import com.polyakov.log.Logger;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriterInFile {

    Logger logger = new Logger(ParserText.class);

    private final String fileName;

    public WriterInFile(String fileName) {
        this.fileName = fileName;
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean write(BufferedReader reader) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {

            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                writer.write(line);
            }
            return true;
        } catch (IOException e) {
            logger.error("IOException", e.getMessage());
            throw e;
        }
    }
}
