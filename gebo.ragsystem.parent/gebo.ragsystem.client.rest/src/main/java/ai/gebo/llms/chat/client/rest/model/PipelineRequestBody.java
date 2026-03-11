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
	
	public static class PipelineEnvironment {
		LinkedHashMap<String, Object> values = new LinkedHashMap<String, Object>();

		@JsonAnyGetter
		@Schema(description = "Arbitrary environment entries", additionalProperties = Schema.AdditionalPropertiesValue.TRUE)
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