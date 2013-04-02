package sns.teamcity;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.xmlrpc.XmlRpcFactory;
import jetbrains.buildServer.xmlrpc.XmlRpcTarget;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static sns.teamcity.rpc.Handlers.AgentDiskSpace;

public class AppServer extends BaseController {

    private final SBuildAgent buildAgent;
    private final WebControllerManager webControllerManager;
    private final PluginDescriptor pluginDescriptor;

    public AppServer(WebControllerManager webControllerManager, SBuildServer server, PluginDescriptor pluginDescriptor) {
        this.webControllerManager = webControllerManager;
        this.pluginDescriptor = pluginDescriptor;
        this.buildAgent = server.getBuildAgentManager().findAgentById(1, true);
    }

    public void register() {
        webControllerManager.registerController("/cleaner/*", this);
    }

    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        DiskSpaceSummaryParser diskSpaceSummary = getDiskSpaceSummary();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("freeSpace", diskSpaceSummary.freeSpace());
        map.put("totalSpace", diskSpaceSummary.totalSpace());
        return new ModelAndView(pluginDescriptor.getPluginResourcesPath("test.jsp"), map);
    }

    private DiskSpaceSummaryParser getDiskSpaceSummary() {
        XmlRpcTarget xmlRpcTarget = XmlRpcFactory.getInstance().create(urlFor(buildAgent), "TeamCity Agent", 5000, false);

        return new DiskSpaceSummaryParser((Hashtable<String, String>) xmlRpcTarget.call(AgentDiskSpace.method("diskSpaceSummary"), new Object[]{}));
    }

    private String urlFor(SBuildAgent buildAgent) {
        return "http://" + buildAgent.getHostAddress() + ":" + buildAgent.getPort();
    }
}