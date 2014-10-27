package sns.teamcity.model;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ViewBuilder {
    private final ObjectMapper objectMapper;

    public ViewBuilder() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule("AwesomeSoniqueModule", Version.unknownVersion()));
    }

    public ModelAndView buildView(final Object object) {
        return new ModelAndView(new View() {
            private static final String CONTENT_TYPE = "text/json";

            @Override
            public String getContentType() {
                return CONTENT_TYPE;
            }

            @Override
            public void render(Map<String, ?> stringMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
                String response = objectMapper.writeValueAsString(object);

                httpServletResponse.setContentType(CONTENT_TYPE);
                httpServletResponse.setContentLength(response.length());
                httpServletResponse.getWriter().append(response);
            }
        });
    }
}
