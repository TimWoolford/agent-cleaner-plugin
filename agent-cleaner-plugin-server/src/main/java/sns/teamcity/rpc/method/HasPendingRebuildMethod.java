package sns.teamcity.rpc.method;

import sns.teamcity.rpc.Handler;
import sns.teamcity.rpc.result.ResultTransformer;
import sns.teamcity.rpc.result.SuccessTransformer;

public class HasPendingRebuildMethod implements RpcMethod<Boolean> {
    @Override
    public String method() {
        return Handler.RebuildAgent.method("hasPendingRebuild");
    }

    @Override
    public int timeout() {
        return 2000;
    }

    @Override
    public Object[] params() {
        return new Object[] {};
    }

    @Override
    public ResultTransformer<Boolean> resultTransformer() {
        return new SuccessTransformer();
    }
}
