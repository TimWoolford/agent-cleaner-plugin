package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;
import sns.teamcity.persistence.ConfigPersistence;
import sns.teamcity.view.ViewBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminController extends BaseController {

    private final ConfigPersistence configPersistence;
    private final ViewBuilder viewBuilder;

    public AdminController(WebControllerManager controllerManager, ConfigPersistence configPersistence, ViewBuilder viewBuilder) {
        this.configPersistence = configPersistence;
        this.viewBuilder = viewBuilder;
        controllerManager.registerController("/agentManagement/config/", this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) throws Exception {
        switch (httpServletRequest.getMethod()) {
            case "GET":
                return viewBuilder.buildView(configPersistence.getAgentDirectories().getAgentDirectories());
            default:
                throw new IllegalArgumentException(String.format("No controller for '%s' method", httpServletRequest.getMethod()));
        }
    }
}
