package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.AgentProvider;
import sns.teamcity.action.Action;
import sns.teamcity.action.AgentRebuilder;
import sns.teamcity.model.ViewBuilder;
import sns.teamcity.server.AgentCleaner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgentActionController extends BaseController {

    private final BuildAgentManager buildAgentManager;
    private final AgentRebuilder agentRebuilder;
    private final AgentCleaner agentCleaner;
    private final ViewBuilder viewBuilder;
    private final AgentProvider agentProvider;

    public AgentActionController(WebControllerManager webControllerManager, BuildAgentManager buildAgentManager, AgentRebuilder agentRebuilder, AgentCleaner agentCleaner, AgentProvider agentProvider, ViewBuilder viewBuilder) {
        this.buildAgentManager = buildAgentManager;
        this.agentRebuilder = agentRebuilder;
        this.agentCleaner = agentCleaner;
        this.agentProvider = agentProvider;
        this.viewBuilder = viewBuilder;
        webControllerManager.registerController("/agentManagement/action/", this);
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SUser user = SessionUser.getUser(request);
        Action action = Action.valueOf(request.getParameter("action"));
        Integer agentId = safeInteger(request, "agentId");

        SBuildAgent agent = buildAgentManager.findAgentById(agentId, false);

        switch (action) {
            case rebuild:
                agentRebuilder.rebuild(agent, user);
                break;
            case cancelRebuild:
                agentRebuilder.cancel(agent, user);
                break;
            case cleanAppDirs:
                agentCleaner.cleanAppsAndLogs(agent);
                break;
            case cleanMavenRepo:
                agentCleaner.cleanMavenRepo(agent);
                break;
        }

        return viewBuilder.buildView(agentProvider.getAgentDetail(agentId));
    }

    private Integer safeInteger(HttpServletRequest request, String paramName) {
        String parameter = request.getParameter(paramName);
        return StringUtils.isEmpty(parameter) ? null : Integer.valueOf(parameter);
    }
}
