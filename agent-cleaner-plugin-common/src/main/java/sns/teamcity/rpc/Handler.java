package sns.teamcity.rpc;

public enum Handler {
    AgentDiskSpace,
    RebuildAgent,
    CleanAgent,
    DiskUsage,
    FullDiskPanic;

    public String id() {
        return name();
    }

    public String method(String methodName) {
        return String.format("%s.%s", name(), methodName);
    }
}
