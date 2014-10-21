package sns.teamcity;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import sns.teamcity.model.AgentInfo;

import java.util.Comparator;

import static com.google.common.base.Functions.forMap;

public class AgentComparators {
    private final Function<String, Comparator<AgentInfo>> comparatorFunction;

    public AgentComparators() {
        comparatorFunction = forMap(
                ImmutableMap.of(
                        "SORT_BY_PERCENTAGE", new SortByPercentage(),
                        "SORT_BY_FREE_SPACE", new SortByFreeSpace(),
                        "SORT_BY_UPTIME", new SortByUptime()
                ),
                new SortByName()
        );
    }

    public Comparator<AgentInfo> forId(String sortId, Boolean sortAsc) {
        return new Inverserator(comparatorFunction.apply(sortId), sortAsc);
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
            return Double.compare(agentInfo.getDiskSpaceSummary().getPercentageUsed(), agentInfo2.getDiskSpaceSummary().getPercentageUsed());
        }
    }

    private static class SortByUptime implements Comparator<AgentInfo> {
        @Override
        public int compare(AgentInfo agentInfo, AgentInfo agentInfo2) {
            return Double.compare(agentInfo.getUptime(), agentInfo2.getUptime());
        }
    }

    private static class SortByFreeSpace implements Comparator<AgentInfo> {
        @Override
        public int compare(AgentInfo agentInfo, AgentInfo agentInfo2) {
            return Long.valueOf(agentInfo.getDiskSpaceSummary().getFreeSpace()).compareTo(agentInfo2.getDiskSpaceSummary().getFreeSpace());
        }
    }

    private static class Inverserator implements Comparator<AgentInfo> {
        private final Comparator<AgentInfo> delegate;
        private final int inverser;

        private Inverserator(Comparator<AgentInfo> delegate, Boolean inverse) {
            this.delegate = delegate;
            this.inverser = inverse ? 1 : -1;
        }

        @Override
        public int compare(AgentInfo agentInfo, AgentInfo agentInfo2) {
            return inverser * delegate.compare(agentInfo, agentInfo2);
        }
    }
}