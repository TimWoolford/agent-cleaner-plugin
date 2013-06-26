package sns.teamcity.model;

import jetbrains.buildServer.serverSide.comments.Comment;
import sns.teamcity.DiskSpaceSummary;

public class AgentInfo {
    private final int id;
    private final String name;
    private final DiskSpaceSummary diskSpaceSummary;
    private final String status;
    private final String statusComment;

    public AgentInfo(int id, String name, DiskSpaceSummary diskSpaceSummary, boolean isEnabled, Comment statusComment) {
        this.id = id;
        this.name = name;
        this.diskSpaceSummary = diskSpaceSummary;
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
}
