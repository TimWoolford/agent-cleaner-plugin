package sns.teamcity.persistence;

import jetbrains.buildServer.serverSide.ServerPaths;
import org.apache.commons.io.FileUtils;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sns.teamcity.model.AgentDirectories;
import sns.teamcity.model.AgentDirectory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfigPersistenceTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ConfigPersistence configPersistence;

    @Before
    public void setUp() throws Exception {
        File configDir = temporaryFolder.newFolder();
        ServerPaths serverPaths = mock(ServerPaths.class);
        when(serverPaths.getConfigDir()).thenReturn(configDir.getAbsolutePath());

        File file = new File(configDir, "agent-management-config.xml");
        FileUtils.write(file, configXml());

        configPersistence = new ConfigPersistence(serverPaths);
    }

    @Test
    public void readsConfigurationFromFile() throws Exception {
        AgentDirectories value = configPersistence.getAgentDirectories();

        assertThat(value, IsIterableWithSize.<AgentDirectory>iterableWithSize(1));
    }

    private String configXml() {
        return "" +
                "<agentDirectories>" +
                "  <agentDirectory>" +
                "     <agentNamePattern>ba.*</agentNamePattern>" +
                "     <directory>dir1</directory>" +
                "     <directory>dir2</directory>" +
                "     <directory>dir3</directory>" +
                "  </agentDirectory>" +
                "</agentDirectories>" +
                "";
    }
}