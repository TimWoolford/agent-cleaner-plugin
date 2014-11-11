package sns.teamcity.agent;

import jetbrains.buildServer.agent.BuildAgent;
import sns.teamcity.DiskSpaceSummary;

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