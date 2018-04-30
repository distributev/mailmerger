package org.distributev.mailmerger;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Mail Merger command line application
 */
public class MailMerger {

    private static final Logger LOGGER = LogManager.getLogger(MailMerger.class);

    private static final MailMergerService mailMergerService = MailMergerServiceFactory.build();

    /**
     * Default configuration file to be used if no file is specified
     */
    private static final String DEFAULT_CONFIGURATION_FILE = getDefaultConfigurationFile();

    public static void main(String[] args) {
        CommandLine commandLine = generateCommandLine(generateOptions(), args);

        if (!commandLine.hasOption("file")) {
            throw new IllegalArgumentException("file option must be specified");
        }

        String dataFileLocation = commandLine.getOptionValue("file");
        String configurationFileLocation = commandLine.getOptionValue("configuration", DEFAULT_CONFIGURATION_FILE);

        File dataFile = new File(dataFileLocation);

        if (!dataFile.exists() || !dataFile.isFile()) {
            throw new IllegalArgumentException("Data file not found");
        }

        File configurationFile = new File(configurationFileLocation);

        if (!configurationFile.exists() || !configurationFile.isFile()) {
            throw new IllegalArgumentException("Configuration file " + configurationFile + " not found");
        }

        try {
            mailMergerService.generateReports(dataFile, new MailMergerConfiguration(configurationFile));
        } catch (Exception e) {
            LOGGER.error("Error generating reports", e);
            throw new RuntimeException(e);
        }
    }

    private static String getDefaultConfigurationFile() {
        return MailMerger.class.getClassLoader().getResource("default-configuration.xml").getPath();
    }

    private static Options generateOptions() {
        final Option fileOption = Option.builder("f")
                                        .required()
                                        .longOpt("file")
                                        .hasArg()
                                        .desc("CSV file containing report rows")
                                        .build();

        final Option configurationOption = Option.builder("c")
                                                 .longOpt("configuration")
                                                 .hasArg()
                                                 .desc("Configuration file")
                                                 .build();

        Options options = new Options();
        options.addOption(fileOption);
        options.addOption(configurationOption);
        return options;
    }

    private static CommandLine generateCommandLine(Options options, String[] commandLineArguments) {
        CommandLineParser cmdLineParser = new DefaultParser();
        try {
            return cmdLineParser.parse(options, commandLineArguments);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
