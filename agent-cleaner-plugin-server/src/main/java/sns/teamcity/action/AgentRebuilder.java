package sns.teamcity.action;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.util.EventDispatcher;
import sns.teamcity.rpc.RpcCaller;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class AgentRebuilder extends BuildServerAdapter {
    private static final Logger LOG = Logger.getLogger("agent-cleaner-plugin");

    private final Set<SBuildAgent> rebuildingAgents = new HashSet<SBuildAgent>();
    private final RpcCaller rpcCaller;
    private final AgentDisabler agentDisabler;

    public AgentRebuilder(EventDispatcher<BuildServerListener> eventDispatcher, RpcCaller rpcCaller) {
        this.rpcCaller = rpcCaller;
        this.agentDisabler = new AgentDisabler("Automatically disabled for rebuild");
        eventDispatcher.addListener(this);
    }

    @Override
    public void buildFinished(SRunningBuild runningBuild) {
        Optional<SBuildAgent> optional = optionalAgent(runningBuild.getAgent());
        if (optional.isPresent()) {
            doAction(runningBuild.getAgent(), null);
        }
    }

    @Override
    public void agentRegistered(SBuildAgent registeredAgent, long currentlyRunningBuildId) {
        LOG.info(registeredAgent.getName() + " registered");
        Optional<SBuildAgent> optionalAgent = optionalAgent(registeredAgent);
        if (optionalAgent.isPresent()) {
            SBuildAgent agent = optionalAgent.get();
            LOG.info("attempting to re-enable");
            agentDisabler.enable(registeredAgent, null);
            rebuildingAgents.remove(agent);
        }
    }

    public void rebuild(SBuildAgent agent, SUser user) {
        doAction(agent, user);
    }

    public void cancel(SBuildAgent agent, SUser user) {

    }

    public boolean hasPendingRebuild(SBuildAgent agent) {
        return optionalAgent(agent).isPresent();
    }

    private void doAction(SBuildAgent agent, SUser user) {
        LOG.info("initialising rebuild " + agent.getName());
        agentDisabler.disable(agent, user);
        if (agent.getRunningBuild() == null) {
            LOG.info("no running build so.... ooomph!");
            rpcCaller.rebuildAgent(agent);
        }
        rebuildingAgents.add(agent);
    }

    private com.google.common.base.Optional<SBuildAgent> optionalAgent(final SBuildAgent currentAgent) {
        return Iterables.tryFind(rebuildingAgents, new Predicate<SBuildAgent>() {
            @Override
            public boolean apply(SBuildAgent sBuildAgent) {
                return sBuildAgent.getId() == currentAgent.getId();
            }
        });
    }
}