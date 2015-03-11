package sns.teamcity.server;

import jetbrains.buildServer.serverSide.SBuildAgent;
import sns.teamcity.rpc.RpcCaller;

public class AgentCleaner {
    private final RpcCaller rpcCaller;

    public AgentCleaner(RpcCaller rpcCaller) {
        this.rpcCaller = rpcCaller;
    }

    public void cleanAppsAndLogs(SBuildAgent agent) {
        if (agent.getRunningBuild() == null) {
            rpcCaller.cleanAppDirs(agent);
        }
    }

    public void cleanRepositories(SBuildAgent agent) {
        if (agent.getRunningBuild() == null) {
            rpcCaller.cleanRepositories(agent);
        }
    }
}
