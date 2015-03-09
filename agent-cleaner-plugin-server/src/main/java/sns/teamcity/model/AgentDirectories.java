package sns.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("agentDirectories")
public class AgentDirectories {

    @XStreamImplicit
    private final List<AgentDirectory> agentDirectories;

    public AgentDirectories(List<AgentDirectory> agentDirectories) {
        this.agentDirectories = agentDirectories;
    }

    public List<AgentDirectory> getAgentDirectories() {
        return agentDirectories;
    }
}
