package sns.teamcity.rpc;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;
import sns.teamcity.DiskSpaceSummary;
import sns.teamcity.DiskUsage;

import java.util.Hashtable;

public class RpcCaller {
    private final XmlRpcFactory rpcFactory;

    public RpcCaller() {
        rpcFactory = XmlRpcFactory.getInstance();
    }

    public DiskSpaceSummary diskSpaceSummary(SBuildAgent agent) {
        return doTheThing(agent, new DiskSpaceSummaryTransformer(), RpcMethod.diskSpaceSummary);
    }

    public boolean rebuildAgent(SBuildAgent agent) {
        return doTheThing(agent, new SuccessTransformer(), RpcMethod.rebuild);
    }

    public boolean cancelRebuild(SBuildAgent agent) {
        return doTheThing(agent, new SuccessTransformer(), RpcMethod.cancel);
    }

    public boolean hasPendingRebuild(SBuildAgent agent) {
        return doTheThing(agent, new SuccessTransformer(), RpcMethod.hasPendingRebuild);
    }

    public DiskUsage diskUsage(SBuildAgent agent) {
        return doTheThing(agent, new DiskUsageTransformer(), RpcMethod.usage);
    }

    public boolean cleanAppDirs(SBuildAgent agent) {
        return doTheThing(agent, new SuccessTransformer(), RpcMethod.cleanSnsAppDirs);
    }

    public boolean cleanMavenRepo(SBuildAgent agent) {
        return doTheThing(agent, new SuccessTransformer(), RpcMethod.cleanMavenRepository);
    }

    @SuppressWarnings("unchecked")
    private <T> T doTheThing(SBuildAgent agent, ResultTransformer<T> transformer, RpcMethod rpcMethod) {
        XmlRpcTarget xmlRpcTarget = rpcFactory.create(urlFor(agent), "TeamCity Agent", 2000, false);
        return transformer.transform((Hashtable<String, String>) xmlRpcTarget.call(rpcMethod.method(), new Object[]{}));
    }

    private String urlFor(SBuildAgent buildAgent) {
        return String.format("http://%s:%d", buildAgent.getHostAddress(), buildAgent.getPort());
    }

    private interface ResultTransformer<T> {
        T transform(Hashtable<String, String> hashTable);
    }

    private static class SuccessTransformer implements ResultTransformer<Boolean> {
        @Override
        public Boolean transform(Hashtable<String, String> hashTable) {
            return Boolean.valueOf(hashTable.get("success"));
        }
    }

    private static class DiskUsageTransformer implements ResultTransformer<DiskUsage> {
        @Override
        public DiskUsage transform(Hashtable<String, String> hashTable) {
            return new DiskUsage(hashTable);
        }
    }

    private static class DiskSpaceSummaryTransformer implements ResultTransformer<DiskSpaceSummary> {
        @Override
        public DiskSpaceSummary transform(Hashtable<String, String> hashTable) {
            return new DiskSpaceSummary(hashTable);
        }
    }
}