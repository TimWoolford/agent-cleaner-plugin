package sns.teamcity.util;

import com.google.common.base.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenamePlaceholderTranslator implements Function<String, String> {
    private static final Pattern PATTERN = Pattern.compile("(\\$\\{(.*?)\\})");

    public static Function<String, String> translatePlaceholders() {
        return new FilenamePlaceholderTranslator();
    }

    @Override
    public String apply(String rawFilename) {
        Matcher matcher = PATTERN.matcher(rawFilename);

        while (matcher.find()) {
            rawFilename = rawFilename.replace(matcher.group(1), System.getProperty(matcher.group(2)));
        }

        return rawFilename;
    }
}
