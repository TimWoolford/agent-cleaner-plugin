package sns.teamcity;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import sns.teamcity.model.AgentInfo;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

import static com.google.common.base.Functions.forMap;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Maps.newHashMap;

public class AgentProvider {
    private final SBuildServer server;
    private final RpcCaller rpc;
    private final AgentComparators agentComparators;

    public AgentProvider(SBuildServer server) {
        this.server = server;
        rpc = new RpcCaller();
        agentComparators = new AgentComparators();
    }

    public List<AgentInfo> getAgentInfos(String sortId, Boolean sortAsc) {
        List<AgentInfo> agentInfos = transform(server.getBuildAgentManager().getRegisteredAgents(), toAgentInto());

        return Ordering.from(agentComparators.forId(sortId, sortAsc)).immutableSortedCopy(agentInfos);
    }

    private Function<SBuildAgent, AgentInfo> toAgentInto() {
        return new Function<SBuildAgent, AgentInfo>() {

            @Override
            public AgentInfo apply(SBuildAgent sBuildAgent) {
                return new AgentInfo(sBuildAgent.getName(), rpc.diskSpaceSummary(sBuildAgent));
            }
        };
    }

}