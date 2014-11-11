package sns.teamcity.agent;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Hashtable;

import static sns.teamcity.BooleanResponseBuilder.responseFor;
import static sns.teamcity.util.FileEncoder.decode;

public class AgentCleaner {
    private static final Logger LOG = Loggers.AGENT;

    public Hashtable<String, String> cleanDirectories(String csvDirs) {
        boolean success = true;
        for (String dir : decode(csvDirs)) {
            success = success && deleteDescendantsOf(new File(dir));
        }
        return responseFor(success);
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
                    if (!dir.delete() && dir.exists()) {
                        LOG.warn("Unable to delete file : " + dir.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                LOG.error("Unable to remove file/directory", e);
            }
        }
        return true;
    }

    private FileFilter thatAreDirectoriesOrFiles() {
        return FileFilterUtils.trueFileFilter();
    }
}