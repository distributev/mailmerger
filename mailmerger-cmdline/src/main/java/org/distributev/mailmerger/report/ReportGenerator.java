package org.distributev.mailmerger.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.ImmutableMap;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.ReportTemplate;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportImpl;

import java.util.Collections;
import java.util.Map;

/**
 * Generates reports
 */
public class ReportGenerator {

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();

    private final Reporting reporting;

    private final TemplateBuilder templateBuilder;

    private final ReportWriter reportWriter;

    public ReportGenerator(Reporting reporting, TemplateBuilder templateBuilder, ReportWriter reportWriter) {
        this.reporting = reporting;
        this.templateBuilder = templateBuilder;
        this.reportWriter = reportWriter;
    }

    /**
     * Generates a single report
     *
     * @param templateFile path of the template file to use
     * @param outputLocation location where the generated report should be written
     * @param outputName desired name of the report
     * @param data Map of data for the report
     */
    public void generateReport(String templateFile, String outputLocation, String outputName, Map<String, String> data) {
        String json = getJsonData(data);

        ReportTemplate reportTemplate = templateBuilder.buildTemplate(templateFile, outputName);
        ReportBand rootBand = buildRootBand();

        Report report = new ReportImpl("report", ImmutableMap.of(ReportTemplate.DEFAULT_TEMPLATE_CODE, reportTemplate), rootBand, Collections.emptyList(), Collections.emptyList());

        RunParams runParams = new RunParams(report).param("param1", json);
        ReportOutputDocument reportOutputDocument = reporting.runReport(runParams);

        reportWriter.writeReportToFile(outputLocation, reportOutputDocument);
    }

    private static ReportBand buildRootBand() {
        BandBuilder rootBandDefinitionBuilder = new BandBuilder().name(BandData.ROOT_BAND_NAME)
                                                                 .query("Root", "parameter=param1 $", "json");
        return rootBandDefinitionBuilder.build();
    }

    private static String getJsonData(Map<String, String> data) {
        try {
            return OBJECT_WRITER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error marshalling to JSON", e);
        }
    }
}
