package sns.teamcity.rpc.method;

import sns.teamcity.rpc.Handler;
import sns.teamcity.rpc.result.ResultTransformer;
import sns.teamcity.rpc.result.SuccessTransformer;

public class CancelRebuildMethod implements RpcMethod<Boolean> {

    private final ResultTransformer<Boolean> tranformer;

    public CancelRebuildMethod() {
        this.tranformer = new SuccessTransformer();
    }

    @Override
    public String method() {
        return Handler.RebuildAgent.method("cancel");
    }

    @Override
    public int timeout() {
        return 2000;
    }

    @Override
    public Object[] params() {
        return new Object[]{};
    }

    @Override
    public ResultTransformer<Boolean> resultTransformer() {
        return tranformer;
    }
}
