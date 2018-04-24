package org.distributev.mailmerger;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MailMergerConfigurationTest {

    @Test
    void shouldLoadConfigurationFromFile() {
        File file = new File(MailMergerConfigurationTest.class.getClassLoader().getResource("config/test-config.xml").getPath());
        MailMergerConfiguration configuration = new MailMergerConfiguration(file);

        assertEquals("template value", configuration.getTemplate());
        assertEquals("outputfolder value", configuration.getOutputFolder());
        assertEquals("outputfilename value", configuration.getOutputFileName());
    }

    @Test
    void shouldErrorWithInvalidConfigurationFile() {
        File file = new File(MailMergerConfigurationTest.class.getClassLoader().getResource("config/invalid-config.xml").getPath());

        assertThrows(IllegalArgumentException.class, () -> new MailMergerConfiguration(file));
    }

}
