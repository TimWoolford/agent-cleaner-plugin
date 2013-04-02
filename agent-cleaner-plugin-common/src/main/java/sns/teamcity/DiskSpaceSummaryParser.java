package sns.teamcity;

import java.io.File;
import java.util.Hashtable;

public class DiskSpaceSummaryParser {
    private static final String TOTAL_SPACE = "totalSpace";
    private static final String FREE_SPACE = "freeSpace";

    private long totalSpace;
    private long freeSpace;

    public DiskSpaceSummaryParser(File file) {
        totalSpace = file.getTotalSpace();
        freeSpace = file.getFreeSpace();
    }

    public DiskSpaceSummaryParser(Hashtable<String, String> hashtable) {
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

    public long freeSpace() {
        return freeSpace;
    }

    public long totalSpace() {
        return totalSpace;
    }
}