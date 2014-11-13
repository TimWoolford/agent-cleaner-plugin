package sns.teamcity.rpc;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;
import sns.teamcity.model.DiskSpaceSummary;
import sns.teamcity.model.DiskUsage;
import sns.teamcity.rpc.result.DiskSpaceSummaryTransformer;
import sns.teamcity.rpc.result.DiskUsageTransformer;
import sns.teamcity.rpc.result.ResultTransformer;
import sns.teamcity.rpc.result.SuccessTransformer;

import java.util.Hashtable;

public class RpcCaller {
    private final XmlRpcFactory rpcFactory;

    public RpcCaller() {
        rpcFactory = XmlRpcFactory.getInstance();
    }

    public DiskSpaceSummary diskSpaceSummary(SBuildAgent agent) {
        return callAndTransformResult(agent, new DiskSpaceSummaryTransformer(), RpcMethod.diskSpaceSummary);
    }

    public boolean rebuildAgent(SBuildAgent agent) {
        return callAndTransformResult(agent, new SuccessTransformer(), RpcMethod.rebuild);
    }

    public boolean cancelRebuild(SBuildAgent agent) {
        return callAndTransformResult(agent, new SuccessTransformer(), RpcMethod.cancel);
    }

    public boolean hasPendingRebuild(SBuildAgent agent) {
        return callAndTransformResult(agent, new SuccessTransformer(), RpcMethod.hasPendingRebuild);
    }

    public DiskUsage diskUsage(SBuildAgent agent) {
        return callAndTransformResult(agent, new DiskUsageTransformer(), RpcMethod.usage);
    }

    public boolean cleanAppDirs(SBuildAgent agent) {
        return callAndTransformResult(agent, new SuccessTransformer(), RpcMethod.cleanSnsAppDirs);
    }

    public boolean cleanMavenRepo(SBuildAgent agent) {
        return callAndTransformResult(agent, new SuccessTransformer(), RpcMethod.cleanMavenRepository);
    }

    private <T> T callAndTransformResult(SBuildAgent agent, ResultTransformer<T> transformer, RpcMethod rpcMethod) {
        XmlRpcTarget xmlRpcTarget = rpcFactory.create(urlFor(agent), "TeamCity Agent", rpcMethod.timeout(), false);

        return transformer.transform(call(rpcMethod, xmlRpcTarget));
    }

    @SuppressWarnings("unchecked")
    private Hashtable<String, String> call(RpcMethod rpcMethod, XmlRpcTarget xmlRpcTarget) {
        return (Hashtable<String, String>) xmlRpcTarget.call(rpcMethod.method(), rpcMethod.params());
    }

    private String urlFor(SBuildAgent buildAgent) {
        return String.format("http://%s:%d", buildAgent.getHostAddress(), buildAgent.getPort());
    }
}