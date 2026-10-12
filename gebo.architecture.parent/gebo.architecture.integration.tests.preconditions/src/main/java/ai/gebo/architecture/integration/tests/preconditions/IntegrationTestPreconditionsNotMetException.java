package ai.gebo.architecture.integration.tests.preconditions;

/**
 * Raised instead of skipping when {@code testWhatIsConfigured=false} and a test
 * misses some of its preconditions.
 */
public class IntegrationTestPreconditionsNotMetException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IntegrationTestPreconditionsNotMetException(String message) {
		super(message);
	}
}
