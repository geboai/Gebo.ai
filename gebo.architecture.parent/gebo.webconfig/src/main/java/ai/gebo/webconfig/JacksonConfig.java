package ai.gebo.webconfig;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.util.StdDateFormat;

/**
 * The JSON mapper of the application, the one Spring MVC writes its responses with.
 *
 * <p>
 * Declaring it replaces the mapper Spring Boot would build, and with it the
 * registration of the Jackson modules of the context, which Spring Boot only does
 * on its own mapper: they are registered here instead. Among them is Spring Data's,
 * which writes a {@code Page} the way {@code @EnableSpringDataWebSupport} asks.
 * Without it every page went out as a bare {@code PageImpl}, its total at the root,
 * although the monolith asks for {@code VIA_DTO}, and its API description, the user
 * interface and the generated clients all read the total under {@code page}: no
 * paginator ever knew how many entries there were.
 * </p>
 */
@Configuration
public class JacksonConfig {

	@Bean
	public JsonMapper objectMapper(ObjectProvider<JacksonModule> modules) {
		SimpleModule dateModule = new SimpleModule();
		dateModule.addDeserializer(Date.class, new MultiFormatDateDeserializer(ZoneOffset.UTC));

		return JsonMapper.builder()
			.disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
			.defaultDateFormat(new StdDateFormat().withColonInTimeZone(true))
			.defaultTimeZone(TimeZone.getTimeZone("UTC"))
			.disable(DateTimeFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.addModules(modules.orderedStream().toList())
			.addModule(dateModule)
			.build();
	}
}
