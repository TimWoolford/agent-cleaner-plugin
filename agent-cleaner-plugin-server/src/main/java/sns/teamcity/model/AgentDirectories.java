package sns.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Iterator;
import java.util.List;

@XStreamAlias("agentDirectories")
public class AgentDirectories implements Iterable<AgentDirectory> {

    @XStreamImplicit
    private final List<AgentDirectory> agentDirectories;

    public AgentDirectories(List<AgentDirectory> agentDirectories) {
        this.agentDirectories = agentDirectories;
    }

    public List<AgentDirectory> getAgentDirectories() {
        return agentDirectories;
    }

    @Override
    public Iterator<AgentDirectory> iterator() {
        return agentDirectories.iterator();
    }
}
