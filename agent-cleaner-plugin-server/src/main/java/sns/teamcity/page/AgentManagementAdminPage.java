package sns.teamcity.page;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.web.openapi.Groupable;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import sns.teamcity.ResourceLocator;

import javax.servlet.http.HttpServletRequest;

public class AgentManagementAdminPage extends AdminPage {

    public AgentManagementAdminPage(@NotNull PagePlaces pagePlaces, @NotNull ResourceLocator resourceLocator) {
        super(pagePlaces, "agent-cleaner-plugin", resourceLocator.agentManagementAdminJsp(), "Agent Management");
        setPosition(PositionConstraint.last());
    }

    @NotNull
    @Override
    public String getGroup() {
        return Groupable.SERVER_RELATED_GROUP;
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.ADMINISTER_AGENT);
    }
}
