package sns.teamcity;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class AgentRebuilder {

    private static final String REBUILD_AGENT_FILENAME = "/tmp/REBUILD_AGENT";

    public Hashtable<String, String> rebuild() {
        try {
            new File(REBUILD_AGENT_FILENAME).createNewFile();
            return new Hashtable<String, String>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Hashtable<String, String> cancel() {
        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        hashTable.put("success", getStringStringHashtable() ? "true" : "false");
        return hashTable;
    }

    private boolean getStringStringHashtable() {
        File file = new File(REBUILD_AGENT_FILENAME);
        return file.exists() && file.delete();
    }
}
