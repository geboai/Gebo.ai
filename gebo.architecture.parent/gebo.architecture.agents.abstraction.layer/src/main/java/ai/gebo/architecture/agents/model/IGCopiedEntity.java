package ai.gebo.architecture.agents.model;

/**
 * An entity that can be stored as the copy of another one: while the copy is being
 * inserted it carries the code of the original, and its own code is made from it
 * instead of from its description (see
 * {@code ai.gebo.architecture.agents.services.impl.GAbstractCopyCodeGenerator}).
 */
public interface IGCopiedEntity {

	/**
	 * @return the code of the entity this one is a copy of, null when it is not a
	 *         copy being inserted
	 */
	String getCopiedFromCode();

	void setCopiedFromCode(String copiedFromCode);
}
