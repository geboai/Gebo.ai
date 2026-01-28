package ai.gebo.llms.chat.pipelines.model;

import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class PipelineRoutingInfosMessageEnvelope extends GeboChatMessageEnvelope<PipelineRoutingInfos> {
	
};
