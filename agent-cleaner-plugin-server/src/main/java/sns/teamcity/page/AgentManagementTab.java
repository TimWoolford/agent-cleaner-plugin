package sns.teamcity.page;

import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import sns.teamcity.ResourceLocator;

public class AgentManagementTab extends SimpleCustomTab {

    public AgentManagementTab(ResourceLocator resourceLocator, PagePlaces pagePlaces) {
        super(pagePlaces, PlaceId.AGENTS_TAB, "agentManagement", resourceLocator.agentManagementTabJsp(), "Agent Management");
        addJsFile(resourceLocator.tableSorter());
        addJsFile(resourceLocator.agentManagementJs());
        addCssFile(resourceLocator.agentManagementCss());
    }
}