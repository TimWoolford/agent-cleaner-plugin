package sns.teamcity;

import java.io.File;
import java.util.Hashtable;

public class DiskSpaceSummary {
    private static final String TOTAL_SPACE = "totalSpace";
    private static final String FREE_SPACE = "freeSpace";

    private long totalSpace;
    private long freeSpace;

    public DiskSpaceSummary(File file) {
        totalSpace = file.getTotalSpace();
        freeSpace = file.getFreeSpace();
    }

    public DiskSpaceSummary(Hashtable<String, String> hashtable) {
        totalSpace = Long.valueOf(hashtable.get(TOTAL_SPACE));
        freeSpace = Long.valueOf(hashtable.get(FREE_SPACE));
    }

    public Hashtable<String, String> toHashTable() {
        return new Hashtable<String, String>() {
            {
                put(TOTAL_SPACE, String.valueOf(totalSpace));
                put(FREE_SPACE, String.valueOf(freeSpace));
            }
        };
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public long getTotalSpace() {
        return totalSpace;
    }
}