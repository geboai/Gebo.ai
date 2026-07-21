package ai.gebo.microservices_cluster_setup_use.tests.model;

import java.util.ArrayList;

/**
 * Registered chat session to replay against the running microservices cluster.
 * <p>
 * Mirrors {@code ai.gebo.full_setup_use.tests.model.RegisteredInteractionTestSession}
 * from {@code integration-tests/full-setup-and-use-integration-tests}, but lives
 * in this standalone module so the parent pom never has to depend on
 * {@code gebo.architecture.integration.tests} (which would pull the whole root
 * reactor in).
 */
public class RegisteredInteractionTestSession
		extends ArrayList<RegisteredInteractionTestModel> {
}
