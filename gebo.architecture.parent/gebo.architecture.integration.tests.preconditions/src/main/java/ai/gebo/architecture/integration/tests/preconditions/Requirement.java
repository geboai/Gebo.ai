package ai.gebo.architecture.integration.tests.preconditions;

/** A single precondition declared by one of the {@code @Requires*} annotations. */
sealed interface Requirement {

	record Docker() implements Requirement {
	}

	record Config(String name, String description) implements Requirement {
	}

	record Endpoint(String name, String url, int timeoutMillis) implements Requirement {
	}

	record LocalImage(String image, String hint) implements Requirement {
	}

	record Directories(String configName) implements Requirement {
	}

	record Custom(Class<? extends PreconditionCheck> type) implements Requirement {
	}
}
