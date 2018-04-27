package org.distributev.mailmerger.report;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.formatters.impl.DocxFormatter;

/**
 * Extension of YARG's {@link DefaultFormatterFactory} to override the 'docx' formatter with a custom implementation.
 */
public class MailMergerFormatterFactory extends DefaultFormatterFactory {

    public MailMergerFormatterFactory() {
        super();
        formattersMap.put("docx", factoryInput -> {
            DocxFormatter docxFormatter = new DefaultRootBandDocxFormatter(factoryInput);
            docxFormatter.setDefaultFormatProvider(defaultFormatProvider);
            docxFormatter.setDocumentConverter(documentConverter);
            docxFormatter.setHtmlImportProcessor(htmlImportProcessor);
            return docxFormatter;
        });
    }
}
