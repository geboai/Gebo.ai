package ai.gebo.llms.chat.client.rest.model;

import java.util.LinkedHashMap;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class PipelineRequestBody {
	private @NotNull @Valid GeboChatRequest request = null;
	@Schema(description = "Free-form environment map containint environment entries for output processor", type = "object", additionalProperties = Schema.AdditionalPropertiesValue.TRUE)
	private LinkedHashMap<String, Object> environment = new LinkedHashMap<String, Object>();
}