package ai.gebo.llms.chat.pipelines.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class RoutingDecision {
	private final List<String> futureRoute;
	private final IChatPipelineStepRuntimeData processedOutput;
	private final String pipelineRouterDecisionCode;
	private final Map<String, Object> pipelineParams;
}
