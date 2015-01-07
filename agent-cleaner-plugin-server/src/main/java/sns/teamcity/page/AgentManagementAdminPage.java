package sns.teamcity.page;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.AuthUtil;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.auth.SecurityContext;
import jetbrains.buildServer.web.openapi.Groupable;
import jetbrains.buildServer.web.openapi.PagePlaces;
import org.jetbrains.annotations.NotNull;
import sns.teamcity.ResourceLocator;

public class AgentManagementAdminPage extends AdminPage {

    private final SecurityContext securityContext;

    public AgentManagementAdminPage(@NotNull PagePlaces pagePlaces, @NotNull ResourceLocator resourceLocator, @NotNull SecurityContext securityContext) {
        super(pagePlaces, "agent-cleaner-plugin", resourceLocator.agentManagementAdminJsp(), "Agent Management");
        this.securityContext = securityContext;
    }

    @NotNull
    @Override
    public String getGroup() {
        return Groupable.SERVER_RELATED_GROUP;
    }

    @Override
    public boolean isVisible() {
        return AuthUtil.hasGlobalPermission(securityContext.getAuthorityHolder(), Permission.ADMINISTER_AGENT);
    }
}
