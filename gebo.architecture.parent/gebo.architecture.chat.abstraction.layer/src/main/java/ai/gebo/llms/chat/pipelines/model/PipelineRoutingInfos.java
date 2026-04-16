package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PipelineRoutingInfos {
	private List<String> stepIds = new ArrayList<String>();
	@NotNull
	private String pipelineRouterDecisionCode = null;
	@NotNull
	private String chatModel = null;
	@NotNull
	private String serviceModel = null;
	private LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
}