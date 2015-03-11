package sns.teamcity.view;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.web.servlet.ModelAndView;

public class ViewBuilder {
    private final ObjectMapper objectMapper;

    public ViewBuilder() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule("AwesomeSoniqueModule", Version.unknownVersion()));
    }

    public ModelAndView buildView(Object object) {
        return new ModelAndView(new JsonView(objectMapper, object));
    }
}
