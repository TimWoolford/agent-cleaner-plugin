package sns.teamcity;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SRunningBuild;
import sns.teamcity.action.AgentRebuilder;
import sns.teamcity.model.AgentDetail;
import sns.teamcity.model.AgentSummary;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

public class AgentProvider {
    private final RpcCaller rpc;
    private final AgentRebuilder agentRebuilder;
    private final BuildAgentManager buildAgentManager;

    public AgentProvider(SBuildServer server, RpcCaller rpcCaller, AgentRebuilder agentRebuilder) {
        this.rpc = rpcCaller;
        this.agentRebuilder = agentRebuilder;
        this.buildAgentManager = server.getBuildAgentManager();
    }

    public AgentDetail getAgentDetail(int agentId) {
        SBuildAgent sBuildAgent = buildAgentManager.findAgentById(agentId, false);

        return new AgentDetail(
                sBuildAgent.getId(),
                sBuildAgent.getName(),
                sBuildAgent.isEnabled(),
                sBuildAgent.getStatusComment(),
                rpc.hasPendingRebuild(sBuildAgent) || agentRebuilder.hasPendingRebuild(sBuildAgent),
                rpc.diskSpaceSummary(sBuildAgent),
                rpc.diskUsage(sBuildAgent),
                statusOf(sBuildAgent.getRunningBuild()));
    }

    public List<AgentSummary> getAgentSummaries() {
        return FluentIterable.from(buildAgentManager.getRegisteredAgents()).transform(toAgentSummary()).toList();
    }

    private String statusOf(SRunningBuild runningBuild) {
        if (runningBuild == null) {
            return "no-build";
        }
        Status buildStatus = runningBuild.getBuildStatus();
        if (buildStatus == Status.NORMAL) {
            return "green-build";
        }
        return "red-build";
    }

    private Function<SBuildAgent, AgentSummary> toAgentSummary() {
        return new Function<SBuildAgent, AgentSummary>() {
            @Override
            public AgentSummary apply(SBuildAgent sBuildAgent) {
                return new AgentSummary(
                        sBuildAgent.getId(),
                        sBuildAgent.getName(),
                        sBuildAgent.isEnabled(),
                        sBuildAgent.getStatusComment(),
                        sBuildAgent.getRegistrationTimestamp(),
                        statusOf(sBuildAgent.getRunningBuild()));
            }
        };
    }
}