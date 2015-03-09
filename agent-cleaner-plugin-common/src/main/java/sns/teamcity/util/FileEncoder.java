package sns.teamcity.util;

import com.google.common.collect.Lists;
import com.intellij.openapi.util.text.StringUtil;

import java.util.Arrays;
import java.util.List;

import static sns.teamcity.util.FilenamePlaceholderTranslator.translatePlaceholders;

public class FileEncoder {
    private static final String SEP = "::";

    public static List<String> decode(String csv) {
        return Lists.transform(Arrays.asList(csv.split(SEP)), translatePlaceholders());
    }

    public static String encode(List<String> fileNames) {
        return StringUtil.join(fileNames, SEP);
    }

    public static String encode(String... fileNames) {
        return StringUtil.join(fileNames, SEP);
    }
}