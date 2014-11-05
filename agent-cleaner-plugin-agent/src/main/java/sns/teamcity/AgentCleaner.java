package sns.teamcity;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Hashtable;

import static sns.teamcity.BooleanResponseBuilder.responseFor;

public class AgentCleaner {
    private static final Logger LOG = Loggers.AGENT;
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
        LOG.info("Cleanup requested for : " + rootFile.getAbsolutePath());

        File[] directories = rootFile.listFiles(thatAreDirectoriesOrFiles());
        if (directories == null) {
            return false;
        }

        for (File dir : directories) {
            try {
                if (dir.isDirectory()) {
                    LOG.info("Deleting directory : " + dir.getAbsolutePath());
                    FileUtils.deleteDirectory(dir);
                } else {
                    LOG.info("Deleting file : " + dir.getAbsolutePath());
                    if (!dir.delete()) {
                        if (dir.exists()) {
                            LOG.warn("Unable to delete file : " + dir.getAbsolutePath());
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("Unable to remove file : ", e);
            }
        }
        return true;
    }

    private FileFilter thatAreDirectoriesOrFiles() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
    }
}
