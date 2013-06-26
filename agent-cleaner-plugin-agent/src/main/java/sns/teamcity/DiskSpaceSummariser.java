package sns.teamcity;

import jetbrains.buildServer.agent.BuildAgent;

import java.util.Hashtable;

public class DiskSpaceSummariser {

    private final BuildAgent buildAgent;

    public DiskSpaceSummariser(BuildAgent buildAgent) {
        this.buildAgent = buildAgent;
    }

    public Hashtable<String, String> diskSpaceSummary() {
        return new DiskSpaceSummary(buildAgent.getConfiguration().getWorkDirectory()).toHashTable();
    }
}