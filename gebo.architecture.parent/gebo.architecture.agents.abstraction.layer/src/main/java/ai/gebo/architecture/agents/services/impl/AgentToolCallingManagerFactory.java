package ai.gebo.architecture.agents.services.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.tool.DefaultToolCallingManager.Builder;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AgentToolCallingManagerFactory implements ToolCallbackResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentToolCallingManagerFactory.class);
	private final ToolCallsListener callBacksListener;
	private final List<String> allFunctions;
	private final List<ToolCallback> wrapped;
	private final Map<String, ToolCallback> callMap;

	public ToolCallingManager create() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin create() tool calling manager with " + (callMap != null ? callMap.size() : 0)
					+ " resolvable tool(s) out of " + (allFunctions != null ? allFunctions.size() : 0)
					+ " enabled function(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Enabled function names: " + allFunctions);
			LOGGER.trace("Resolvable tool names: " + (callMap != null ? callMap.keySet() : null));
		}
		Builder builder = ToolCallingManager.builder();
		builder.toolCallbackResolver(this);
		ToolCallingManager manager = builder.build();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End create() tool calling manager built");
		}
		return manager;
	}

	@Override
	public ToolCallback resolve(String toolName) {
		ToolCallback callback = callMap.get(toolName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("resolve(" + toolName + ") tool callback resolved:" + (callback != null));
		}
		return callback;
	}

}