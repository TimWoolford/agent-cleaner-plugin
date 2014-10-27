package sns.teamcity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Hashtable;

import static sns.teamcity.BooleanResponseBuilder.responseFor;

public class AgentCleaner {
    private static final Logger LOG = Logger.getLogger("agent-cleaner-plugin");
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
            LOG.info("Cleanup requested for : " + rootFile.getAbsolutePath());

            File[] directories = rootFile.listFiles(thatAreDirectories());
            if (directories == null ){
                return false;
            }

            for (File dir : directories) {
                LOG.info("Deleting directory : " + dir.getAbsolutePath());
                FileUtils.deleteDirectory(dir);
            }
            return true;
        } catch (IOException e) {
            LOG.error("Unable to clean directory", e);
            return false;
        }
    }

    private FileFilter thatAreDirectories() {
        return FileFilterUtils.directoryFileFilter();
    }
}
