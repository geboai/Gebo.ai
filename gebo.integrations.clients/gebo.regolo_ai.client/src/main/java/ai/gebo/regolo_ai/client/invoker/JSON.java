package ai.gebo.regolo_ai.client.invoker;

import java.text.DateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import jakarta.ws.rs.ext.ContextResolver;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-09-02T09:08:15.751524600+02:00[Europe/Rome]")
public class JSON implements ContextResolver<ObjectMapper> {
    private ObjectMapper mapper;

    public JSON() {
        mapper = JsonMapper.builder()
            .changeDefaultPropertyInclusion(v -> JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            .defaultDateFormat(new RFC3339DateFormat())
            .build();
    }

    /**
     * Set the date format for JSON (de)serialization with Date properties.
     * @param dateFormat Date format
     */
    public void setDateFormat(DateFormat dateFormat) {
        // ObjectMapper is immutable in Jackson 3.x; date format is configured at construction time
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
