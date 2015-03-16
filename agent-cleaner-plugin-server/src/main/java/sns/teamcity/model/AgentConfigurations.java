package sns.teamcity.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Iterator;
import java.util.List;

@XStreamAlias("agentConfigurations")
public class AgentConfigurations implements Iterable<AgentConfig> {

    @XStreamImplicit
    private final List<AgentConfig> agentDirectories;

    @JsonCreator
    public AgentConfigurations(@JsonProperty("agentConfigurations") List<AgentConfig> agentDirectories) {
        this.agentDirectories = agentDirectories;
    }

    public List<AgentConfig> getAgentConfigurations() {
        return agentDirectories;
    }

    @Override
    public Iterator<AgentConfig> iterator() {
        return agentDirectories.iterator();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
