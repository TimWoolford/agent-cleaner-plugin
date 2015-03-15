package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.model.AgentDirectories;
import sns.teamcity.persistence.ConfigPersistence;
import sns.teamcity.view.Jsoniser;
import sns.teamcity.view.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminController extends BaseController {

    private final ConfigPersistence configPersistence;
    private final ViewBuilder viewBuilder;
    private final Jsoniser jsoniser;

    public AdminController(WebControllerManager controllerManager,
                           ConfigPersistence configPersistence,
                           ViewBuilder viewBuilder,
                           Jsoniser jsoniser) {
        this.configPersistence = configPersistence;
        this.viewBuilder = viewBuilder;
        this.jsoniser = jsoniser;
        controllerManager.registerController("/agentManagement/config/", this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse httpServletResponse) throws Exception {
        switch (request.getMethod()) {
            case "GET":
                return viewBuilder.buildView(configPersistence.getAgentDirectories().getAgentDirectories());
            case "POST":
                String content = IOUtils.toString(request.getReader());
                AgentDirectories agentDirectories = jsoniser.fromString(content, AgentDirectories.class);
                System.err.println(agentDirectories);
                return simpleView("OK");
            default:
                throw new IllegalArgumentException(String.format("No controller for '%s' method", request.getMethod()));
        }
    }
}
