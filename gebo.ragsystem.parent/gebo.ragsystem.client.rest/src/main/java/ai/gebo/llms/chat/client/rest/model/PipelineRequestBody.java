package ai.gebo.llms.chat.client.rest.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class PipelineRequestBody {
	
	/*
	 * Described as a free-form object at the CLASS level, which is what the wire
	 * format actually is: @JsonAnyGetter/@JsonAnySetter flatten the entries onto
	 * this object, so a request carries {"someKey": ...}, never {"values": {...}}.
	 *
	 * The getter is therefore hidden from the schema. Exposing it used to publish a
	 * phantom 'values' property that the API never sends, and springdoc gave that
	 * property a bogus "default": "" - the only default in the entire spec. Because
	 * the property typed as a free-form object, swagger-codegen rendered the default
	 * into the generated Java as 'private Object values = ;', a syntax error that
	 * broke every regeneration of the brain and monolithic REST clients.
	 */
	@Schema(description = "Arbitrary environment entries",
			additionalProperties = Schema.AdditionalPropertiesValue.TRUE)
	public static class PipelineEnvironment {
		LinkedHashMap<String, Object> values = new LinkedHashMap<String, Object>();

		@JsonAnyGetter
		@Schema(hidden = true)
		public Map<String, Object> getValues() {
			return values;
		}
		@JsonAnySetter
		public void put(String key, Object value) {
			values.put(key, value);
		}
	}

	private @NotNull @Valid GeboChatRequest request = null;
	private PipelineEnvironment environment = new PipelineEnvironment();
}