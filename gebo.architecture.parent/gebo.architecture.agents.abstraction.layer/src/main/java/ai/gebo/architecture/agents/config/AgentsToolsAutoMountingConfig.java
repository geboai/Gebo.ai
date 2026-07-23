package ai.gebo.architecture.agents.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Configuration of the tools that must be kept out of an agent's automatic tool
 * mounting.
 *
 * <p>An agent enables "tools auto mounting" by setting
 * {@code GAgentConfig.subscribeAllTools == true}: at runtime such an agent
 * expands its tool set to every tool registered in the system (this is how, for
 * instance, the tool-calling agent is wired while the default agents network is
 * assembled). The tool (function) names listed in {@link #excludedTools} are
 * filtered out of that automatic expansion, so they are never auto-associated to
 * an auto-mounting agent - regardless of which agent enables auto mounting.
 *
 * <p>The exclusion only affects <em>automatic</em> mounting: an agent can still
 * opt into an excluded tool explicitly through its {@code enabledFunctions}.
 *
 * <p>Configure via
 * {@code ai.gebo.agents.tools.auto-mounting.excluded-tools} in application.yml,
 * e.g.:
 *
 * <pre>
 * ai:
 *   gebo:
 *     agents:
 *       tools:
 *         auto-mounting:
 *           excluded-tools:
 *             - someDangerousTool
 *             - anotherToolToKeepManual
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.agents.tools.auto-mounting")
@Data
public class AgentsToolsAutoMountingConfig {

	/**
	 * Tool (function) names never auto-mounted onto an agent that enables tools auto
	 * mounting ({@code subscribeAllTools == true}). Defaults to an empty list (no
	 * exclusions).
	 */
	private List<String> excludedTools = new ArrayList<>();

	/**
	 * Ids of the {@code IGToolCallbackSource}s whose tools must never be
	 * auto-mounted. Every tool contributed by a source listed here is excluded from
	 * automatic mounting as a block, without having to enumerate each tool name.
	 * This is how a whole family of tools (e.g. the standard search tools source) is
	 * kept out of the default agents network's auto-mounting agents. Defaults to an
	 * empty list.
	 */
	private List<String> excludedToolSources = new ArrayList<>();

	/**
	 * @return {@code true} when the given tool name is explicitly configured to be
	 *         excluded from automatic tool mounting (by name; source-based exclusion
	 *         is resolved by the agent service against the tool repository).
	 */
	public boolean isExcluded(String toolName) {
		return toolName != null && excludedTools != null && excludedTools.contains(toolName);
	}
}
