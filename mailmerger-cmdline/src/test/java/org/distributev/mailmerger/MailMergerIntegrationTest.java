package org.distributev.mailmerger;

import com.google.common.io.Files;
import com.google.common.io.PatternFilenameFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * End-to-end test for the {@link MailMerger}
 */
class MailMergerIntegrationTest {

    private File temporaryWorkSpace;

    @BeforeEach
    void createTemporaryWorkingDirectory() throws IOException {
        temporaryWorkSpace = Files.createTempDir();

        String configString = FileUtils.readFileToString(new File(
            MailMergerIntegrationTest.class.getClassLoader().getResource("example/default-configuration.xml").getPath()));
        configString = configString.replaceAll("\\{outputfolder\\}", temporaryWorkSpace.getPath().replace('\\', '/'));
        File tempConfigFile = new File(temporaryWorkSpace, "config.xml");

        FileUtils.writeStringToFile(tempConfigFile, configString);
    }

    @AfterEach
    void cleanup() throws IOException {
        FileUtils.forceDeleteOnExit(temporaryWorkSpace);
    }


    @Test
    void testMailMergerEndtoEnd() throws Exception{
        File config = new File(temporaryWorkSpace, "config.xml");

        String[] args = new String[]{"-f", getResourseLocation("example/test-data.csv"),
                                     "-c", config.getPath()};

        MailMerger.main(args);

        String[] pdfFiles = temporaryWorkSpace.list((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        assertEquals(4, pdfFiles.length);

        Arrays.sort(pdfFiles);

        assertEquals("aaa.pdf", pdfFiles[0]);
        assertEquals("eee.pdf", pdfFiles[1]);
        assertEquals("iii.pdf", pdfFiles[2]);
        assertEquals("lll.pdf", pdfFiles[3]);
    }

    private static String getResourseLocation(String resource) {
        return MailMergerIntegrationTest.class.getClassLoader().getResource(resource).getPath();
    }


}
