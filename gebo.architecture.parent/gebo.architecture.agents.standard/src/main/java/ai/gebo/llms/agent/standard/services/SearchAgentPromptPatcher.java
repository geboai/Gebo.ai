package ai.gebo.llms.agent.standard.services;

import ai.gebo.architecture.agents.services.AgentPromptTemplateParams;
import ai.gebo.architecture.agents.services.GAbstractGenericalAgentService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;

/**
 * Runtime patcher that adapts a standard (non-agentic) assisted-search prompt
 * template so it can be used as the {@code customLoopPrompt} of a network
 * search agent.
 * <p>
 * Search agents reuse the query-generation prompt of their underlying assisted
 * search method (the same prompt used by the standard chat pipelines). That
 * prompt knows nothing about the agent network, so it is cloned in memory and
 * the agent placeholder blocks ({@code {AGENT_IDENTITY}},
 * {@code {NETWORK_SCENARY}}, {@code {AGENT_COMUNICATION_CAPABILITY}},
 * {@code {PRIVATE_CONTEXT}}, {@code {INPUT}}) are appended to its user
 * template. Because {@link GPromptTemplateConfig#getPlaceholders()} is derived
 * from the template text, the appended tokens become first-class placeholders
 * that {@code createAgentTemplateParams} will populate at runtime.
 * <p>
 * The network-wide {@code {SHARED_CONTEXT}} is deliberately <b>not</b>
 * injected: search agents are command-driven executors, the coordinator already
 * distils the relevant state into the {@code {INPUT}} command, and rendering
 * the whole shared blackboard would be redundant and would compete with the
 * searcher's own retrieval token budget. The agent's own
 * {@code {PRIVATE_CONTEXT}} is kept so it can avoid repeating its previous
 * searches across loop iterations.
 * <p>
 * The original template content (including any {@code {format}} or
 * {@code {question}} placeholders) is preserved: the agent section is only
 * appended, never substituted. The {@code format} placeholder is intentionally
 * not injected, as the search prompts drive their own structured/field output.
 */
public final class SearchAgentPromptPatcher {

	private static final String NEWLINE = "\r\n";

	private SearchAgentPromptPatcher() {
	}

	/**
	 * Returns a copy of the given prompt with the agent network context section
	 * appended to its user template, or {@code null} if {@code original} is
	 * {@code null}.
	 */
	public static GPromptTemplateConfig withAgentPlaceholders(GPromptTemplateConfig original) {
		if (original == null) {
			return null;
		}
		GPromptTemplateConfig patched = original.copy();
		String userTemplate = patched.getUserPromptTemplate() != null ? patched.getUserPromptTemplate() : "";
		StringBuilder buffer = new StringBuilder(userTemplate);
		buffer.append(NEWLINE).append(NEWLINE);
		buffer.append("## AGENT NETWORK CONTEXT").append(NEWLINE).append(NEWLINE);
		buffer.append(
				"You are operating as a search agent inside a multi-agent network. Use the following network context to focus your search. The current search command/input is the highest-priority instruction and overrides the generic question above.")
				.append(NEWLINE).append(NEWLINE);
		appendBlock(buffer, "Current agent identity", AgentPromptTemplateParams.AGENT_IDENTITY_TEMPLATE_PARAM);
		appendBlock(buffer, "Network scenario", AgentPromptTemplateParams.NETWORK_SCENARY_TEMPLATE_PARAM);
		appendBlock(buffer, "Communication capabilities",
				AgentPromptTemplateParams.AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM);
		appendBlock(buffer, "Current agent private memory", AgentPromptTemplateParams.PRIVATE_CONTEXT_TEMPLATE_PARAM);
		appendBlock(buffer, "Current search command/input (highest priority)",
				AgentPromptTemplateParams.INPUT_TEMPLATE_PARAM);
		patched.setUserPromptTemplate(buffer.toString());
		return patched;
	}

	private static void appendBlock(StringBuilder buffer, String heading, String placeholder) {
		buffer.append("### ").append(heading).append(NEWLINE);
		buffer.append("{").append(placeholder).append("}").append(NEWLINE).append(NEWLINE);
	}
}
