package sns.teamcity.persistence;

import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.ServerPaths;
import org.jetbrains.annotations.NotNull;
import sns.teamcity.model.AgentConfig;
import sns.teamcity.model.AgentConfigurations;

import java.io.*;
import java.util.regex.Pattern;

public class ConfigPersistence {
    private final static String CONFIG_FILE_NAME = "agent-management-config.xml";

    private final XStream xStream;
    private final File configFile;

    private AgentConfigurations agentConfigurations;


    public ConfigPersistence(@NotNull ServerPaths serverPaths) {
        xStream = new XStream();
        xStream.setClassLoader(AgentConfigurations.class.getClassLoader());
        xStream.processAnnotations(new Class[]{AgentConfigurations.class, AgentConfig.class});

        configFile = new File(serverPaths.getConfigDir(), CONFIG_FILE_NAME);

        agentConfigurations = loadSettings();
    }

    private AgentConfigurations loadSettings() {
        if (!configFile.exists()) {
            return new AgentConfigurations(Lists.<AgentConfig>newArrayList());
        }

        try (InputStream inputStream = new FileInputStream(configFile)) {
            return (AgentConfigurations) xStream.fromXML(inputStream);
        } catch (IOException e) {
            Loggers.SERVER.error("Failed to load config file: " + configFile, e);
            throw Throwables.propagate(e);
        }
    }

    public AgentConfigurations updateData(AgentConfigurations agentConfigurations) {
        this.agentConfigurations = new AgentConfigurations(agentConfigurations.getAgentConfigurations());

        return persist();
    }

    public AgentConfigurations getAgentConfigurations() {
        return agentConfigurations;
    }

    public AgentConfigurations persist() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw Throwables.propagate(e);
            }
        }

        try (OutputStream outputStream = new FileOutputStream(configFile)) {
            xStream.toXML(agentConfigurations, outputStream);
            return agentConfigurations;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public AgentConfig agentConfigFor(final String agentName) {
        return Iterables.getFirst(Iterables.filter(agentConfigurations, new Predicate<AgentConfig>() {
            @Override
            public boolean apply(AgentConfig agentConfig) {
                return Pattern.compile(agentConfig.getAgentNamePattern()).matcher(agentName).find();
            }
        }), new AgentConfig(null, null, -1L, Lists.<String>newArrayList()));
    }
}
