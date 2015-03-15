package sns.teamcity.view;

import org.springframework.web.servlet.ModelAndView;

public class ViewBuilder {
    private final Jsoniser jsoniser;

    public ViewBuilder(Jsoniser jsoniser) {
        this.jsoniser = jsoniser;
    }

    public ModelAndView buildView(Object object) {
        return new ModelAndView(new JsonView(jsoniser, object));
    }
}