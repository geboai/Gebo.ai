package ai.gebo.full_setup_use.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.file.PathUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.googlesearch.handler.impl.GoogleSearchServiceImpl;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.repository.DeepSearchDataSourceDocumentResultRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchDataSourceResponseRepository;
import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.DeepSearchRequest;
import ai.gebo.monolithic.api.client.model.DeepSearchResponse;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;
import ai.gebo.system.ingestion.GeboIngestionException;
import jakarta.validation.constraints.AssertFalse;
import reactor.core.publisher.Flux;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class DeepSearchTests extends AbstractVendorSetupAndUseTest {
	@Autowired
	IGRuntimeBinder runtimeBinder;

	@Test
	public void runDeepSearchTest() throws IOException, DocumentCacheAccessException, GeboContentHandlerSystemException,
			GeboIngestionException, SearchServiceException {
		TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
		ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
				systemInfo.getSecurityHeader());

		GeboDeepSearchControllerApi deepSearchApi = new GeboDeepSearchControllerApi(apiClient);
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();

		deepSearchRequest.setCode(UUID.randomUUID().toString());
		deepSearchRequest.setKnowledgeBases(List.of());
		deepSearchRequest.setDeepSearchDataSources(List.of(GoogleSearchServiceImpl.GOOGLE_SEARCH_SERVICE));
		deepSearchRequest.setQuery(
				"Do a research on the actual Ucraine war situation in begin of 2026 and forecasts regarding its end");
		long time = System.currentTimeMillis();
		DeepSearchResponse deepSearchResult = deepSearchApi.doDeepSearch(deepSearchRequest);
		long timeExecuted = System.currentTimeMillis();

		assertFalse(deepSearchResult == null || deepSearchResult.getResponse() == null
				|| deepSearchResult.getResponse().trim().length() == 0, "The deep search must give a result");
		LOGGER.info("TIMING!! Deep search executed in " + (timeExecuted - time) + " msec");
		LOGGER.info(deepSearchResult.getResponse());
		Path path = Path.of("deep-search-result.md");
		Files.write(path, deepSearchResult.getResponse().getBytes(), StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		DeepSearchDataSourceDocumentResultRepository dataSourceResultsRepo = runtimeBinder
				.getImplementationOf(DeepSearchDataSourceDocumentResultRepository.class);
		DeepSearchDataSourceResponseRepository responsesRepo = runtimeBinder
				.getImplementationOf(DeepSearchDataSourceResponseRepository.class);
		List<DeepSearchDataSourceDocumentResult> data = dataSourceResultsRepo
				.findByDeepsearchCode(deepSearchRequest.getCode());
		for (DeepSearchDataSourceDocumentResult deepSearchDataSourceDocumentResult : data) {
			LOGGER.info("Data source document result: " + deepSearchDataSourceDocumentResult);
		}
		List<DeepSearchDataSourceResponse> responses = responsesRepo.findByDeepsearchCode(deepSearchRequest.getCode());
		for (DeepSearchDataSourceResponse deepSearchDataSourceResponse : responses) {
			LOGGER.info("Data source consolidated result:" + deepSearchDataSourceResponse);
		}
		assertFalse(data.isEmpty(), "List of produced documents analisys cannot be empty");

		assertFalse(responses.isEmpty(), "List of produced data source result cannot be empty");

	}
}
