package sns.teamcity.rpc.method;

import sns.teamcity.rpc.result.ResultTransformer;

public interface RpcMethod<T> {
    String method();
    int timeout();
    Object[] params();
    ResultTransformer<T> resultTransformer();
}
