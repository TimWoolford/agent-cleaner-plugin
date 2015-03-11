package sns.teamcity.agent;

import jetbrains.buildServer.agent.BuildAgent;
import sns.teamcity.model.DiskSpaceSummary;

import java.io.File;
import java.util.Hashtable;

public class DiskSpaceSummariser {

    private final BuildAgent buildAgent;

    public DiskSpaceSummariser(BuildAgent buildAgent) {
        this.buildAgent = buildAgent;
    }

    public Hashtable<String, String> diskSpaceSummary() {
        File workDirectory = buildAgent.getConfiguration().getWorkDirectory();
        return new DiskSpaceSummary(workDirectory.getFreeSpace(), workDirectory.getTotalSpace()).toHashTable();
    }
}