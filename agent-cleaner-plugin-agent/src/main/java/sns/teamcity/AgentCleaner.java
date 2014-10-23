package sns.teamcity;

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
            File[] directories = rootFile.listFiles(thatAreDirectories());
            if (directories == null ){
                return false;
            }

            for (File dir : directories) {
                FileUtils.deleteDirectory(dir);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private FileFilter thatAreDirectories() {
        return FileFilterUtils.directoryFileFilter();
    }
}
