package sns.teamcity;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class AgentRebuilder {

    public Hashtable<String, String> rebuild() {
        try {
            new File("/tmp/REBUILD_AGENT").createNewFile();
            return new Hashtable<String, String>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
