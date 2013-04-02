package sns.teamcity;

import jetbrains.buildServer.web.openapi.*;

public class AgentDiskSpaceTab  extends SimplePageExtension implements CustomTab {
    public AgentDiskSpaceTab(PagePlaces pagePlaces, PluginDescriptor pluginDescriptor) {
        super(pagePlaces, PlaceId.AGENTS_TAB, "meme", pluginDescriptor.getPluginResourcesPath("agentDiskSpaceTab.jsp"));
    }

    @Override
    public String getTabId() {
        return "agentDiskSpaceTab";
    }


    @Override
    public String getTabTitle() {
        return "Disk Space";
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
