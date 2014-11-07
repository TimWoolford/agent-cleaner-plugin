package sns.teamcity.page;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.web.openapi.Groupable;
import jetbrains.buildServer.web.openapi.PagePlaces;
import org.jetbrains.annotations.NotNull;
import sns.teamcity.ResourceLocator;

public class AgentManagementAdminPage extends AdminPage {
    public AgentManagementAdminPage(@NotNull PagePlaces pagePlaces, @NotNull ResourceLocator resourceLocator) {
        super(pagePlaces, "agent-cleaner-plugin", resourceLocator.agentManagementAdminJsp(), "Agent Management");
    }

    @NotNull
    @Override
    public String getGroup() {
        return Groupable.SERVER_RELATED_GROUP;
    }
}
