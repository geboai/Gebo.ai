package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PipelineRoutingInfos {
	List<String> stepIds = new ArrayList<String>();
	@NotNull
	String pipelineRouterDecisionCode = null;
	@NotNull
	String chatModel = null;
	@NotNull
	String serviceModel = null;
}