package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.AgentProvider;
import sns.teamcity.model.ViewBuilder;

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
    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        int agentId = Integer.valueOf(httpServletRequest.getParameter("agentId"));

        return viewBuilder.buildView(agentProvider.getAgentDetail(agentId));
    }
}
