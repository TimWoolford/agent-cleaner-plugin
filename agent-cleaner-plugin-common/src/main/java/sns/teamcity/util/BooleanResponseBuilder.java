package sns.teamcity.util;

import com.google.common.collect.ImmutableMap;

import java.util.Hashtable;

public class BooleanResponseBuilder {

    private BooleanResponseBuilder() {
    }

    public static Hashtable<String, String> responseFor(boolean result) {
        return new Hashtable<String, String>(ImmutableMap.of("success", result ? "true" : "false"));
    }
}