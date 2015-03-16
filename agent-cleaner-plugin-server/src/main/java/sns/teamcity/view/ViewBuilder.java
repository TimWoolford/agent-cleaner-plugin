package sns.teamcity.view;

import org.springframework.web.servlet.ModelAndView;

public class ViewBuilder {
    private final JsonWrapper json;

    public ViewBuilder(JsonWrapper json) {
        this.json = json;
    }

    public ModelAndView buildView(Object object) {
        return new ModelAndView(new JsonView(json, object));
    }
}