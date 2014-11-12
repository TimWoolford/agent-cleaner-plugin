package sns.teamcity;

import static sns.teamcity.Formatter.formatDiskSpace;

public class FileSummary {
    private final String path;
    private final long size;

    public FileSummary(String path, long size) {
        this.path = path;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public String getFormattedSize() {
        return formatDiskSpace(size);
    }
}
