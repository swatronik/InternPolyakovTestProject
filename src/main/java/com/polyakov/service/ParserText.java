package com.polyakov.service;

import com.polyakov.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class ParserText {

    Logger logger = new Logger(ParserText.class);

    public ParserText() {
    }

    public HashMap<String, Integer> parse(BufferedReader reader) throws IOException {

        HashMap<String, Integer> parseResult = new HashMap<>();
        try {

            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                String[] split = line.split("[-\\s '\\\\/<>=,.!?|\";:\\[\\]()\\n\\r\\t]");
                for (String el : split) {
                    el = el.trim();
                    if (!el.isEmpty()) {
                        parseResult.merge(el, 1, Integer::sum);
                    }
                }
            }
            return parseResult;
        } catch (IOException e) {
            logger.error("IOException", e.getMessage());
            throw e;
        }
    }
}
