package org.distributev.mailmerger.report;

import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.ReportOutputDocumentImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReportWriterTest {

    private ReportWriter reportWriter = new ReportWriter();

    private byte[] content = RandomUtils.nextBytes(20);

    @Test
    void shouldWriteFile() throws IOException {
        File file = File.createTempFile("report", ".pdf");
        file.deleteOnExit();

        ReportOutputDocument reportOutputDocument = new ReportOutputDocumentImpl(null, content, file.getName(), null);

        reportWriter.writeReportToFile(file.getParent(), reportOutputDocument);

        byte[] writtenBytes = IOUtils.toByteArray(file.toURI());
        assertArrayEquals(reportOutputDocument.getContent(), writtenBytes);
    }

    @Test
    void shouldRethrowUncheckedIOException() {
        assertThrows(UncheckedIOException.class, () -> {
            reportWriter
                .writeReportToFile("xxx", new ReportOutputDocumentImpl(null, content, "..", null));
        });
    }

}
