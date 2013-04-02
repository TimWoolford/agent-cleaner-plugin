package sns.teamcity;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.BuildAgent;

import java.util.Hashtable;

public class DiskSpaceSummariser extends AgentLifeCycleAdapter {

    private final BuildAgent buildAgent;

    public DiskSpaceSummariser(BuildAgent buildAgent) {
        this.buildAgent = buildAgent;
    }

    public Hashtable<String, String> diskSpaceSummary() {
        return new DiskSpaceSummaryParser(buildAgent.getConfiguration().getWorkDirectory()).toHashTable();
    }
}