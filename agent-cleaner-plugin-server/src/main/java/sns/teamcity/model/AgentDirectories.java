package sns.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias("agentDirectories")
public class AgentDirectories {

    private final List<AgentDirectory> agentDirectories;

    public AgentDirectories(List<AgentDirectory> agentDirectories) {
        this.agentDirectories = agentDirectories;
    }

    public List<AgentDirectory> getAgentDirectories() {
        return agentDirectories;
    }
}
