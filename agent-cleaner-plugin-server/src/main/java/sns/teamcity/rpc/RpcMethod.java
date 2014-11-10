package sns.teamcity.rpc;

public enum RpcMethod {
    rebuild(Handler.RebuildAgent, "rebuild"),
    cancel(Handler.RebuildAgent, "cancel"),
    hasPendingRebuild(Handler.RebuildAgent, "hasPendingRebuild"),

    diskSpaceSummary(Handler.AgentDiskSpace, "diskSpaceSummary"),

    usage(Handler.DiskUsage, "usage"),

    cleanSnsAppDirs(Handler.CleanAgent, "cleanDirectories", 30000, new Object[]{"/data/apps", "/logs/apps"}),
    cleanMavenRepository(Handler.CleanAgent, "cleanMavenRepository", 30000),;

    private final Handler handler;
    private final String methodName;
    private final int timeout;
    private final Object[] params;

    private RpcMethod(Handler handler, String methodName) {
        this(handler, methodName, 2000);
    }

    private RpcMethod(Handler rebuildAgent, String methodName, int timeout) {
        this(rebuildAgent, methodName, timeout, new Object[]{});
    }

    private RpcMethod(Handler rebuildAgent, String methodName, int timeout, Object[] params) {
        this.handler = rebuildAgent;
        this.methodName = methodName;
        this.timeout = timeout;
        this.params = params;
    }

    public String method() {
        return handler.method(methodName);
    }

    public int timeout() {
        return timeout;
    }

    public Object[] params() {
        return params;
    }
}
