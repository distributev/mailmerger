package org.distributev.mailmerger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * End-to-end test fot the {@link MailMerger}
 */
@Disabled
class MailMergerIntegrationTest {


    @Test
    void testMailMergerEndtoEnd() {
        String[] args = new String[]{"-f", getResourseLocation("example/test-data.csv"),
                                     "-c", getResourseLocation("example/default-configuration.xml")};

        MailMerger.main(args);
    }

    private String getResourseLocation(String resource) {
        return MailMergerIntegrationTest.class.getClassLoader().getResource(resource).getPath();
    }


}
