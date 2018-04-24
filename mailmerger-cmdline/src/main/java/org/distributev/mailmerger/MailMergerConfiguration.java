package org.distributev.mailmerger;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.distributev.mailmerger.report.ReportWriter;

import java.io.File;

/**
 * Configuration for the Mail Merger
 */
public class MailMergerConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(MailMergerConfiguration.class);

    private Configuration configuration;

    /**
     * Constructor
     *
     * @param configFile XML configuration file to load
     */
    public MailMergerConfiguration(File configFile) {
        configuration = loadConfiguration(configFile);
    }

    public String getTemplate() {
        return configuration.getString("settings.template");
    }

    public String getOutputFileName() {
        return configuration.getString("settings.outputfilename");
    }

    public String getOutputFolder() {
        return configuration.getString("settings.outputfolder");
    }

    private static Configuration loadConfiguration(File configurationFile) {
        try {
            XMLConfiguration configuration = new Configurations().xml(configurationFile.getPath());
            LOGGER.debug("Loaded configuration from {}", configurationFile);
            return configuration;
        } catch (ConfigurationException e) {
            throw new IllegalArgumentException("Error loading configuration", e);
        }
    }

}
