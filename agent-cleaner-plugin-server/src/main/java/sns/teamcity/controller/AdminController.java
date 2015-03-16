package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.model.AgentConfigurations;
import sns.teamcity.persistence.ConfigPersistence;
import sns.teamcity.view.JsonWrapper;
import sns.teamcity.view.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminController extends BaseController {

    private final ConfigPersistence configPersistence;
    private final ViewBuilder viewBuilder;
    private final JsonWrapper json;

    public AdminController(WebControllerManager controllerManager,
                           ConfigPersistence configPersistence,
                           ViewBuilder viewBuilder,
                           JsonWrapper json) {
        this.configPersistence = configPersistence;
        this.viewBuilder = viewBuilder;
        this.json = json;
        controllerManager.registerController("/agentManagement/config/", this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse httpServletResponse) throws Exception {

        if (isPost(request)) {
            String content = IOUtils.toString(request.getReader());
            AgentConfigurations agentConfigurations = json.fromString(content, AgentConfigurations.class);
            configPersistence.updateData(agentConfigurations);
        }

        return viewBuilder.buildView(configPersistence.getAgentConfigurations().getAgentConfigurations());
    }
}
