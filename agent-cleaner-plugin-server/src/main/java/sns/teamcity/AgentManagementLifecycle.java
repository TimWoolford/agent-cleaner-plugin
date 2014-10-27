package sns.teamcity;

import com.google.common.collect.ImmutableList;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.util.EventDispatcher;
import org.apache.log4j.Logger;
import sns.teamcity.action.AgentDisabler;
import sns.teamcity.rpc.RpcCaller;

import java.util.List;

public class AgentManagementLifecycle extends BuildServerAdapter {
    private static final Logger LOG = Logger.getLogger("agent-cleaner-plugin");
    private static final double ONE_GIGABYTE = Math.pow(1024, 3);

    private RpcCaller rpcCaller;
    private List<CleanAction> actions;

    public AgentManagementLifecycle(EventDispatcher<BuildServerListener> eventDispatcher, RpcCaller rpcCaller) {
        this.rpcCaller = rpcCaller;
        eventDispatcher.addListener(this);
        actions = ImmutableList.of(
                new CleanAppDirs(rpcCaller),
                new CleanMavenRepo(rpcCaller),
                new DiableAgent(new AgentDisabler("Agent automatically disabled due to low disk space"))
        );
    }

    @Override
    public void beforeBuildFinish(SRunningBuild runningBuild) {
        SBuildAgent agent = runningBuild.getAgent();

        for (CleanAction action : actions) {
            if (rpcCaller.diskSpaceSummary(agent).getFreeSpace() < ONE_GIGABYTE) {
                action.doAction(agent);
            }
        }
    }

    private interface CleanAction {
        void doAction(SBuildAgent agent);
    }

    private static class CleanAppDirs implements CleanAction {
        private final RpcCaller rpcCaller;

        private CleanAppDirs(RpcCaller rpcCaller) {
            this.rpcCaller = rpcCaller;
        }

        @Override
        public void doAction(SBuildAgent agent) {
            LOG.info("Cleaning App Dirs to reclaim disk space");
            rpcCaller.cleanAppDirs(agent);
        }
    }

    private static class CleanMavenRepo implements CleanAction {
        private final RpcCaller rpcCaller;

        private CleanMavenRepo(RpcCaller rpcCaller) {
            this.rpcCaller = rpcCaller;
        }

        @Override
        public void doAction(SBuildAgent agent) {
            LOG.info("Cleaning Maven Repo to reclaim disk space");
            rpcCaller.cleanMavenRepo(agent);
        }
    }

    private static class DiableAgent implements CleanAction {
        private final AgentDisabler disabler;

        private DiableAgent(AgentDisabler disabler) {
            this.disabler = disabler;
        }

        @Override
        public void doAction(SBuildAgent agent) {
            LOG.warn("Disabling agent [" + agent.getName() + "]. Unable to reclaim disk space.");
            disabler.disable(agent, null);
        }
    }
}