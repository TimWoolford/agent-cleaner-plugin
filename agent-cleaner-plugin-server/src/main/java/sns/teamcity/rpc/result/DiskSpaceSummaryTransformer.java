package sns.teamcity.rpc.result;

import sns.teamcity.DiskSpaceSummary;

import java.util.Hashtable;

public class DiskSpaceSummaryTransformer implements ResultTransformer<DiskSpaceSummary> {
    @Override
    public DiskSpaceSummary transform(Hashtable<String, String> hashTable) {
        return new DiskSpaceSummary(hashTable);
    }
}
