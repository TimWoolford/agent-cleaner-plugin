package sns.teamcity.view;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {

    private static final String CONTENT_TYPE = "text/json";
    private final JsonWrapper json;
    private final Object myObject;

    public JsonView(JsonWrapper json, Object myObject) {
        this.myObject = myObject;
        this.json = json;
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public void render(Map<String, ?> stringMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String response = json.fromObject(myObject);

        httpServletResponse.setContentType(CONTENT_TYPE);
        httpServletResponse.setContentLength(response.length());
        httpServletResponse.getWriter().append(response);
    }
}
