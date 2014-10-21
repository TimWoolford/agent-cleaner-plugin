package sns.teamcity.model;

import jetbrains.buildServer.serverSide.comments.Comment;
import sns.teamcity.DiskSpaceSummary;

import java.util.Date;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

public class AgentInfo {
    private final int id;
    private final String name;
    private final DiskSpaceSummary diskSpaceSummary;
    private final Date registrationDate;
    private final String status;
    private final String statusComment;

    public AgentInfo(int id, String name, DiskSpaceSummary diskSpaceSummary, boolean isEnabled, Comment statusComment, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.diskSpaceSummary = diskSpaceSummary;
        this.registrationDate = registrationDate;
        this.status = isEnabled ? "Enabled" : "Disabled";
        this.statusComment = statusComment.getComment();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DiskSpaceSummary getDiskSpaceSummary() {
        return diskSpaceSummary;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public long getUptime() {
        return System.currentTimeMillis() - registrationDate.getTime();
    }

    public String getFormattedUptime() {
        String duration = formatDuration(getUptime(), "d' days 'H' hours'");
        return duration
                .replaceFirst("1 days", "1 day")
                .replaceFirst("1 hours", "1 hour");
    }
}
