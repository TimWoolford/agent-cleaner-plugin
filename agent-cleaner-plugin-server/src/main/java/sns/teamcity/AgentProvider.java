package sns.teamcity;

import com.google.common.base.Function;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import sns.teamcity.model.AgentInfo;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

import static com.google.common.collect.Lists.transform;

public class AgentProvider {
    private final SBuildServer server;
    private final RpcCaller rpc;

    public AgentProvider(SBuildServer server) {
        this.server = server;
        rpc = new RpcCaller();
    }

    public List<AgentInfo> getAgentInfos() {
        return transform(server.getBuildAgentManager().getRegisteredAgents(), toAgentInto());
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