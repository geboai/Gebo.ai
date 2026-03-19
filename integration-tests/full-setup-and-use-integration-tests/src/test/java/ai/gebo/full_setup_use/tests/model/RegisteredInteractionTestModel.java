package ai.gebo.full_setup_use.tests.model;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.PipelineEnvironment;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
		private List<RespondingWith> allowedRoutingDecisions = null;
		private List<DeliverableIntent> allowedDetectedUserIntent = null;
		private String checkPromptUseCode = null;
		private String structuredResponseClassName = null;
		private String structuredResponseCheckServiceId = null;
		private boolean checkNotEmptyDocumentsList = false;
	}
}
