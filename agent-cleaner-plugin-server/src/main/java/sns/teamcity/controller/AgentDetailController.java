package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.AgentProvider;
import sns.teamcity.view.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgentDetailController extends BaseController {
    private final AgentProvider agentProvider;
    private final ViewBuilder viewBuilder;

    public AgentDetailController(AgentProvider agentProvider, ViewBuilder viewBuilder, WebControllerManager webControllerManager) {
        this.agentProvider = agentProvider;
        this.viewBuilder = viewBuilder;
        webControllerManager.registerController("/agentManagement/agentDetail/", this);
    }

    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        int agentId = Integer.valueOf(request.getParameter("agentId"));

        return viewBuilder.buildView(agentProvider.getAgentDetail(agentId));
    }
}