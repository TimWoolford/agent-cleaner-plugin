package sns.teamcity.agent;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import sns.teamcity.DiskUsage;
import sns.teamcity.FileSummary;

import java.io.File;

import static org.apache.commons.io.FileUtils.sizeOfDirectory;
import static sns.teamcity.util.FileEncoder.decode;

public class DiskUsageProvider {

    @SuppressWarnings("UnusedDeclaration")
    public java.util.Hashtable<String, String> usage(String csvDirs) {
        return new DiskUsage(
                Lists.transform(decode(csvDirs), FileSummaryFunction.asFileSummary())
        ).toHashTable();
    }

    private static class FileSummaryFunction implements Function<String, FileSummary> {
        public static Function<String, FileSummary> asFileSummary() {
            return new FileSummaryFunction();
        }

        @Override
        public FileSummary apply(String path) {
            return new FileSummary(
                    path,
                    sizeOf(new File(path)));
        }

        private long sizeOf(File directory) {
            return (directory == null || !directory.exists()) ? 0 : sizeOfDirectory(directory);
        }
    }
}
