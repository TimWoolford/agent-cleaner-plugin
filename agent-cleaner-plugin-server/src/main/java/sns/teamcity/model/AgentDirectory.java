package sns.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("agentDirectory")
public class AgentDirectory {
    @XStreamAlias("agentNamePattern")
    private final String agentNamePattern;

    @XStreamImplicit(itemFieldName="directory")
    private final List<String> directories;

    public AgentDirectory(String agentNamePattern, List<String> directories) {
        this.agentNamePattern = agentNamePattern;
        this.directories = directories;
    }


    public String getAgentNamePattern() {
        return agentNamePattern;
    }

    public List<String> getDirectories() {
        return directories;
    }
}

