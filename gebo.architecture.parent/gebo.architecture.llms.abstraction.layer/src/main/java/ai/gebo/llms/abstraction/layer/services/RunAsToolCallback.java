package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RunAsToolCallback implements ToolCallback {

	private final ToolCallback delegate;
	private final ReactiveIdentityUtil runAs;

	@Override
	public ToolDefinition getToolDefinition() {
		return delegate.getToolDefinition();
	}

	@Override
	public ToolMetadata getToolMetadata() {
		return delegate.getToolMetadata();
	}

	@Override
	public String call(String toolInput) {
		return runAs.doRunAsWithReturn(() -> delegate.call(toolInput));
	}
	
	@Override
	public String call(String toolInput, ToolContext toolContext) {
		return runAs.doRunAsWithReturn(() -> delegate.call(toolInput, toolContext));
	}
}