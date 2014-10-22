package sns.teamcity.action;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.users.SUser;

public class AgentDisabler {
    private final String myComment;

    public AgentDisabler(String myComment) {
        this.myComment = myComment;
    }

    public void disable(SBuildAgent agent, SUser user) {
        if (agent.isEnabled()) {
            agent.setEnabled(false, user, myComment);
        }
    }

    public void enable(SBuildAgent agent, SUser user) {
        if (!agent.isEnabled() && wasDisabledByMe(agent)) {
            agent.setEnabled(true, user, "");
        }
    }

    private boolean wasDisabledByMe(SBuildAgent registeredAgent) {
        String comment = registeredAgent.getStatusComment().getComment();
        return comment != null && comment.equals(myComment);
    }
}
