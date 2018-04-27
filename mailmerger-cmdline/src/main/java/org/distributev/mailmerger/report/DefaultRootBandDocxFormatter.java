package org.distributev.mailmerger.report;

import com.haulmont.yarg.formatters.factory.FormatterFactoryInput;
import com.haulmont.yarg.formatters.impl.DocxFormatter;
import org.apache.commons.lang3.StringUtils;

/**
 * Extension of YARG's {@link DocxFormatter} to treat aliases with no band name as if they were for the root band.
 */
public class DefaultRootBandDocxFormatter extends DocxFormatter {

    public DefaultRootBandDocxFormatter(FormatterFactoryInput formatterFactoryInput) {
        super(formatterFactoryInput);
    }

    @Override
    protected BandPathAndParameterName separateBandNameAndParameterName(String alias) {
        BandPathAndParameterName bandPathAndParameterName = super.separateBandNameAndParameterName(alias);
        if (StringUtils.isEmpty(bandPathAndParameterName.getBandPath())) {
            return new BandPathAndParameterName("Root", bandPathAndParameterName.getParameterName());
        } else {
            return bandPathAndParameterName;
        }
    }


}
