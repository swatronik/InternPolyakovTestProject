package com.polyakov.service.bufferreader;

import com.polyakov.log.Logger;

import java.io.*;

public class ReaderFromFile implements BufferReader {

    Logger logger = new Logger(ReaderFromFile.class);

    private final String fileName;

    public ReaderFromFile(String fileName) {
        this.fileName = fileName;
    }

    public BufferedReader getReader() throws FileNotFoundException {
        try {
            File file = new File(fileName);
            return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            logger.error("IOException", e.getMessage());
            throw e;
        }
    }
}