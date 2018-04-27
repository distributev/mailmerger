package org.distributev.mailmerger.report;

import com.haulmont.yarg.formatters.ReportFormatter;
import com.haulmont.yarg.formatters.factory.FormatterFactoryInput;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.ReportOutputType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MailMergerFormatterFactoryTest {

    private MailMergerFormatterFactory factory = new MailMergerFormatterFactory();

    @Test
    void shouldReturnInstanceOfCustomFormatterForDocxExtension() {
        FormatterFactoryInput factoryInput =
            new FormatterFactoryInput("docx", Mockito.mock(BandData.class), null, ReportOutputType.pdf, null);

        ReportFormatter formatter = factory.createFormatter(factoryInput);

        assertTrue(formatter instanceof DefaultRootBandDocxFormatter);
    }


}
