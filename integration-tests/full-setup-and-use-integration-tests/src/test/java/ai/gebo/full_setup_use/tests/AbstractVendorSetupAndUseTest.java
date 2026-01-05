package ai.gebo.full_setup_use.tests;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTests;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

public class AbstractVendorSetupAndUseTest extends AbstractGeboMonolithicIntegrationTests {

	public AbstractVendorSetupAndUseTest() {
		
	}

	@Override
	protected void enableWorkflowSteps(GKnowledgeBase kb, GProject project, GProjectEndpoint endpoint)
			throws GeboPersistenceException {
		

	}

}
