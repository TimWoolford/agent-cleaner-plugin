package sns.teamcity.rpc;

public enum Handlers {
    AgentDiskSpace,
    RebuildAgent,
    FullDiskPanic;

    public String id() {
        return name();
    }

    public String method(String methodName) {
        return String.format("%s.%s", name(), methodName);
    }
}
