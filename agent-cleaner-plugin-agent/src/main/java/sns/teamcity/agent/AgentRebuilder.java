package sns.teamcity.agent;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import static sns.teamcity.util.BooleanResponseBuilder.responseFor;

public class AgentRebuilder {

    private static final String REBUILD_AGENT_FILENAME = "/tmp/REBUILD_AGENT";

    @SuppressWarnings("UnusedDeclaration")
    public Hashtable<String, String> rebuild() {
        try {
            return responseFor(new File(REBUILD_AGENT_FILENAME).createNewFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public Hashtable<String, String> cancel() {
        return responseFor(removeRebuildFile());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Hashtable<String, String> hasPendingRebuild() {
        return responseFor(new File(REBUILD_AGENT_FILENAME).exists());
    }

    private boolean removeRebuildFile() {
        File file = new File(REBUILD_AGENT_FILENAME);
        return file.exists() && file.delete();
    }
}
