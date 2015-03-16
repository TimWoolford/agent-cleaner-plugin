package sns.teamcity.persistence;

import jetbrains.buildServer.serverSide.ServerPaths;
import org.apache.commons.io.FileUtils;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sns.teamcity.model.AgentConfigurations;
import sns.teamcity.model.AgentConfig;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
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
        AgentConfigurations value = configPersistence.getAgentConfigurations();

        assertThat(value, IsIterableWithSize.<AgentConfig>iterableWithSize(1));
    }

    @Test
    public void retrievesMatchingConfig() throws Exception {
        AgentConfig agentConfig = configPersistence.agentConfigFor("ba333");

        assertThat(agentConfig.getFreeSpaceThreshold(), is(100L));
    }

    private String configXml() {
        return "" +
                "<agentConfigurations>" +
                "    <agentConfig>" +
                "        <id>foo</id>" +
                "        <agentNamePattern>ba.*</agentNamePattern>" +
                "        <freeSpaceThreshold>100</freeSpaceThreshold>" +
                "        <directory>dir1</directory>" +
                "        <directory>dir2</directory>" +
                "        <directory>dir3</directory>" +
                "    </agentConfig>" +
                "</agentConfigurations>" +
                "";
    }
}