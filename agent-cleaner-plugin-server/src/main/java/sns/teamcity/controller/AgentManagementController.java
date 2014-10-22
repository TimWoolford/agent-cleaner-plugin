package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.action.Action;
import sns.teamcity.action.Actionator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgentManagementController extends BaseController {

    private final Actionator actionator;

    public AgentManagementController(WebControllerManager webControllerManager, Actionator actionator) {
        webControllerManager.registerController("/agentManagement/action/", this);
        this.actionator = actionator;
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SUser user = SessionUser.getUser(request);
        Action action = Action.valueOf(request.getParameter("action"));
        Integer agentId = safeInteger(request, "agentId");

        actionator.doAction(action, user, agentId);

        return simpleView("OK");
    }

    private Integer safeInteger(HttpServletRequest request, String paramName) {
        String parameter = request.getParameter(paramName);
        return StringUtils.isEmpty(parameter) ? null : Integer.valueOf(parameter);
    }
}
