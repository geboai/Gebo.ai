package ai.gebo.full_setup_use.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.googlesearch.handler.impl.GoogleSearchServiceImpl;
import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.DeepSearchRequest;
import ai.gebo.monolithic.api.client.model.DeepSearchResponse;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class DeepSearchTests extends AbstractVendorSetupAndUseTest {

	@Test
	public void runDeepSearchTest() throws JsonMappingException, JsonProcessingException {
		TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
		ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
				systemInfo.getSecurityHeader());
		GeboDeepSearchControllerApi deepSearchApi = new GeboDeepSearchControllerApi(apiClient);
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		
		deepSearchRequest.setCode(UUID.randomUUID().toString());
		deepSearchRequest.setKnowledgeBases(List.of());
		deepSearchRequest.setDeepSearchDataSources(List.of(GoogleSearchServiceImpl.GOOGLE_SEARCH_SERVICE));
		deepSearchRequest.setQuery("Do a research on the actual Ucraine war situation");
		DeepSearchResponse deepSearchResult = deepSearchApi.doDeepSearch(deepSearchRequest);
		assertFalse(deepSearchResult == null || deepSearchResult.getResponse() == null
				|| deepSearchResult.getResponse().trim().length() == 0, "The deep search must give a result");
		LOGGER.info(deepSearchResult.getResponse());

	}
}
