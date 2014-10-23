package sns.teamcity;

import java.io.File;

public class DirectoryLocator {

    public File dataApps() {
        return new File("/data/apps");
    }

    public File logsApps() {
        return new File("/logs/apps");
    }

    public File mavenRepository() {
        String m2_home = System.getProperty("M2_HOME", System.getProperty("user.home") + "/.m2") + "/repository";
        return new File(m2_home);
    }
}
