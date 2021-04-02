package com.polyakov.log;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class Logger {

    private final String nameClass;

    public Logger(Class clazz) {
        this.nameClass = clazz.getName();
    }

    private void writeFile(String text) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void error(String msg, String error) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Instant.now()).append(" ").append(nameClass).append(": ")
                .append(msg).append(" ").append(error).append(System.lineSeparator());
        writeFile(stringBuilder.toString());
    }

    public void msg(String msg) {
        error(msg, "");
    }
}
