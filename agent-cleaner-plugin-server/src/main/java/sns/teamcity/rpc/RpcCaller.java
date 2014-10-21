package sns.teamcity.rpc;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;
import sns.teamcity.DiskSpaceSummary;

import java.util.Hashtable;

import static sns.teamcity.rpc.Handlers.AgentDiskSpace;
import static sns.teamcity.rpc.Handlers.RebuildAgent;

public class RpcCaller {
    private final XmlRpcFactory rpcFactory;

    public RpcCaller() {
        rpcFactory = XmlRpcFactory.getInstance();
    }

    public DiskSpaceSummary diskSpaceSummary(SBuildAgent buildAgent) {
        XmlRpcTarget xmlRpcTarget = rpcFactory.create(urlFor(buildAgent), "TeamCity Agent", 5000, false);

        return new DiskSpaceSummary((Hashtable<String, String>) xmlRpcTarget.call(AgentDiskSpace.method("diskSpaceSummary"), new Object[]{}));
    }

    private String urlFor(SBuildAgent buildAgent) {
        return String.format("http://%s:%d", buildAgent.getHostAddress(), buildAgent.getPort());
    }

    public void rebuildAgent(SBuildAgent agent) {
        rpcFactory.create(urlFor(agent), "TeamCity Agent", 2000, false).call(RebuildAgent.method("rebuild"), new Object[]{});
    }
}