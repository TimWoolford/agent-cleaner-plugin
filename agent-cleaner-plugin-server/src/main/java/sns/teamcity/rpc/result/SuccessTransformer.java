package sns.teamcity.rpc.result;

import java.util.Hashtable;

public class SuccessTransformer implements ResultTransformer<Boolean> {
    @Override
    public Boolean transform(Hashtable<String, String> hashTable) {
        return Boolean.valueOf(hashTable.get("success"));
    }
}
