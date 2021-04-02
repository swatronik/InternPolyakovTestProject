package com.polyakov.main;

import com.polyakov.log.Logger;
import com.polyakov.main.argument.ArgumentParser;
import com.polyakov.service.RControllerDB;
import com.polyakov.service.bufferreader.DownloaderCodeWebsite;
import com.polyakov.service.ParserText;
import com.polyakov.service.bufferreader.ReaderFromFile;
import com.polyakov.service.WriterInFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    static Logger logger = new Logger(Main.class);

    private static final String FILE_NAME_CODE_WEBSITE = "code_website.txt";

    public static void main(String[] args) throws Exception {
        executeMain(new ArgumentParser(args));
    }

    private static void executeMain(ArgumentParser argumentParser) throws IOException {

        logger.msg("Program start");

        if (argumentParser.hostWebsite == null || argumentParser.hostWebsite.equals("")) {
            logger.error("Error","Empty host");
            return;
        }

        logger.msg("Host: " + argumentParser.hostWebsite);

        RControllerDB rControllerDB = null;

        try {
            rControllerDB = new RControllerDB(argumentParser.hostWebsite);
            logger.msg("MongoDB success run");
        } catch (Exception e){
            logger.error("MongoDB Error:", e.getMessage());
            logger.msg("MongoDB dont run");
        }



        if (rControllerDB != null && rControllerDB.checkHostInDBAndCreateIfNotExist()) {
            logger.msg("This host already exist in database");
            HashMap<String, Integer> dataFromMap = rControllerDB.getDataFromMap();
            dataFromMap.forEach((key, value) -> System.out.println(key + " " + value));
        } else {
            try {
                DownloaderCodeWebsite downloaderCodeWebsite = new DownloaderCodeWebsite(argumentParser.hostWebsite);
                BufferedReader downloadReader = downloaderCodeWebsite.getReader();

                if (downloadReader == null) {
                    logger.error("Error", "DownloadReader is null");
                    return;
                }

                WriterInFile writerInFile = new WriterInFile(FILE_NAME_CODE_WEBSITE);
                boolean write = writerInFile.write(downloadReader);

                if (!write) {
                    logger.error("Error", "Write is not complete");
                    return;
                }

                ReaderFromFile reader = new ReaderFromFile(FILE_NAME_CODE_WEBSITE);
                BufferedReader fileReader = reader.getReader();

                if (fileReader == null) {
                    logger.error("Error", "FileReader is null");
                    return;
                }

                ParserText parserText = new ParserText();
                HashMap<String, Integer> parse = parserText.parse(fileReader);

                RControllerDB finalRControllerDB = rControllerDB;
                parse.forEach((key, value) -> {
                    System.out.println(key + " " + value);
                    if (finalRControllerDB != null) {
                        finalRControllerDB.writeInDb(key, value);
                    }
                });
            } catch (Exception e) {
                throw e;
            }
        }

        logger.msg("Program success stop");
    }
}
