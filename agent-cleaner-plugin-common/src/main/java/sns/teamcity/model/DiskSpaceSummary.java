package sns.teamcity.model;

import java.util.Hashtable;

import static sns.teamcity.Formatter.formatDiskSpace;

public class DiskSpaceSummary {
    private static final String TOTAL_SPACE = "totalSpace";
    private static final String FREE_SPACE = "freeSpace";

    private final long totalSpace;
    private final long freeSpace;

    public DiskSpaceSummary(long freeSpace, long totalSpace) {
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
    }

    public DiskSpaceSummary(Hashtable<String, String> hashtable) {
        freeSpace = Long.valueOf(hashtable.get(FREE_SPACE));
        totalSpace = Long.valueOf(hashtable.get(TOTAL_SPACE));
    }

    public Hashtable<String, String> toHashTable() {
        return new Hashtable<String, String>() {
            {
                put(FREE_SPACE, String.valueOf(freeSpace));
                put(TOTAL_SPACE, String.valueOf(totalSpace));
            }
        };
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public String getFormattedFreeSpace() {
        return formatDiskSpace(freeSpace);
    }

    public double getPercentageUsed() {
        return ((double) (totalSpace - freeSpace) * 100) / totalSpace;
    }

    public String getFormattedPercentageUsed() {
        return String.format("%.1f%%", getPercentageUsed());
    }
}