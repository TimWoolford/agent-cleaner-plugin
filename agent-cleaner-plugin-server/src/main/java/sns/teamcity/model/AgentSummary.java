package sns.teamcity.model;

import jetbrains.buildServer.serverSide.comments.Comment;
import sns.teamcity.DiskSpaceSummary;
import sns.teamcity.DiskUsage;

import java.util.Date;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

public class AgentSummary {
    private final int id;
    private final String name;
    private final Date registrationDate;
    private final String status;
    private final String statusComment;
    private final String runningBuildStatus;

    public AgentSummary(int id, String name, boolean isEnabled, Comment statusComment, Date registrationDate, String runningBuildStatus) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.runningBuildStatus = runningBuildStatus;
        this.status = isEnabled ? "Enabled" : "Disabled";
        this.statusComment = statusComment.getComment();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getRunningBuildStatus() {
        return runningBuildStatus;
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