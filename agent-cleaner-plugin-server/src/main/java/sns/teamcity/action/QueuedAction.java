package sns.teamcity.action;

import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.util.EventDispatcher;
import sns.teamcity.rpc.RpcCaller;

public class QueuedAction extends BuildServerAdapter {
    private final SBuildAgent agent;
    private final RpcCaller rpcCaller;
    private final EventDispatcher<BuildServerListener> eventDispatcher;

    public QueuedAction(SBuildAgent agent, RpcCaller rpcCaller, EventDispatcher<BuildServerListener> eventDispatcher) {
        this.agent = agent;
        this.rpcCaller = rpcCaller;
        this.eventDispatcher = eventDispatcher;
        this.eventDispatcher.addListener(this);
    }

    @Override
    public void beforeBuildFinish(SRunningBuild runningBuild) {
        if (isForAgent(runningBuild.getAgent())) {
            agent.setEnabled(false, null, "Disabled for rebuild");
            rpcCaller.rebuildAgent(agent);
        }
    }

    @Override
    public void agentRegistered(SBuildAgent agent, long currentlyRunningBuildId) {
        if (isForAgent(agent)) {
            if (agent.isEnabled()) {
                agent.setEnabled(true, null, "");
            }
            eventDispatcher.removeListener(this);
        }
    }

    private boolean isForAgent(SBuildAgent agent1) {
        return agent1.getId() == agent.getId();
    }
}