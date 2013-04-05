package sns.teamcity;

import jetbrains.buildServer.web.openapi.PluginDescriptor;

public class ResourceLocator {
    private final PluginDescriptor pluginDescriptor;

    public ResourceLocator(PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
    }

    public String agentDiskSpaceTabJsp() {
        return jsp("agentDiskSpaceTab");
    }

    public String progressBarJs() {
        return javascript("jquery-ui-progressbar");
    }

    private String jsp(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("jsp/%s.jsp", fileName));
    }

    private String javascript(String fileName) {
        return pluginDescriptor.getPluginResourcesPath(String.format("js/%s.js", fileName));
    }
}