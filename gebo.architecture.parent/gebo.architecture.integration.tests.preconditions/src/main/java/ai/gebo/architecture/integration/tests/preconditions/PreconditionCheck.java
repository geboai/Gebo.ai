package ai.gebo.architecture.integration.tests.preconditions;

import java.util.List;

/**
 * A precondition not covered by the built-in annotations, declared with
 * {@link RequiresCustom}. Implementations need a public no argument
 * constructor.
 */
@FunctionalInterface
public interface PreconditionCheck {

	/**
	 * @return one line per missing precondition, saying what is missing and how
	 *         to provide it; empty when everything is in place
	 */
	List<String> missing();
}
