package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.action.Action;
import sns.teamcity.action.AgentDisabler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BulkActionController extends BaseController {

    private final BuildAgentManager buildAgentManager;
    private final AgentDisabler agentDisabler;

    public BulkActionController(WebControllerManager webControllerManager, BuildAgentManager buildAgentManager) {
        this.buildAgentManager = buildAgentManager;
        this.agentDisabler = new AgentDisabler("Disabled by plugin");
        webControllerManager.registerController("/agentManagement/bulkAction/", this);
    }

    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        SUser user = SessionUser.getUser(request);
        Action action = Action.valueOf(request.getParameter("action"));

        List<SBuildAgent> registeredAgents = buildAgentManager.getRegisteredAgents();
        switch (action) {
            case disable:
                for (SBuildAgent registeredAgent : registeredAgents) {
                    agentDisabler.disable(registeredAgent, user);
                }
                break;
            case enable:
                for (SBuildAgent registeredAgent : registeredAgents) {
                    agentDisabler.enable(registeredAgent, user);
                }
                break;
        }

        return simpleView("OK");
    }
}
