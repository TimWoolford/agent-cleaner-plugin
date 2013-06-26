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

    public String progressBarJs() {
        return javascript("jquery-ui-progressbar");
    }

    public String agentManagementJs() {
        return javascript("agentManagement");
    }

    private String jsp(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("jsp/%s.jsp", fileName));
    }

    private String javascript(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("js/%s.js", fileName));
    }
}