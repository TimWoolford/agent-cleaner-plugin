package sns.teamcity.action;

import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.users.SUser;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

public class Actionactor {
    private static final String DISABLED_BY_PLUGIN_COMMENT = "Disabled by plugin";

    private final BuildAgentManager buildAgentManager;
    private final RpcCaller rpcCaller;

    public Actionactor(SBuildServer buildServer, RpcCaller rpcCaller) {
        this.rpcCaller = rpcCaller;
        this.buildAgentManager = buildServer.getBuildAgentManager();
    }

    public void doAction(Action action, SUser user, Integer agentId) {
        List<SBuildAgent> registeredAgents = buildAgentManager.getRegisteredAgents();
        switch (action) {
            case disable:
                for (SBuildAgent registeredAgent : registeredAgents) {
                    if (registeredAgent.isEnabled()) {
                        registeredAgent.setEnabled(false, user, DISABLED_BY_PLUGIN_COMMENT);
                    }
                }
                break;
            case enable:
                for (SBuildAgent registeredAgent : registeredAgents) {
                    if (!registeredAgent.isEnabled() && wasDisabledByPlugin(registeredAgent)) {
                        registeredAgent.setEnabled(true, user, "");
                    }
                }
                break;
            case rebuild:
                SBuildAgent agent = buildAgentManager.findAgentById(agentId, false);
                rpcCaller.rebuildAgent(agent);
                break;
        }
    }

    private boolean wasDisabledByPlugin(SBuildAgent registeredAgent) {
        String comment = registeredAgent.getStatusComment().getComment();
        return comment != null && comment.equals(DISABLED_BY_PLUGIN_COMMENT);
    }
}