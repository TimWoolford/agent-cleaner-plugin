package sns.teamcity.persistence;

import com.google.common.base.Throwables;
import com.thoughtworks.xstream.XStream;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.ServerPaths;
import org.jetbrains.annotations.NotNull;
import sns.teamcity.model.AgentDirectories;
import sns.teamcity.model.AgentDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigPersistence {
    private final static String CONFIG_FILE_NAME = "agent-management-config.xml";

    private final XStream xStream;
    private final File configFile;
    private final AgentDirectories agentDirectories;


    public ConfigPersistence(@NotNull ServerPaths serverPaths) {
        xStream = new XStream();
        xStream.setClassLoader(AgentDirectories.class.getClassLoader());
        xStream.processAnnotations(new Class[]{AgentDirectories.class, AgentDirectory.class});

        configFile = new File(serverPaths.getConfigDir(), CONFIG_FILE_NAME);

        agentDirectories = loadSettings();
    }

    private AgentDirectories loadSettings() {
        try (InputStream inputStream = new FileInputStream(configFile)) {

            return (AgentDirectories) xStream.fromXML(inputStream);

        } catch (IOException e) {
            Loggers.SERVER.error("Failed to load config file: " + configFile, e);
            throw Throwables.propagate(e);
        }
    }

    public AgentDirectories value() {
        return agentDirectories;
    }

}
