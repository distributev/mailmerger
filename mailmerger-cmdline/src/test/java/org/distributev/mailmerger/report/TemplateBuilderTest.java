package org.distributev.mailmerger.report;

import com.haulmont.yarg.structure.ReportTemplate;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class TemplateBuilderTest {

    private TemplateBuilder templateBuilder = new TemplateBuilder();

    @Test
    void shouldBuildTemplate() throws IOException {
        URL templateResource = TemplateBuilderTest.class.getClassLoader().getResource("templates/example.docx");
        ReportTemplate template = templateBuilder.buildTemplate(templateResource.getPath(), "output name");

        assertEquals("example.docx", template.getDocumentName());
        assertEquals(templateResource.getPath(), template.getDocumentPath());
        assertEquals(ReportTemplate.DEFAULT_TEMPLATE_CODE, template.getCode());
        assertEquals("output name", template.getOutputNamePattern());

        assertTrue(IOUtils.contentEquals(templateResource.openStream(), template.getDocumentContent()));
    }

    @Test
    void throwsExceptionIfTemplateFileNotFound() {
        assertThrows(UncheckedIOException.class, () -> templateBuilder.buildTemplate("invalid", "output name"));
    }
}
