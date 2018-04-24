package org.distributev.mailmerger.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportGeneratorTest {

    private static final String TEMPLATE_FILE = RandomStringUtils.randomAlphanumeric(10);

    private static final String OUTPUT_NAME = RandomStringUtils.randomAlphanumeric(10);

    private static final String OUTPUT_LOCATION = RandomStringUtils.randomAlphanumeric(10);

    @InjectMocks
    private ReportGenerator reportGenerator;

    @Mock
    private TemplateBuilder templateBuilder;

    @Mock
    private ReportWriter reportWriter;

    @Mock
    private Reporting reporting;

    @Mock
    private ReportTemplate reportTemplate;

    @Captor
    private ArgumentCaptor<RunParams> runParamsCaptor;

    @Mock
    private ReportOutputDocument reportOutputDocument;

    @BeforeEach
    void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGenerateReport() throws Exception {
        Map<String, String> data = ImmutableMap.of("a", "b",
                                                   "c", "d");

        when(templateBuilder.buildTemplate(TEMPLATE_FILE, OUTPUT_NAME)).thenReturn(reportTemplate);
        when(reporting.runReport(runParamsCaptor.capture())).thenReturn(reportOutputDocument);

        reportGenerator.generateReport(TEMPLATE_FILE, OUTPUT_LOCATION, OUTPUT_NAME, data);

        verify(reportWriter).writeReportToFile(OUTPUT_LOCATION, reportOutputDocument);
        RunParams runParams = runParamsCaptor.getValue();
        validateRunParams(data, runParams);
    }

    @SuppressWarnings("unchecked")
    private void validateRunParams(Map<String, String> data, RunParams runParams) throws Exception {
        Report paramsReport = (Report) getField(runParams, "report");
        ReportTemplate paramsReportTemplate = (ReportTemplate) getField(runParams, "reportTemplate");
        Map<String, Object> params = (Map) getField(runParams, "params");

        assertEquals(reportTemplate, paramsReportTemplate);
        assertEquals("parameter=param1 $", paramsReport.getRootBand().getReportQueries().get(0).getScript());
        assertEquals(new ObjectMapper().writeValueAsString(data), params.get("param1"));
    }

    private static Object getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);

    }
}
