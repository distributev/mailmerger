package org.distributev.mailmerger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Mail Merger command line application
 */
@SpringBootApplication
public class MailMerger implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(MailMerger.class);

    public static void main(String[] args) {
        SpringApplication.run(MailMerger.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Test log to the info file");
        LOGGER.error("Test log to the error file");
    }
}
