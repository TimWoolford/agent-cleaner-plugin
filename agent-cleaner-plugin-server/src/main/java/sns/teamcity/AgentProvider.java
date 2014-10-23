package sns.teamcity;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import sns.teamcity.action.AgentRebuilder;
import sns.teamcity.model.AgentInfo;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

public class AgentProvider {
    private final SBuildServer server;
    private final RpcCaller rpc;
    private final AgentRebuilder agentRebuilder;
    private final AgentComparators agentComparators;

    public AgentProvider(SBuildServer server, RpcCaller rpcCaller, AgentRebuilder agentRebuilder) {
        this.server = server;
        this.rpc = rpcCaller;
        this.agentRebuilder = agentRebuilder;
        this.agentComparators = new AgentComparators();
    }

    public List<AgentInfo> getAgentInfos(String sortId, Boolean sortAsc) {
        List<AgentInfo> agentInfos = FluentIterable.from(server.getBuildAgentManager().getRegisteredAgents()).transform(toAgentInfo()).toList();

        return Ordering.from(agentComparators.forId(sortId, sortAsc)).immutableSortedCopy(agentInfos);
    }

    private Function<SBuildAgent, AgentInfo> toAgentInfo() {
        return new Function<SBuildAgent, AgentInfo>() {

            @Override
            public AgentInfo apply(SBuildAgent sBuildAgent) {
                return new AgentInfo(
                        sBuildAgent.getId(),
                        sBuildAgent.getName(),
                        rpc.diskSpaceSummary(sBuildAgent),
                        sBuildAgent.isEnabled(),
                        sBuildAgent.getStatusComment(),
                        sBuildAgent.getRegistrationTimestamp(),
                        rpc.hasPendingRebuild(sBuildAgent) || agentRebuilder.hasPendingRebuild(sBuildAgent),
                        rpc.diskUsage(sBuildAgent)
                );
            }
        };
    }
}