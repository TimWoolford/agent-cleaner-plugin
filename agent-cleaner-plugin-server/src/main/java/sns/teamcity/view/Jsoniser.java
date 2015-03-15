package sns.teamcity.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import java.io.IOException;

public class Jsoniser {
    private final ObjectMapper objectMapper;

    public Jsoniser() {
        objectMapper = new ObjectMapper();
        ;
    }

    public String fromObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public <T> T fromString(String input, Class<T> clazz) {
        try {
            return objectMapper.readValue(input, clazz);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
