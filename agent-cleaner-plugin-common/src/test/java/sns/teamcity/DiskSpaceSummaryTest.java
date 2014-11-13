package sns.teamcity;

import org.junit.Test;
import sns.teamcity.model.DiskSpaceSummary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DiskSpaceSummaryTest {

    @Test
    public void formatsFreeSpace() {
        DiskSpaceSummary diskSpaceSummary = new DiskSpaceSummary(1234567890, 1987564321);

        assertThat(diskSpaceSummary.getFormattedFreeSpace(), is("1,177.38 Mb"));
    }

    @Test
    public void showPercentageUsed() {
        DiskSpaceSummary diskSpaceSummary = new DiskSpaceSummary(10, 100);

        assertThat(diskSpaceSummary.getPercentageUsed(), is(90.0));
    }

    @Test
    public void formatsPercentageUsed() throws Exception {
        DiskSpaceSummary diskSpaceSummary = new DiskSpaceSummary(1234567890, 1987564321);

        assertThat(diskSpaceSummary.getFormattedPercentageUsed(), is("37.9%"));
    }
}