package ai.gebo.llms.chat.client.rest.model;

import java.util.LinkedHashMap;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class PipelineRequestBody {
	private @NotNull @Valid GeboChatRequest request = null;
	private LinkedHashMap<String, Object> environment = new LinkedHashMap<String, Object>();
}