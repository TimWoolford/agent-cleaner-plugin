package sns.teamcity.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import sns.teamcity.AgentProvider;
import sns.teamcity.model.AgentInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class AgentController extends BaseController {
    private final AgentProvider agentProvider;
    private ObjectMapper objectMapper;

    public AgentController(AgentProvider agentProvider, WebControllerManager webControllerManager) {
        this.agentProvider = agentProvider;
        webControllerManager.registerController("/agentManagement/agents/", this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule("AwesomeSoniqueModule", Version.unknownVersion()));
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(new View() {
            private static final String CONTENT_TYPE = "text/json";

            @Override
            public String getContentType() {
                return CONTENT_TYPE;
            }

            @Override
            public void render(Map<String, ?> stringMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
                String sortBy = httpServletRequest.getParameter("sortBy");
                Boolean sortAsc = Boolean.valueOf(httpServletRequest.getParameter("sortAsc"));

                List<AgentInfo> agentInfos = agentProvider.getAgentInfos(sortBy, sortAsc);

                String response = objectMapper.writeValueAsString(agentInfos);

                httpServletResponse.setContentType(CONTENT_TYPE);
                httpServletResponse.setContentLength(response.length());
                httpServletResponse.getWriter().append(response);
            }
        });
    }
}