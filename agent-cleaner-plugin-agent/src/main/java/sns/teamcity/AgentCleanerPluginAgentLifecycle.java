package sns.teamcity;

import jetbrains.buildServer.XmlRpcHandlerManager;
import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.util.EventDispatcher;

import static sns.teamcity.rpc.Handler.*;

public class AgentCleanerPluginAgentLifecycle extends AgentLifeCycleAdapter {

    private final DiskSpaceSummariser diskSpaceSummariser;
    private final AgentRebuilder agentRebuilder;
    private final AgentCleaner agentCleaner;
    private final DiskUsageProvider diskUsageProvider;

    public AgentCleanerPluginAgentLifecycle(EventDispatcher<AgentLifeCycleListener> eventDispatcher,
                                            DiskSpaceSummariser diskSpaceSummariser,
                                            AgentRebuilder agentRebuilder,
                                            AgentCleaner agentCleaner,
                                            DiskUsageProvider diskUsageProvider) {
        this.diskSpaceSummariser = diskSpaceSummariser;
        this.agentRebuilder = agentRebuilder;
        this.agentCleaner = agentCleaner;
        this.diskUsageProvider = diskUsageProvider;
        eventDispatcher.addListener(this);
    }

    @Override
    public void agentStarted(BuildAgent agent) {
        XmlRpcHandlerManager rpcHandlerManager = agent.getXmlRpcHandlerManager();

        rpcHandlerManager.addHandler(AgentDiskSpace.id(), diskSpaceSummariser);
        rpcHandlerManager.addHandler(RebuildAgent.id(), agentRebuilder);
        rpcHandlerManager.addHandler(CleanAgent.id(), agentCleaner);
        rpcHandlerManager.addHandler(DiskUsage.id(), diskUsageProvider);
    }
}