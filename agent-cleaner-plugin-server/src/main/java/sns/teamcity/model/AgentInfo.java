package sns.teamcity.model;

import sns.teamcity.DiskSpaceSummary;

public class AgentInfo {
    private final String name;
    private final DiskSpaceSummary diskSpaceSummary;

    public AgentInfo(String name, DiskSpaceSummary diskSpaceSummary) {
        this.name = name;
        this.diskSpaceSummary = diskSpaceSummary;
    }

    public String getName() {
        return name;
    }

    public DiskSpaceSummary getDiskSpaceSummary() {
        return diskSpaceSummary;
    }

}
