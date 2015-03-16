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

@XStreamAlias("agentConfig")
public class AgentConfig {
    @XStreamAlias("id")
    private final String id;

    @XStreamAlias("agentNamePattern")
    private final String agentNamePattern;

    @XStreamAlias("freeSpaceThreshold")
    private final Long freeSpaceThreshold;

    @XStreamImplicit(itemFieldName = "directory")
    private final List<String> directories;

    @JsonCreator
    public AgentConfig(@JsonProperty("id") String id,
                       @JsonProperty("agentNamePattern") String agentNamePattern,
                       @JsonProperty("freeSpaceThreshold") Long freeSpaceThreshold,
                       @JsonProperty("directories") List<String> directories) {
        this.id = id;
        this.agentNamePattern = agentNamePattern;
        this.freeSpaceThreshold = freeSpaceThreshold;
        this.directories = directories;
    }

    public String getId() {
        return id;
    }

    public String getAgentNamePattern() {
        return agentNamePattern;
    }

    public List<String> getDirectories() {
        return directories;
    }

    public Long getFreeSpaceThreshold() {
        return freeSpaceThreshold;
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

