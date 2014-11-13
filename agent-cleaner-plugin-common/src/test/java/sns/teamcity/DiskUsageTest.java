package sns.teamcity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import sns.teamcity.model.DiskUsage;
import sns.teamcity.model.FileSummary;

import java.util.Collection;
import java.util.Hashtable;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

public class DiskUsageTest {

    @Test
    public void foo() throws Exception {
        DiskUsage diskUsage = new DiskUsage(newArrayList(new FileSummary("path1", 22), new FileSummary("path2", 3333)));

        Hashtable<String, String> hashtable = diskUsage.toHashTable();

        assertThat(hashtable.size(), is(2));
        assertThat(hashtable.get("path1"), is("22"));
        assertThat(hashtable.get("path2"), is("3333"));
    }


    @Test
    public void bar() throws Exception {
        Hashtable<String, String> hashtable = new Hashtable<String, String>() {{
            put("path3", "999");
            put("path4", "8888");
        }};

        DiskUsage diskUsage = new DiskUsage(hashtable);

        Collection<FileSummary> fileSummaries = diskUsage.getFileSummaries();

        assertThat(fileSummaries.size(), is(2));
        assertThat(fileSummaries, hasItem(sameAs(new FileSummary("path3", 999))));
        assertThat(fileSummaries, hasItem(sameAs(new FileSummary("path4", 8888))));
    }

    private Matcher<FileSummary> sameAs(final FileSummary expected) {
        return new TypeSafeMatcher<FileSummary>() {
            @Override
            public boolean matchesSafely(FileSummary fileSummary) {
                return fileSummary.getPath().equals(expected.getPath())
                        && fileSummary.getSize() == expected.getSize();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("A file summary of " + expected.getPath() + " : " + expected.getSize());
            }

        };
    }
}