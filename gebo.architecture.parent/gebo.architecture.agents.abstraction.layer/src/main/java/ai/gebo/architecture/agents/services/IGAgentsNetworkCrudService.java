package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.model.OperationStatus;

/**
 * CRUD service for {@link GAgentsNetwork} configurations on top of the
 * {@link IAgentsNetworkDao}.
 * <p>
 * Every mutating operation is validated before it touches the persistence layer
 * and returns an {@link OperationStatus} carrying both the resulting value (when
 * the operation succeeds) and the set of {@code GUserMessage}s describing the
 * errors, warnings and success notices produced while validating and executing
 * the operation. When validation reports at least one {@code error} message the
 * operation is rejected (nothing is persisted) and the status carries a
 * {@code null} result together with the diagnostic messages.
 * <p>
 * The structural validation enforces what the runtime needs to run the network:
 * the network service factory it declares can allocate its participants (an input
 * node accepting its input, an output node producing its output, the agents
 * answering the user directly only where the factory streams them) and the agents
 * can actually talk to the agents of their communication lists, after the semantic
 * of the messages they produce ({@link IGNetworkAgentService#getProducedMessageSemantic()}):
 * an agent delivering its own output needs it to be the input of the agents it talks
 * to (see {@link #validate(GAgentsNetwork)}).
 */
public interface IGAgentsNetworkCrudService {

	/**
	 * Validates the given network without persisting it.
	 *
	 * @param network the network to validate
	 * @return a status whose result is the validated network when no {@code error}
	 *         message is produced, or {@code null} together with the error messages
	 *         otherwise; warnings/info notices are reported in both cases
	 */
	OperationStatus<GAgentsNetwork> validate(GAgentsNetwork network);

	/**
	 * Validates and inserts a new network. Fails (without persisting) when the
	 * network is structurally invalid or a network with the same code already
	 * exists.
	 */
	OperationStatus<GAgentsNetwork> insert(GAgentsNetwork network);

	/**
	 * Validates and updates an existing network. Fails (without persisting) when the
	 * network is structurally invalid, does not exist yet or is read-only.
	 */
	OperationStatus<GAgentsNetwork> update(GAgentsNetwork network);

	/**
	 * Deletes an existing network. Fails when the network does not exist or is
	 * read-only; warns when deleting the default user-interaction network. The
	 * returned status carries the deleted network as its result on success.
	 */
	OperationStatus<GAgentsNetwork> delete(GAgentsNetwork network);
}
