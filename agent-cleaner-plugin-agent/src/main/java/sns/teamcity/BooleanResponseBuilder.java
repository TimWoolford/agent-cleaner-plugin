package sns.teamcity;

import java.util.Hashtable;

public class BooleanResponseBuilder {

    private BooleanResponseBuilder() {
    }

    public static Hashtable<String, String> responseFor(boolean result) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("success", result ? "true" : "false");
        return hashtable;
    }
}
