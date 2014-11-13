package sns.teamcity.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import static com.google.common.collect.Collections2.transform;

public class DiskUsage {
    private final Collection<FileSummary> fileSummaries;

    public DiskUsage(Collection<FileSummary> fileSummaries) {
        this.fileSummaries = fileSummaries;
    }

    public DiskUsage(Hashtable<String, String> hashtable) {
        this(transform(hashtable.entrySet(), new Function<Map.Entry<String, String>, FileSummary>() {
            @Override
            public FileSummary apply(Map.Entry<String, String> entry) {
                return new FileSummary(entry.getKey(), Long.valueOf(entry.getValue()));
            }
        }));
    }

    public Hashtable<String, String> toHashTable() {
        ImmutableMap<String, FileSummary> rawMap = Maps.uniqueIndex(fileSummaries, new Function<FileSummary, String>() {
            @Override
            public String apply(FileSummary fileSummary) {
                return fileSummary.getPath();
            }
        });

        Map<String, String> map = Maps.transformEntries(rawMap, new StringFileSummaryStringEntryTransformer());

        return new Hashtable<String, String>(map);
    }

    public Collection<FileSummary> getFileSummaries() {
        return fileSummaries;
    }

    private static class StringFileSummaryStringEntryTransformer implements Maps.EntryTransformer<String, FileSummary, String> {
        @Override
        public String transformEntry(String key, FileSummary fileSummary) {
            return String.valueOf(fileSummary.getSize());
        }
    }
}
