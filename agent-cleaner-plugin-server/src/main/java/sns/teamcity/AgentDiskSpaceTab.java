package sns.teamcity;

import com.google.common.base.Function;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.*;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.transform;
import static sns.teamcity.rpc.Handlers.AgentDiskSpace;

public class AgentDiskSpaceTab extends SimplePageExtension implements CustomTab {

    private final SBuildServer server;

    public AgentDiskSpaceTab(SBuildServer server, PluginDescriptor pluginDescriptor, PagePlaces pagePlaces) {
        super(pagePlaces, PlaceId.AGENTS_TAB, "agentDiskSpaceTab", pluginDescriptor.getPluginResourcesPath("agentDiskSpaceTab.jsp"));
        this.server = server;
    }

    @Override
    public String getTabId() {
        return "agentDiskSpaceTab";
    }

    @Override
    public void fillModel(Map<String, Object> model, HttpServletRequest request) {
        super.fillModel(model, request);
        List<AgentInfo> agents = transform(server.getBuildAgentManager().getRegisteredAgents(), toAgentInto());
        model.put("agents", agents);
    }

    @Override
    public String getTabTitle() {
        return "Disk Space";
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    private static Function<SBuildAgent, AgentInfo> toAgentInto() {
        return new Function<SBuildAgent, AgentInfo>() {
            @Override
            public AgentInfo apply(SBuildAgent sBuildAgent) {
                return new AgentInfo(sBuildAgent.getName(), getDiskSpaceSummary(sBuildAgent));
            }

            private DiskSpaceSummary getDiskSpaceSummary(SBuildAgent buildAgent) {
                return RpcCaller.diskSpaceSummary(buildAgent);
            }

        };
    }

    public static class RpcCaller {
        public static DiskSpaceSummary diskSpaceSummary(SBuildAgent buildAgent) {
            XmlRpcTarget xmlRpcTarget = XmlRpcFactory.getInstance().create(urlFor(buildAgent), "TeamCity Agent", 5000, false);

            return new DiskSpaceSummary((Hashtable<String, String>) xmlRpcTarget.call(AgentDiskSpace.method("diskSpaceSummary"), new Object[]{}));
        }

        private static String urlFor(SBuildAgent buildAgent) {
            return "http://" + buildAgent.getHostAddress() + ":" + buildAgent.getPort();
        }
    }


    public static class AgentInfo {
        private final String name;
        private final DiskSpaceSummary diskSpaceSummary;

        public AgentInfo(String name, DiskSpaceSummary diskSpaceSummary) {
            this.name = name;
            this.diskSpaceSummary = diskSpaceSummary;
        }

        public String getName() {
            return name;
        }

        public DiskSpaceSummary getDiskSpaceSummary() {
            return diskSpaceSummary;
        }
    }
}
