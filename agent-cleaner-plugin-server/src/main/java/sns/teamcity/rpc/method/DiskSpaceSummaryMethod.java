package sns.teamcity.rpc.method;

import sns.teamcity.model.DiskSpaceSummary;
import sns.teamcity.rpc.Handler;
import sns.teamcity.rpc.result.DiskSpaceSummaryTransformer;
import sns.teamcity.rpc.result.ResultTransformer;

public class DiskSpaceSummaryMethod implements RpcMethod<DiskSpaceSummary> {
    @Override
    public String method() {
        return Handler.AgentDiskSpace.method("diskSpaceSummary");
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
    public ResultTransformer<DiskSpaceSummary> resultTransformer() {
        return new DiskSpaceSummaryTransformer();
    }
}
