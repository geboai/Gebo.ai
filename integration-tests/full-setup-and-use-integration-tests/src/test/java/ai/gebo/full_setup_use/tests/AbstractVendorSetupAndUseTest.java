package ai.gebo.full_setup_use.tests;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testcontainers.qdrant.QdrantContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTests;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.model.OperationStatus;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class AbstractVendorSetupAndUseTest extends AbstractGeboMonolithicIntegrationTests {
	static QdrantContainer qdrantContainer = new QdrantContainer("qdrant/qdrant:latest");
	static boolean qdrantStartedUp = false;
	static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	GeboVectorStoreConfigurationService vectorStoreConfigurationService;
	public AbstractVendorSetupAndUseTest() {

	}
	@BeforeEach
	protected void beforeEachCallback() throws Exception {
		if (!qdrantStartedUp) {
			qdrantContainer.setExposedPorts(List.of(6333, 6334));
			qdrantContainer.start();
			qdrantStartedUp = true;
		}
		GeboMongoVectorStoreConfig actualConfiguration = vectorStoreConfigurationService.getActualConfiguration();
		actualConfiguration.setProduct(VectorStoreProduct.QDRANT);
		actualConfiguration.setQdrantConfig(new QdrantConfig());
		actualConfiguration.getQdrantConfig().setHost("localhost");
		actualConfiguration.getQdrantConfig().setPort(qdrantContainer.getGrpcPort());
		actualConfiguration.getQdrantConfig().setTls(false);
		OperationStatus<GeboMongoVectorStoreConfig> result = vectorStoreConfigurationService
				.validateAndTestConfiguration(actualConfiguration);
		assertFalse("Change of vector store to qdrant have to be without errors", result.isHasErrorMessages());
		LOGGER.info("Change to qdrant vector store: " + mapper.writeValueAsString(result));
		vectorStoreConfigurationService.save(actualConfiguration);
	}
	@Override
	protected void enableWorkflowSteps(GKnowledgeBase kb, GProject project, GProjectEndpoint endpoint)
			throws GeboPersistenceException {

	}

	protected void executingPredefinedSystemSetup(String username, String password, String vendorId, String vendorUser,
			String vendorApiKey, String host, int port) {
		
	}
}
