package sns.teamcity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DiskSpaceSummaryTest {

    @Test
    public void formatsFreeSpace() {
        DiskSpaceSummary diskSpaceSummary = new DiskSpaceSummary(1234567890, 987564321);

        assertThat(diskSpaceSummary.getFormattedFreeSpace(), is("1,177.38 Mb"));
    }
}
