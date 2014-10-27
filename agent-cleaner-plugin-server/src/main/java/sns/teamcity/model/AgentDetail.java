package sns.teamcity.model;

import jetbrains.buildServer.serverSide.comments.Comment;
import sns.teamcity.DiskSpaceSummary;
import sns.teamcity.DiskUsage;

import java.util.Date;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

public class AgentDetail {
    private final int id;
    private final String name;
    private final boolean hasPendingRebuild;
    private final DiskSpaceSummary diskSpaceSummary;
    private final DiskUsage diskUsage;
    private final String status;
    private final String statusComment;
    private final String runningBuildStatus;

    public AgentDetail(int id, String name, boolean isEnabled, Comment statusComment, boolean hasPendingRebuild, DiskSpaceSummary diskSpaceSummary, DiskUsage diskUsage, String runningBuildStatus) {
        this.id = id;
        this.name = name;
        this.hasPendingRebuild = hasPendingRebuild;
        this.diskSpaceSummary = diskSpaceSummary;
        this.diskUsage = diskUsage;
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

    public DiskUsage getDiskUsage() {
        return diskUsage;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public boolean getHasPendingRebuild() {
        return hasPendingRebuild;
    }

    public DiskSpaceSummary getDiskSpaceSummary() {
        return diskSpaceSummary;
    }
}
