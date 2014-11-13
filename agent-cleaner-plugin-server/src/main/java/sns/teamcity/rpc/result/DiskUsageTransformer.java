package sns.teamcity.rpc.result;

import sns.teamcity.model.DiskUsage;

import java.util.Hashtable;

public class DiskUsageTransformer implements ResultTransformer<DiskUsage> {
    @Override
    public DiskUsage transform(Hashtable<String, String> hashTable) {
        return new DiskUsage(hashTable);
    }
}
