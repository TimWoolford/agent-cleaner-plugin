package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.AgentProvider;
import sns.teamcity.model.AgentSummary;
import sns.teamcity.model.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AgentSummaryController extends BaseController {
    private final AgentProvider agentProvider;
    private final ViewBuilder viewBuilder;

    public AgentSummaryController(AgentProvider agentProvider, WebControllerManager webControllerManager, ViewBuilder viewBuilder) {
        this.agentProvider = agentProvider;
        this.viewBuilder = viewBuilder;
        webControllerManager.registerController("/agentManagement/agents/", this);
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return viewBuilder.buildView(agentProvider.getAgentSummaries());
    }
}