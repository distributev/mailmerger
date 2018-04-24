package org.distributev.mailmerger;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import org.distributev.mailmerger.report.ReportGenerator;
import org.distributev.mailmerger.report.ReportWriter;
import org.distributev.mailmerger.report.TemplateBuilder;

/**
 * Factory for building a MailMergerService and associated dependencies.
 */
public class MailMergerServiceFactory {

    /**
     * Builds a new {@link MailMergerService} completed with dependencies.
     *
     * @return a new MailMergerService
     */
    public static MailMergerService build() {
        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        reporting.setLoaderFactory(new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));

        TemplateBuilder templateBuilder = new TemplateBuilder();

        ReportWriter reportWriter = new ReportWriter();

        ReportGenerator reportGenerator = new ReportGenerator(reporting, templateBuilder, reportWriter);

        return new MailMergerService(reportGenerator);
    }

}
