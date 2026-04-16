package ai.gebo.webconfig;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

final class MultiFormatDateDeserializer extends StdDeserializer<Date> {
	private static final DateTimeFormatter LEGACY = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final ZoneId defaultZone;

	MultiFormatDateDeserializer(ZoneId defaultZone) {
		super(Date.class);
		this.defaultZone = defaultZone;
	}

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonToken t = p.currentToken();

		// opzionale: se arriva un numero, trattalo come epoch millis
		if (t == JsonToken.VALUE_NUMBER_INT) {
			return new Date(p.getLongValue());
		}

		if (t != JsonToken.VALUE_STRING) {
			return (Date) ctxt.handleUnexpectedToken(Date.class, p);
		}

		String s = p.getText().trim();
		if (s.isEmpty())
			return null;

		// 1) ISO con offset/Z: 2026-01-09T07:37:23Z / 2026-01-09T07:37:23+01:00
		try {
			return Date.from(OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant());
		} catch (DateTimeParseException ignored) {
		}

		// 2) ISO Instant (Z): 2026-01-09T07:37:23Z
		try {
			return Date.from(Instant.parse(s));
		} catch (DateTimeParseException ignored) {
		}

		// 3) ISO LocalDateTime: 2026-01-09T07:37:23 (assumo defaultZone)
		try {
			LocalDateTime ldt = LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			return Date.from(ldt.atZone(defaultZone).toInstant());
		} catch (DateTimeParseException ignored) {
		}

		// 4) Legacy: "2026-01-09 07:37:23" (assumo defaultZone)
		try {
			LocalDateTime ldt = LocalDateTime.parse(s, LEGACY);
			return Date.from(ldt.atZone(defaultZone).toInstant());
		} catch (DateTimeParseException ignored) {
		}

		throw InvalidFormatException.from(p, "Unsupported date format: " + s, s, Date.class);
	}
}