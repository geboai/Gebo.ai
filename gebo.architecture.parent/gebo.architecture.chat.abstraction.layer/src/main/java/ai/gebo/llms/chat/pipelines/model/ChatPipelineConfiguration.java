package ai.gebo.llms.chat.pipelines.model;

import com.drew.lang.annotations.NotNull;

import lombok.Data;

@Data
public class ChatPipelineConfiguration {
	@NotNull
	private String code = null;
	@NotNull
	private String description = null;
	@NotNull
	private String stepInputId = null;
	@NotNull
	private String stepRouterId = null;
	private boolean defaultPipeline = false;
}
