package sns.teamcity;

import jetbrains.buildServer.web.openapi.CustomTab;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.SimplePageExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AgentDiskSpaceTab extends SimplePageExtension implements CustomTab {

    private final AgentProvider agentProvider;

    public AgentDiskSpaceTab(ResourceLocator resourceLocator, PagePlaces pagePlaces, AgentProvider agentProvider) {
        super(pagePlaces, PlaceId.AGENTS_TAB, "agentDiskSpaceTab", resourceLocator.agentDiskSpaceTabJsp());
        this.agentProvider = agentProvider;
    }

    @Override
    public String getTabId() {
        return "agentDiskSpaceTab";
    }

    @Override
    public void fillModel(Map<String, Object> model, HttpServletRequest request) {
        super.fillModel(model, request);
        model.put("agents", agentProvider.getAgentInfos());
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
