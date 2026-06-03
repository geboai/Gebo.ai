package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.DefaultToolCallingManager.Builder;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import lombok.AllArgsConstructor;

@AllArgsConstructor class AgentToolCallingManagerFactory implements ToolCallbackResolver {
	private final ToolCallsListener callBacksListener;
	private final List<String> allFunctions;
	private final List<ToolCallback> wrapped;
	private final Map<String, ToolCallback> callMap;

	ToolCallingManager create() {
		Builder builder = ToolCallingManager.builder();
		builder.toolCallbackResolver(this);
		return builder.build();
	}

	@Override
	public ToolCallback resolve(String toolName) {

		return callMap.get(toolName);
	}

}