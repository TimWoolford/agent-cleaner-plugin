package sns.teamcity.model;

import sns.teamcity.DiskSpaceSummary;

public class AgentInfo {
    private final int id;
    private final String name;
    private final DiskSpaceSummary diskSpaceSummary;
    private final String status;

    public AgentInfo(int id, String name, DiskSpaceSummary diskSpaceSummary, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.diskSpaceSummary = diskSpaceSummary;
        this.status = isEnabled ? "enabled" : "disabled";
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
}
