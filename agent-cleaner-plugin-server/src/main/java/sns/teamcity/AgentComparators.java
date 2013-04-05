package sns.teamcity;

import sns.teamcity.model.AgentInfo;

import java.util.Comparator;
import java.util.Map;

import static com.google.common.base.Functions.forMap;
import static com.google.common.collect.Maps.newHashMap;

public class AgentComparators {
    private final Map<String, Comparator<AgentInfo>> map = newHashMap();
    private final SortByName sortByName = new SortByName();

    public AgentComparators() {
        map.put("SORT_BY_NAME", sortByName);
        map.put("SORT_BY_PERCENTAGE", new SortByPercentage());
    }

    public Comparator<AgentInfo> forId(String sortId, Boolean sortAsc) {
        return forMap(map, sortByName).apply(sortId);
    }

    private static class SortByName implements Comparator<AgentInfo> {
        @Override
        public int compare(AgentInfo agentInfo, AgentInfo agentInfo2) {
            return agentInfo.getName().compareTo(agentInfo2.getName());
        }
    }

    private static class SortByPercentage implements Comparator<AgentInfo> {
        @Override
        public int compare(AgentInfo agentInfo, AgentInfo agentInfo2) {
            return Long.valueOf(agentInfo.getDiskSpaceSummary().getPercentageUsed()).compareTo(agentInfo2.getDiskSpaceSummary().getPercentageUsed());
        }
    }
}
