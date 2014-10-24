package sns.teamcity;

import java.io.File;

import static org.apache.commons.io.FileUtils.sizeOfDirectory;

public class DiskUsageProvider {
    private final DirectoryLocator directory;

    public DiskUsageProvider(DirectoryLocator directory) {
        this.directory = directory;
    }

    public java.util.Hashtable<String, String> usage() {
        return new DiskUsage(
                sizeOf(directory.dataApps()),
                sizeOf(directory.logsApps()),
                sizeOf(directory.mavenRepository())
        ).toHashTable();
    }

    private long sizeOf(File directory) {
        return (directory == null || !directory.exists()) ? 0 : sizeOfDirectory(directory);
    }
}
