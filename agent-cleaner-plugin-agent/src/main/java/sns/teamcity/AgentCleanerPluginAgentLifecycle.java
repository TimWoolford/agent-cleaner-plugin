package sns.teamcity;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.util.EventDispatcher;

import static sns.teamcity.rpc.Handlers.AgentDiskSpace;
import static sns.teamcity.rpc.Handlers.RebuildAgent;

public class AgentCleanerPluginAgentLifecycle extends AgentLifeCycleAdapter {

    private final DiskSpaceSummariser diskSpaceSummariser;
    private final AgentRebuilder agentRebuilder;

    public AgentCleanerPluginAgentLifecycle(EventDispatcher<AgentLifeCycleListener> eventDispatcher, DiskSpaceSummariser diskSpaceSummariser, AgentRebuilder agentRebuilder) {
        this.diskSpaceSummariser = diskSpaceSummariser;
        this.agentRebuilder = agentRebuilder;
        eventDispatcher.addListener(this);
    }

    @Override
    public void agentStarted(BuildAgent agent) {
        agent.getXmlRpcHandlerManager().addHandler(AgentDiskSpace.id(), diskSpaceSummariser);
        agent.getXmlRpcHandlerManager().addHandler(RebuildAgent.id(), agentRebuilder);
    }
}