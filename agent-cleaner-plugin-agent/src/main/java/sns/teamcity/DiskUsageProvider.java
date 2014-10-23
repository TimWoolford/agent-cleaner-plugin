package sns.teamcity;

import org.apache.commons.io.FileUtils;

public class DiskUsageProvider {
    private final DirectoryLocator directory;

    public DiskUsageProvider(DirectoryLocator directory) {
        this.directory = directory;
    }

    public java.util.Hashtable<String, String> usage() {
        return new DiskUsage(
                FileUtils.sizeOfDirectory(directory.dataApps()),
                FileUtils.sizeOfDirectory(directory.logsApps()),
                FileUtils.sizeOfDirectory(directory.mavenRepository())
        ).toHashTable();
    }
}
