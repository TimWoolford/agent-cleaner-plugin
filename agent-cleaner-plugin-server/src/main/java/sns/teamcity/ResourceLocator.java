package sns.teamcity;

import jetbrains.buildServer.web.openapi.PluginDescriptor;

public class ResourceLocator {
    private final PluginDescriptor pluginDescriptor;

    public ResourceLocator(PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
    }

    public String agentDiskSpaceTabJsp() {
        return pluginDescriptor.getPluginResourcesPath("agentDiskSpaceTab.jsp");
    }
}
