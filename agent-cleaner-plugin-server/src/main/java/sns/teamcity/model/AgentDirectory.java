package sns.teamcity.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@XStreamAlias("agentDirectory")
public class AgentDirectory {
    @XStreamAlias("agentNamePattern")
    private final String agentNamePattern;

    @XStreamImplicit(itemFieldName = "directory")
    private final List<String> directories;

    @JsonCreator
    public AgentDirectory(@JsonProperty("agentNamePattern") String agentNamePattern, @JsonProperty("directories") List<String> directories) {
        this.agentNamePattern = agentNamePattern;
        this.directories = directories;
    }

    public String getAgentNamePattern() {
        return agentNamePattern;
    }

    public List<String> getDirectories() {
        return directories;
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

