package sns.teamcity.util;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FileEncoderTest {

    @Test
    public void encodesAndDecodes() throws Exception {
        List<String> original = newArrayList("file1", "file2", "file3");

        String encoded = FileEncoder.encode(original);
        List<String> decoded = FileEncoder.decode(encoded);

        assertThat(decoded, is(original));
    }
}