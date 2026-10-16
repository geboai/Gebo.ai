package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.model.OperationStatus;

/**
 * Copies {@link GAgentConfig}s and {@link GAgentsNetwork}s into new, editable
 * entities.
 * <p>
 * A copy is always stored with {@code readOnly=false}, also when the original is a
 * read-only configuration declared by a configuration bean, and it never takes the
 * {@code defaultConfiguration} flag of an agent: the original stays the default
 * configuration of its agent service. The copy's description is the original one
 * marked as a copy ({@code "(copy)"}, {@code "(copy 2)"}, ...), unique among the
 * existing entities of its kind, and so is the code generated from it on insert: a
 * code that a configuration bean already declares is never given to a copy.
 * <p>
 * Every operation returns an {@link OperationStatus} carrying the stored copy, or a
 * {@code null} result when nothing was copied, with the messages describing the
 * outcome.
 */
public interface IGAgentsCopyService {

	/**
	 * Copies the agent configuration with the given code.
	 *
	 * @param code the code of the agent configuration to copy
	 * @return the stored copy, or a {@code null} result with the error messages
	 */
	OperationStatus<GAgentConfig> copyAgent(String code);

	/**
	 * Copies the network of agents with the given code together with its agents.
	 * Every agent configuration taking part in the network is copied once, also when
	 * it takes part more than once under different contextual names, and the copied
	 * network's participants and communication lists refer to the agent copies, so
	 * the copy shares no agent configuration with the original. A network that does
	 * not pass the validation is not copied; when the copy does not pass it, the copy
	 * and its agents are removed.
	 *
	 * @param code the code of the network of agents to copy
	 * @return the stored network copy, or a {@code null} result with the error
	 *         messages
	 */
	OperationStatus<GAgentsNetwork> copyAgentsNetwork(String code);
}
