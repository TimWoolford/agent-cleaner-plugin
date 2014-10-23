package sns.teamcity;

import java.util.Hashtable;

import static sns.teamcity.Formatter.formatDiskSpace;

public class DiskUsage {
    private static final String DATA_APPS = "dataApps";
    private static final String LOGS_APPS = "logsApps";
    private static final String MAVEN_REPO = "mavenRepo";

    private final long dataApps;
    private final long logsApps;
    private final long mavenRepo;

    public DiskUsage(long dataApps, long logsApps, long mavenRepo) {
        this.dataApps = dataApps;
        this.logsApps = logsApps;
        this.mavenRepo = mavenRepo;
    }

    public DiskUsage(Hashtable<String, String> hashtable) {
        this(
                Long.valueOf(hashtable.get(DATA_APPS)),
                Long.valueOf(hashtable.get(LOGS_APPS)),
                Long.valueOf(hashtable.get(MAVEN_REPO))
        );
    }

    public Hashtable<String, String> toHashTable() {
        return new Hashtable<String, String>() {
            {
                put(DATA_APPS, String.valueOf(dataApps));
                put(LOGS_APPS, String.valueOf(logsApps));
                put(MAVEN_REPO, String.valueOf(mavenRepo));
            }
        };
    }

    public long getDataApps() {
        return dataApps;
    }

    public long getLogsApps() {
        return logsApps;
    }

    public long getMavenRepo() {
        return mavenRepo;
    }

    public String getFormattedDataApps() {
        return formatDiskSpace(dataApps);
    }

    public String getFormattedLogsApps() {
        return formatDiskSpace(logsApps);
    }

    public String getFormattedMavenRepo() {
        return formatDiskSpace(mavenRepo);
    }
}
