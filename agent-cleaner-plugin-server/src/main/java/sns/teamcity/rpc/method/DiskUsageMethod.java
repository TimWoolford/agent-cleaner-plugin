package sns.teamcity.rpc.method;

import sns.teamcity.model.DiskUsage;
import sns.teamcity.rpc.Handler;
import sns.teamcity.rpc.result.DiskUsageTransformer;
import sns.teamcity.rpc.result.ResultTransformer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static sns.teamcity.util.FileEncoder.encode;

public class DiskUsageMethod implements RpcMethod<DiskUsage> {
    private final List<String> directories;

    public DiskUsageMethod(String... directories) {
        this(newArrayList(directories));
    }

    public DiskUsageMethod(List<String> directories) {
        this.directories = directories;
    }

    @Override
    public String method() {
        return Handler.DiskUsage.method("usage");
    }

    @Override
    public int timeout() {
        return 2000;
    }

    @Override
    public Object[] params() {
        return new Object[]{encode(directories)};
    }

    @Override
    public ResultTransformer<DiskUsage> resultTransformer() {
        return new DiskUsageTransformer();
    }
}
