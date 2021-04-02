package com.polyakov.service.bufferreader;

import com.polyakov.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloaderCodeWebsite implements BufferReader {

    Logger logger = new Logger(DownloaderCodeWebsite.class);

    private final String host;

    public DownloaderCodeWebsite(String host) {
        this.host = host;
    }

    public BufferedReader getReader() throws IOException {
        try {
            URL url = new URL(host);
            return new BufferedReader(
                    new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));

        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e.getMessage());
            throw e;
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException", e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("IOException", e.getMessage());
            throw e;
        }
    }
}
