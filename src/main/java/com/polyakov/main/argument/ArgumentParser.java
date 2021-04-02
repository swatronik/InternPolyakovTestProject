package com.polyakov.main.argument;

import com.polyakov.log.Logger;
import org.apache.commons.cli.*;

public class ArgumentParser {

    Logger logger = new Logger(ArgumentParser.class);

    private static final String HOST_WEBSITE = "host_website";

    public String hostWebsite;

    public ArgumentParser(String[] args) {
        Options options = new Options().addOption(Option.builder().longOpt(HOST_WEBSITE).hasArg(true)
                .optionalArg(true).desc("Website host").build());
        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            hostWebsite = cmd.getOptionValue(HOST_WEBSITE, "https://www.simbirsoft.com/");
        } catch (ParseException e) {
            logger.error("ParseException", e.getMessage());
        }
    }
}
