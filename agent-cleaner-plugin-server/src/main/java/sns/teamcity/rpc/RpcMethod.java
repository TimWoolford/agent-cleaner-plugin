package sns.teamcity.rpc;

public enum RpcMethod {
    rebuild(Handler.RebuildAgent),
    cancel(Handler.RebuildAgent),
    hasPendingRebuild(Handler.RebuildAgent),

    diskSpaceSummary(Handler.AgentDiskSpace),

    usage(Handler.DiskUsage),

    cleanSnsAppDirs(Handler.CleanAgent, 30000),
    cleanMavenRepository(Handler.CleanAgent, 30000),

    ;

    private final Handler handler;
    private final int timeout;

    private RpcMethod(Handler handler) {
        this(handler , 2000);
    }

    private RpcMethod(Handler rebuildAgent, int timeout) {
        this.handler = rebuildAgent;
        this.timeout = timeout;
    }

    public String method() {
        return handler.method(name());
    }

    public int timeout() {
        return timeout;
    }
}
