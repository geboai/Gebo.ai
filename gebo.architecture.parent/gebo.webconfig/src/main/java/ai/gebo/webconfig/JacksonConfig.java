package ai.gebo.webconfig;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.util.StdDateFormat;

@Configuration
public class JacksonConfig {

	@Bean
	public JsonMapper objectMapper() {
		SimpleModule dateModule = new SimpleModule();
		dateModule.addDeserializer(Date.class, new MultiFormatDateDeserializer(ZoneOffset.UTC));

		return JsonMapper.builder()
			.disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
			.defaultDateFormat(new StdDateFormat().withColonInTimeZone(true))
			.defaultTimeZone(TimeZone.getTimeZone("UTC"))
			.disable(DateTimeFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.addModule(dateModule)
			.build();
	}
}
