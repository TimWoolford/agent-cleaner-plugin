package sns.teamcity;

import jetbrains.buildServer.log.Loggers;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Hashtable;

import static sns.teamcity.BooleanResponseBuilder.responseFor;

public class AgentCleaner {
    private final DirectoryLocator directory;

    public AgentCleaner(DirectoryLocator directory) {
        this.directory = directory;
    }

    public Hashtable<String, String> cleanMavenRepository() {
        return responseFor(deleteDescendantsOf(directory.mavenRepository()));
    }

    public Hashtable<String, String> cleanSnsAppDirs() {
        return responseFor(
                deleteDescendantsOf(directory.dataApps()) && deleteDescendantsOf(directory.logsApps()));
    }

    private boolean deleteDescendantsOf(File rootFile) {
        try {
            Loggers.AGENT.info("Cleanup requested for : " + rootFile.getAbsolutePath());

            File[] directories = rootFile.listFiles(thatAreDirectories());
            if (directories == null ){
                return false;
            }

            for (File dir : directories) {
                Loggers.AGENT.info("Deleting directory : " + dir.getAbsolutePath());
                FileUtils.deleteDirectory(dir);
            }
            return true;
        } catch (IOException e) {
            Loggers.AGENT.error("Unable to clean directory", e);
            return false;
        }
    }

    private FileFilter thatAreDirectories() {
        return FileFilterUtils.directoryFileFilter();
    }
}
