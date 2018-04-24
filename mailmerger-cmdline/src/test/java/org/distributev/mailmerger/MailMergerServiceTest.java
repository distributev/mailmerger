package org.distributev.mailmerger;

import org.distributev.mailmerger.report.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

class MailMergerServiceTest {

    @InjectMocks
    private MailMergerService mailMergerService;

    @Mock
    private ReportGenerator reportGenerator;

    @Mock
    private MailMergerConfiguration mergerConfiguration;

    @Captor
    private ArgumentCaptor<Map<String, String>> dataCaptor;

    @BeforeEach
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);

        when(mergerConfiguration.getTemplate()).thenReturn("templates/example.docx");
        when(mergerConfiguration.getOutputFileName()).thenReturn("outputFile");
        when(mergerConfiguration.getOutputFolder()).thenReturn("outputFolder");
    }

    @Test
    void shouldGenerateCorrectNumberOfReports() {
        mailMergerService.generateReports(getCsvFile(), mergerConfiguration);

        verify(reportGenerator, times(3)).generateReport(anyString(), eq("outputFolder"), eq("outputFile"), anyMapOf(String.class, String.class));
    }

    @Test
    void shouldLocateTemplateFileFromClassPath() {
        mailMergerService.generateReports(getCsvFile(), mergerConfiguration);

        String expectedTemplateFilePath = getFileLocation("templates/example.docx");

        verify(reportGenerator, atLeastOnce()).generateReport(eq(expectedTemplateFilePath), anyString(), anyString(), anyMapOf(String.class, String.class));
    }

    @Test
    void shouldLocateExternalTemplateFile() throws IOException {
        File tempTemplateFile = File.createTempFile("template", ".docx");
        when(mergerConfiguration.getTemplate()).thenReturn(tempTemplateFile.getPath());
        mailMergerService.generateReports(getCsvFile(), mergerConfiguration);

        verify(reportGenerator, atLeastOnce()).generateReport(eq(tempTemplateFile.getPath()), anyString(), anyString(), anyMapOf(String.class, String.class));
    }

    @Test
    void shouldGenerateReportWithCorrectData() {
        mailMergerService.generateReports(getCsvFile(), mergerConfiguration);

        verify(reportGenerator, atLeastOnce()).generateReport(anyString(), anyString(), anyString(), dataCaptor.capture());

        List<Map<String, String>> dataValues = dataCaptor.getAllValues();
        Map<String, String> row1 = dataValues.get(0);
        assertEquals("value1-1", row1.get("column1"));
        assertEquals("value1-2", row1.get("column2"));
        assertEquals("value1-3", row1.get("column3"));

        Map<String, String> row2 = dataValues.get(1);
        assertEquals("value2-1", row2.get("column1"));
        assertEquals("value2-2", row2.get("column2"));
        assertEquals("value2-3", row2.get("column3"));

        Map<String, String> row3 = dataValues.get(2);
        assertEquals("value3-1", row3.get("column1"));
        assertEquals("value3-2", row3.get("column2"));
        assertEquals("value3-3", row3.get("column3"));
    }

    @Test
    void shouldThrowExceptionWithInvalidCsvFile() {
        assertThrows(UncheckedIOException.class, () ->
            mailMergerService.generateReports(new File("invalid.csv"), mergerConfiguration));
    }

    private static File getCsvFile() {
        return new File(getFileLocation("data/test.csv"));
    }

    private static String getFileLocation(String fileName) {
        return MailMergerServiceTest.class.getClassLoader().getResource(fileName).getPath();
    }

}
