package sns.teamcity.rpc;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;
import sns.teamcity.model.DiskSpaceSummary;
import sns.teamcity.model.DiskUsage;
import sns.teamcity.rpc.method.*;

import java.util.Hashtable;

public class RpcCaller {
    private final XmlRpcFactory rpcFactory;

    public RpcCaller() {
        this.rpcFactory = XmlRpcFactory.getInstance();
    }

    public DiskSpaceSummary diskSpaceSummary(SBuildAgent agent) {
        return callAndTransformResult(agent, new DiskSpaceSummaryMethod());
    }

    public boolean rebuildAgent(SBuildAgent agent) {
        return callAndTransformResult(agent, new RebuildMethod());
    }

    public boolean cancelRebuild(SBuildAgent agent) {
        return callAndTransformResult(agent, new CancelRebuildMethod());
    }

    public boolean hasPendingRebuild(SBuildAgent agent) {
        return callAndTransformResult(agent, new HasPendingRebuildMethod());
    }

    public DiskUsage diskUsage(SBuildAgent agent) {
        return callAndTransformResult(agent, new DiskUsageMethod("/data/apps", "/logs/apps", "${user.home}/.m2/repository", "${user.home}/.gradle"));
    }

    public boolean cleanAppDirs(SBuildAgent agent) {
        return callAndTransformResult(agent, new CleanDirectoriesMethod("/data/apps", "/logs/apps"));
    }

    public boolean cleanMavenRepo(SBuildAgent agent) {
        return callAndTransformResult(agent, new CleanDirectoriesMethod("${user.home}/.m2/repository", "${user.home}/.gradle"));
    }

    private <T> T callAndTransformResult(SBuildAgent agent, RpcMethod<T> rpcMethod) {
        XmlRpcTarget xmlRpcTarget = rpcFactory.create(urlFor(agent), "TeamCity Agent", rpcMethod.timeout(), false);

        Hashtable<String, String> result = call(rpcMethod, xmlRpcTarget);

        return rpcMethod.resultTransformer().transform(result);
    }

    @SuppressWarnings("unchecked")
    private Hashtable<String, String> call(RpcMethod rpcMethod, XmlRpcTarget xmlRpcTarget) {
        return (Hashtable<String, String>) xmlRpcTarget.call(rpcMethod.method(), rpcMethod.params());
    }

    private String urlFor(SBuildAgent buildAgent) {
        return String.format("http://%s:%d", buildAgent.getHostAddress(), buildAgent.getPort());
    }
}