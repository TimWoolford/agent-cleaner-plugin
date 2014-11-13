package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.AgentProvider;
import sns.teamcity.view.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgentSummaryController extends BaseController {
    private final AgentProvider agentProvider;
    private final ViewBuilder viewBuilder;

    public AgentSummaryController(AgentProvider agentProvider, WebControllerManager webControllerManager, ViewBuilder viewBuilder) {
        this.agentProvider = agentProvider;
        this.viewBuilder = viewBuilder;
        webControllerManager.registerController("/agentManagement/agents/", this);
    }

    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        return viewBuilder.buildView(agentProvider.getAgentSummaries());
    }
}