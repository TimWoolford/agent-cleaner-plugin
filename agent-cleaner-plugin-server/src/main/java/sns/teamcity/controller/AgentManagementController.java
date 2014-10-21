package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.action.Action;
import sns.teamcity.action.Actionactor;
import sns.teamcity.rpc.RpcCaller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AgentManagementController extends BaseController {

    private final Actionactor actionactor;

    public AgentManagementController(WebControllerManager webControllerManager, Actionactor actionactor) {
        webControllerManager.registerController("/agentManagement/action/", this);
        this.actionactor = actionactor;
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SUser user = SessionUser.getUser(request);
        Action action = Action.valueOf(request.getParameter("action"));
        Integer agentId = Integer.valueOf(request.getParameter("agentId"));

        actionactor.doAction(action, user, agentId);

        return simpleView("OK");
    }

}
