package ai.gebo.llms.chat.pipelines.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class RoutingDecision {
	private final List<String> futureRoute;
	private final IChatPipelineStepRuntimeData processedOutput;
	private final String pipelineRouterDecisionCode;
}
