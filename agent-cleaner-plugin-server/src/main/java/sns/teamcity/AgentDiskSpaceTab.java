package sns.teamcity;

import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AgentDiskSpaceTab extends SimpleCustomTab{

    private final AgentProvider agentProvider;

    public AgentDiskSpaceTab(ResourceLocator resourceLocator, PagePlaces pagePlaces, AgentProvider agentProvider) {
        super(pagePlaces, PlaceId.AGENTS_TAB, "agentDiskSpace", resourceLocator.agentDiskSpaceTabJsp(), "Disk Space");
        this.agentProvider = agentProvider;
    }

    @Override
    public void fillModel(Map<String, Object> model, HttpServletRequest request) {
        super.fillModel(model, request);
        model.put("agents", agentProvider.getAgentInfos());
    }
}