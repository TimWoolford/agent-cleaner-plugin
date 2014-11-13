package sns.teamcity.model;

import jetbrains.buildServer.serverSide.comments.Comment;

import java.util.Date;

public class AgentDetail  extends AgentSummary {
    private final boolean hasPendingRebuild;
    private final DiskSpaceSummary diskSpaceSummary;
    private final DiskUsage diskUsage;

    public AgentDetail(int id, String name, boolean isEnabled, Comment statusComment, Date registrationDate, String runningBuildStatus, String buildName, boolean hasPendingRebuild, DiskSpaceSummary diskSpaceSummary, DiskUsage diskUsage) {
        super(id, name, isEnabled, statusComment, registrationDate, runningBuildStatus, buildName);
        this.hasPendingRebuild = hasPendingRebuild;
        this.diskSpaceSummary = diskSpaceSummary;
        this.diskUsage = diskUsage;
    }

    public DiskUsage getDiskUsage() {
        return diskUsage;
    }

    public boolean getHasPendingRebuild() {
        return hasPendingRebuild;
    }

    public DiskSpaceSummary getDiskSpaceSummary() {
        return diskSpaceSummary;
    }
}
