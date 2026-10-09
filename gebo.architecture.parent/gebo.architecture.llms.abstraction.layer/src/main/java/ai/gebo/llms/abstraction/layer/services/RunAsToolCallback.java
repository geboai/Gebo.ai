package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.security.services.ReactiveIdentityUtil;

public class RunAsToolCallback implements ToolCallback {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunAsToolCallback.class);
	/** Between a tool's result and the text closing it. */
	static final String TOOLS_RESULTS_CLOSING_SEPARATOR = "\n\n---\n";

	private final ToolCallback delegate;
	private final ReactiveIdentityUtil runAs;
	private final ToolCallsListener toolCallListener;
	/**
	 * The text closing every result of this tool, rendered from the prompt of the model
	 * call (see {@code GPromptTemplateConfig#getToolsResultsPromptTemplate()}); null
	 * when the prompt has none.
	 */
	private final String toolsResultsClosing;

	public RunAsToolCallback(ToolCallback delegate, ReactiveIdentityUtil runAs, ToolCallsListener toolCallListener) {
		this(delegate, runAs, toolCallListener, null);
	}

	public RunAsToolCallback(ToolCallback delegate, ReactiveIdentityUtil runAs, ToolCallsListener toolCallListener,
			String toolsResultsClosing) {
		this.delegate = delegate;
		this.runAs = runAs;
		this.toolCallListener = toolCallListener;
		this.toolsResultsClosing = toolsResultsClosing != null && !toolsResultsClosing.isBlank()
				? toolsResultsClosing.strip()
				: null;
	}

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
		return runAs.doRunAsWithReturn(() -> {
			String result = delegate.call(toolInput);
			if (toolCallListener != null) {
				String name = delegate.getToolDefinition().name();
				String description = delegate.getToolDefinition().description();
				toolCallListener.addCall(name, description, toolInput, result);
			}
			return closed(result, null);
		});
	}

	@Override
	public String call(String toolInput, ToolContext toolContext) {
		return runAs.doRunAsWithReturn(() -> {
			String result = delegate.call(toolInput, toolContext);
			// the result piles up in the model call: it takes the room left to the tools,
			// cut to it when larger (see ToolsTokenBudget)
			final ToolsTokenBudget budget = ToolsTokenBudget.from(toolContext);
			if (budget != null) {
				result = budget.admit(delegate.getToolDefinition().name(), toolInput, result);
			}
			// the listener records the tool's own result, without the prompt's closing
			if (toolCallListener != null) {
				String name = delegate.getToolDefinition().name();
				String description = delegate.getToolDefinition().description();
				toolCallListener.addCall(name, description, toolInput, result);
			}
			return closed(result, budget);
		});
	}

	/**
	 * The result followed by the prompt's closing, when it has one: in the model call
	 * the tools' results come after the user message, the closing is the last text the
	 * model reads of them. It is never cut (it is short and it is what must hold) and
	 * it is taken out of the room left to the tools.
	 */
	private String closed(String result, ToolsTokenBudget budget) {
		if (toolsResultsClosing == null) {
			return result;
		}
		if (budget != null) {
			budget.consume(ITokensCountable.stringsTokensSize(toolsResultsClosing));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool:" + delegate.getToolDefinition().name() + " result closed with the prompt's tools results"
					+ " text (" + toolsResultsClosing.length() + " character(s))");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<TOOLS_RESULTS_CLOSING tool=" + delegate.getToolDefinition().name() + ">");
			LOGGER.trace(toolsResultsClosing);
			LOGGER.trace("</TOOLS_RESULTS_CLOSING>");
		}
		return (result != null ? result : "") + TOOLS_RESULTS_CLOSING_SEPARATOR + toolsResultsClosing;
	}
}
