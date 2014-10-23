package sns.teamcity.rpc;

public enum RpcMethod {
    rebuild(Handler.RebuildAgent),
    cancel(Handler.RebuildAgent),
    hasPendingRebuild(Handler.RebuildAgent),

    diskSpaceSummary(Handler.AgentDiskSpace),

    usage(Handler.DiskUsage),

    cleanSnsAppDirs(Handler.CleanAgent),
    cleanMavenRepository(Handler.CleanAgent),

    ;

    private final Handler handler;

    private RpcMethod(Handler rebuildAgent) {
        handler = rebuildAgent;
    }

    public String method() {
        return handler.method(name());
    }
}
