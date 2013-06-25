package sns.teamcity;

import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AgentManagementTab extends SimpleCustomTab {

    private final AgentProvider agentProvider;

    public AgentManagementTab(ResourceLocator resourceLocator, PagePlaces pagePlaces, AgentProvider agentProvider) {
        super(pagePlaces, PlaceId.AGENTS_TAB, "agentManagement", resourceLocator.agentDiskSpaceTabJsp(), "Agent Management");
        addJsFile(resourceLocator.progressBarJs());
        this.agentProvider = agentProvider;
    }

    @Override
    public void fillModel(Map<String, Object> model, HttpServletRequest request) {
        super.fillModel(model, request);
        String sortBy = request.getParameter("sortBy");
        Boolean sortAsc = Boolean.valueOf(request.getParameter("sortAsc"));
        model.put("agents", agentProvider.getAgentInfos(sortBy, sortAsc));
        model.put("sortBy", sortBy);
    }
}