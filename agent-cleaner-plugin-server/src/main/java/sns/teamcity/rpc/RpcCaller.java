package sns.teamcity.rpc;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;
import sns.teamcity.DiskSpaceSummary;

import java.util.Hashtable;

import static sns.teamcity.rpc.Handlers.AgentDiskSpace;

public class RpcCaller {
    private XmlRpcFactory rpcFactory = XmlRpcFactory.getInstance();

    public DiskSpaceSummary diskSpaceSummary(SBuildAgent buildAgent) {
        XmlRpcTarget xmlRpcTarget = rpcFactory.create(urlFor(buildAgent), "TeamCity Agent", 5000, false);

        return new DiskSpaceSummary((Hashtable<String, String>) xmlRpcTarget.call(AgentDiskSpace.method("diskSpaceSummary"), new Object[]{}));
    }

    private String urlFor(SBuildAgent buildAgent) {
        return String.format("http://%s:%d", buildAgent.getHostAddress(), buildAgent.getPort());
    }
}