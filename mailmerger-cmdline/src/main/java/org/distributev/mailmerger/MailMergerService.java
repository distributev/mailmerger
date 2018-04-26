package org.distributev.mailmerger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.distributev.mailmerger.report.ReportGenerator;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * Main Mail Merger service
 */
public class MailMergerService {

    private final ReportGenerator reportGenerator;

    public MailMergerService(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    /**
     * Generates a report for each row in the data file.
     *
     * @param dataFile the csv data file
     * @param configuration configuration for the report
     */
    public void generateReports(File dataFile, MailMergerConfiguration configuration) {
        List<Map<String, String>> rows = parseCsv(dataFile);

        String templateFile = getTemplateFile(configuration.getTemplate());

        for (Map<String, String> row : rows) {
            String outputName = configuration.getOutputFileName();
            reportGenerator.generateReport(templateFile, configuration.getOutputFolder(), outputName, row);
        }
    }

    private String getTemplateFile(String template) {
        File templateFile = new File(template);
        if (templateFile.exists()) {
            return template;
        } else {
            return MailMergerService.class.getClassLoader().getResource(template).getPath();
        }
    }

    private List<Map<String, String>> parseCsv(File dataFile) {
        try {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(
                new InputStreamReader(new BOMInputStream(new FileInputStream(dataFile))));
            return StreamSupport.stream(records.spliterator(), false)
                                .map(CSVRecord::toMap)
                                .collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
