package ai.gebo.microservices_cluster_setup_use.tests.model;

import java.util.List;

import gebo.microservices.api.client.brain.model.PipelineEnvironment;
import gebo.microservices.api.client.brain.model.GeboChatRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Single interaction in a {@link RegisteredInteractionTestSession}: the user
 * request to replay plus the criteria the response has to satisfy (allowed
 * routing decisions, non-empty documents list, ...).
 * <p>
 * Mirrors {@code ai.gebo.full_setup_use.tests.model.RegisteredInteractionTestModel}
 * but uses the brain microservice stub client models
 * ({@code gebo.microservices.api.client.brain.model.*}) instead of the
 * monolithic client ones. The JSON resources under
 * {@code src/test/resources/registered-interaction-tests/} are the SAME files
 * shipped with the monolith integration-test module, because the wire shape
 * of {@code GeboChatRequest}/{@code PipelineEnvironment} is identical across
 * the monolith and the microservices (the stubs are generated from the same
 * controllers, just split per service).
 */
@Data
public class RegisteredInteractionTestModel {
	private String description;
	@NotNull
	private GeboChatRequest request = null;
	private PipelineEnvironment environment = null;
	private ShallowSearchEnvironment shallowSearchEnvironment = null;
	private DeepSearchEnvironment deepSearchEnvironment = null;
	private ExpectedResponseTestCriteria responseTestCriteria = null;

	@Data
	public static class ShallowSearchEnvironment extends PipelineEnvironment {
		List<String> searchedSystem = null;
	}

	@Data
	public static class DeepSearchEnvironment extends PipelineEnvironment {
		List<String> deepSearchedSystems = null;
	}

	@Data
	public static class ExpectedResponseTestCriteria {
		/** Allowed {@code RespondingWith} routing-decision codes. Stored as
		 * plain strings because the {@code RespondingWith} enum lives in an
		 * internal Gebo module not on the stub-client classpath; the test
		 * driver compares them by name. */
		private List<String> allowedRoutingDecisions = null;
		private List<String> allowedDetectedUserIntent = null;
		private String checkPromptUseCode = null;
		private String structuredResponseClassName = null;
		private String structuredResponseCheckServiceId = null;
		private boolean checkNotEmptyDocumentsList = false;
	}
}
