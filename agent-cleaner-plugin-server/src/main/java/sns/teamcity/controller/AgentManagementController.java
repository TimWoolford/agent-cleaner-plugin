package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.rpc.RpcCaller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AgentManagementController extends BaseController {

    private static final String DISABLED_BY_PLUGIN_COMMENT = "Disabled by plugin";
    private final BuildAgentManager buildAgentManager;
    private final RpcCaller rpcCaller;

    public AgentManagementController(SBuildServer buildServer,
                                     WebControllerManager webControllerManager,
                                     RpcCaller rpcCaller) {
        this.rpcCaller = rpcCaller;
        buildAgentManager = buildServer.getBuildAgentManager();
        webControllerManager.registerController("/agentManagement/action/", this);
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SBuildAgent> registeredAgents = buildAgentManager.getRegisteredAgents();
        SUser user = SessionUser.getUser(request);
        String action = request.getParameter("action");

        if ("disable".equals(action)) {
            for (SBuildAgent registeredAgent : registeredAgents) {
                if (registeredAgent.isEnabled()) {
                    registeredAgent.setEnabled(false, user, DISABLED_BY_PLUGIN_COMMENT);
                }
            }
        } else if ("enable".equals(action)) {
            for (SBuildAgent registeredAgent : registeredAgents) {
                if (!registeredAgent.isEnabled() && wasDisabledByPlugin(registeredAgent)) {
                    registeredAgent.setEnabled(true, user, "");
                }
            }
        } else if ("rebuild".equals(action)) {
            SBuildAgent agent = buildAgentManager.findAgentById(Integer.valueOf(request.getParameter("agentId")), false);
            rpcCaller.rebuildAgent(agent);
        } else {
            throw new UnsupportedOperationException(String.format("Action [%s] not supported.", action));
        }

        return simpleView("OK");
    }

    private boolean wasDisabledByPlugin(SBuildAgent registeredAgent) {
        String comment = registeredAgent.getStatusComment().getComment();
        return comment != null && comment.equals(DISABLED_BY_PLUGIN_COMMENT);
    }
}
