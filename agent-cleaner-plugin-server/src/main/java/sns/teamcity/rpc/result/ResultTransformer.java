package sns.teamcity.rpc.result;

import java.util.Hashtable;

public interface ResultTransformer<T> {
    T transform(Hashtable<String, String> hashTable);
}
