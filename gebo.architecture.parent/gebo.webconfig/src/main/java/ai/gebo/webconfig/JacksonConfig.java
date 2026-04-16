package ai.gebo.webconfig;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
	protected ObjectMapper objectMapper = new ObjectMapper();

	public JacksonConfig() {
		// Supporto per java.time.*
		objectMapper.registerModule(new JavaTimeModule());
		// Date -> ISO-8601 (string), non timestamp numerico
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// Date parsing ISO-8601 robusto (accetta anche offset/Z)
		objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		// Timezone coerente (scegli UTC per evitare sorprese tra ambienti)
		objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
		// Opzionali ma spesso utili
		objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		SimpleModule dateModule = new SimpleModule();
	    dateModule.addDeserializer(Date.class, new MultiFormatDateDeserializer(ZoneOffset.UTC));
	    objectMapper.registerModule(dateModule);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return objectMapper;
	}
}
