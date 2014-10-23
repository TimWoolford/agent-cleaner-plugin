package sns.teamcity.action;

import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.users.SUser;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

public class Actionator {
    private final BuildAgentManager buildAgentManager;
    private final AgentRebuilder agentRebuilder;
    private final AgentDisabler agentDisabler;
    private final RpcCaller rpcCaller;

    public Actionator(SBuildServer buildServer, AgentRebuilder agentRebuilder, RpcCaller rpcCaller) {
        this.agentRebuilder = agentRebuilder;
        this.rpcCaller = rpcCaller;
        this.buildAgentManager = buildServer.getBuildAgentManager();
        this.agentDisabler = new AgentDisabler("Disabled by plugin");
    }

    public void doAction(Action action, SUser user, Integer agentId) {
        List<SBuildAgent> registeredAgents = buildAgentManager.getRegisteredAgents();
        switch (action) {
            case disable:
                for (SBuildAgent registeredAgent : registeredAgents) {
                    agentDisabler.disable(registeredAgent, user);
                }
                break;
            case enable:
                for (SBuildAgent registeredAgent : registeredAgents) {
                    agentDisabler.enable(registeredAgent, user);
                }
                break;
            case rebuild:
                agentRebuilder.rebuild(buildAgentManager.findAgentById(agentId, false), user);
                break;
            case cancelRebuild:
                agentRebuilder.cancel(buildAgentManager.findAgentById(agentId, false), user);
                break;
            case cleanAppDirs:
                rpcCaller.cleanAppDirs(buildAgentManager.findAgentById(agentId, false));
                break;
            case cleanMavenRepo:
                rpcCaller.cleanMavenRepo(buildAgentManager.findAgentById(agentId, false));
                break;
        }
    }
}