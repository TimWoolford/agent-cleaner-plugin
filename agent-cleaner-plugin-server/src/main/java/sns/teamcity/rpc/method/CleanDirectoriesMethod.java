package sns.teamcity.rpc.method;

import sns.teamcity.rpc.Handler;
import sns.teamcity.rpc.result.ResultTransformer;
import sns.teamcity.rpc.result.SuccessTransformer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static sns.teamcity.util.FileEncoder.encode;

public class CleanDirectoriesMethod implements RpcMethod<Boolean> {
    private final List<String> directories;

    public CleanDirectoriesMethod(String... directories) {
        this(newArrayList(directories));
    }

    public CleanDirectoriesMethod(List<String> directories) {
        this.directories = directories;
    }

    @Override
    public String method() {
        return Handler.CleanAgent.method("cleanDirectories");
    }

    @Override
    public int timeout() {
        return 30000;
    }

    @Override
    public Object[] params() {
        return new Object[]{encode(directories)};
    }

    @Override
    public ResultTransformer<Boolean> resultTransformer() {
        return new SuccessTransformer();
    }
}
