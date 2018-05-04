package org.distributev.mailmerger.report;

import com.google.common.io.Files;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Writes report output files to disk
 */
public class ReportWriter {

    private static final Logger LOGGER = LogManager.getLogger(ReportWriter.class);

    /**
     * Writes the contents of a YARG {@link ReportOutputDocument} to the specified location.
     *
     * @param location the desired output location
     * @param reportOutputDocument the document to write
     */
    public void writeReportToFile(String location, ReportOutputDocument reportOutputDocument) {
        File file = new File(location, reportOutputDocument.getDocumentName());
        try {
            Files.createParentDirs(file);
            IOUtils.write(reportOutputDocument.getContent(), new FileOutputStream(file));
            LOGGER.info(() -> "Generated file " + file.getPath());
        } catch (IOException e) {
            throw new UncheckedIOException("Error writing to output file: " + file, e);
        }
    }
}
