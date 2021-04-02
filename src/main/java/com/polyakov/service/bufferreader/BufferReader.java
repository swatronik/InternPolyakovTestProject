package com.polyakov.service.bufferreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public interface BufferReader {

    BufferedReader getReader() throws IOException;
}
