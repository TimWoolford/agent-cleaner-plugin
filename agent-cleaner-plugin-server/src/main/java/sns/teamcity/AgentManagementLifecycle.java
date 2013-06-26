package sns.teamcity;

import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.util.EventDispatcher;
import sns.teamcity.rpc.RpcCaller;

public class AgentManagementLifecycle extends BuildServerAdapter {

    private RpcCaller rpcCaller;

    public AgentManagementLifecycle(EventDispatcher<BuildServerListener> eventDispatcher) {
        rpcCaller = new RpcCaller();
        eventDispatcher.addListener(this);
    }

    @Override
    public void beforeBuildFinish(SRunningBuild runningBuild) {
        SBuildAgent agent = runningBuild.getAgent();
        DiskSpaceSummary diskSpaceSummary = rpcCaller.diskSpaceSummary(agent);

        if (diskSpaceSummary.getFreeSpace() < 1L * (1024 * 1024 * 1024)){
            agent.setEnabled(false, runningBuild.getOwner(), "Agent Disabled due to low disk space");
        }
    }
}