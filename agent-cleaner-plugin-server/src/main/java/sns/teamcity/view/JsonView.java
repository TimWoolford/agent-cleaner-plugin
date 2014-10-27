package sns.teamcity.view;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {

    private static final String CONTENT_TYPE = "text/json";
    private final ObjectMapper objectMapper;
    private final Object myObject;

    public JsonView(Object myObject) {
        this.myObject = myObject;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule("AwesomeSoniqueModule", Version.unknownVersion()));
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public void render(Map<String, ?> stringMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String response = objectMapper.writeValueAsString(myObject);

        httpServletResponse.setContentType(CONTENT_TYPE);
        httpServletResponse.setContentLength(response.length());
        httpServletResponse.getWriter().append(response);
    }
}
