package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.AgentProvider;
import sns.teamcity.action.Action;
import sns.teamcity.action.Actionator;
import sns.teamcity.model.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgentActionController extends BaseController {

    private final Actionator actionator;
    private final ViewBuilder viewBuilder;
    private final AgentProvider agentProvider;

    public AgentActionController(WebControllerManager webControllerManager, Actionator actionator, AgentProvider agentProvider, ViewBuilder viewBuilder) {
        this.agentProvider = agentProvider;
        this.actionator = actionator;
        this.viewBuilder = viewBuilder;
        webControllerManager.registerController("/agentManagement/action/", this);
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SUser user = SessionUser.getUser(request);
        Action action = Action.valueOf(request.getParameter("action"));
        Integer agentId = safeInteger(request, "agentId");

        actionator.doAction(action, user, agentId);

        return viewBuilder.buildView(agentProvider.getAgentDetail(agentId));
    }

    private Integer safeInteger(HttpServletRequest request, String paramName) {
        String parameter = request.getParameter(paramName);
        return StringUtils.isEmpty(parameter) ? null : Integer.valueOf(parameter);
    }
}
