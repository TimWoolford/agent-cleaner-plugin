package sns.teamcity;

import jetbrains.buildServer.web.openapi.PluginDescriptor;

public class ResourceLocator {
    private final PluginDescriptor pluginDescriptor;

    public ResourceLocator(PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
    }

    public String agentManagementTabJsp() {
        return jsp("agentManagementTab");
    }

    public String agentManagementAdminJsp() {
        return jsp("agentManagementAdminPage");
    }

    public String tableSorter() {
        return javascript("table-sorter");
    }

    public String agentManagementJs() {
        return javascript("agentManagement");
    }

    public String agentManagementConfigJs() {
        return javascript("agentManagementConfig");
    }

    public String agentManagementCss() {
        return css("agentManagement");
    }

    public String agentManagementConfigCss() {
        return css("agentManagementConfig");
    }

    private String jsp(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("jsp/%s.jsp", fileName));
    }

    private String css(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("css/%s.css", fileName));
    }

    private String javascript(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("js/%s.js", fileName));
    }
}