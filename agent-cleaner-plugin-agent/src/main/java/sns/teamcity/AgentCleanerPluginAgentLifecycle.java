package sns.teamcity;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.util.EventDispatcher;

import static sns.teamcity.rpc.Handlers.AgentDiskSpace;

public class AgentCleanerPluginAgentLifecycle extends AgentLifeCycleAdapter {

    private final DiskSpaceSummariser diskSpaceSummariser;

    public AgentCleanerPluginAgentLifecycle(EventDispatcher<AgentLifeCycleListener> eventDispatcher, DiskSpaceSummariser diskSpaceSummariser) {
        this.diskSpaceSummariser = diskSpaceSummariser;
        eventDispatcher.addListener(this);
    }

    @Override
    public void agentStarted(BuildAgent agent) {
        agent.getXmlRpcHandlerManager().addHandler(AgentDiskSpace.id(), diskSpaceSummariser);
    }
}