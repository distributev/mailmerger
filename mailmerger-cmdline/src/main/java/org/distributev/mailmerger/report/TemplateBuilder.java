package org.distributev.mailmerger.report;

import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.ReportTemplate;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Builds YARG templates
 */
public class TemplateBuilder {

    /**
     * Builds a YARG {@link ReportTemplate} for a template file
     *
     * @param templateFileName the file name/location of the template file
     * @param outputNamePattern output name pattern for documents generated from this template
     * @return a new {@code ReportTemplate}
     */
    public ReportTemplate buildTemplate(String templateFileName, String outputNamePattern) {
        File templateFile = new File(templateFileName);
        try {
            return new ReportTemplateBuilder().code(ReportTemplate.DEFAULT_TEMPLATE_CODE)
                                              .documentName(templateFile.getName())
                                              .documentPath(templateFile.getPath())
                                              .readFileFromPath()
                                              .outputType(ReportOutputType.pdf)
                                              .outputNamePattern(outputNamePattern)
                                              .build();
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading template file", e);
        }
    }

}
