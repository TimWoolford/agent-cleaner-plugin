package sns.teamcity.rpc;

public enum Handlers {
    AgentDiskSpace
    ;

    public String id() {
        return name();
    }

    public String method(String methodName) {
        return String.format("%s.%s", name(), methodName);
    }
}
