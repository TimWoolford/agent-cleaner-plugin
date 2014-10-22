package sns.teamcity.action;

import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.users.SUser;

import java.util.List;

public class Actionator {
    private final BuildAgentManager buildAgentManager;
    private final AgentRebuilder agentRebuilder;
    private final AgentDisabler agentDisabler;

    public Actionator(SBuildServer buildServer, AgentRebuilder agentRebuilder) {
        this.agentRebuilder = agentRebuilder;
        this.buildAgentManager = buildServer.getBuildAgentManager();
        agentDisabler = new AgentDisabler("Disabled by plugin");
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
        }
    }
}