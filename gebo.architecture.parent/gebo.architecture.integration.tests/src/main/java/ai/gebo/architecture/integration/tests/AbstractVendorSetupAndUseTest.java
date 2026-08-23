package ai.gebo.architecture.integration.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.qdrant.QdrantContainer;

import ai.gebo.architecture.integration.tests.model.IntegrationTestSetup;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.architecture.integration.tests.model.TestLLMSetup;
import ai.gebo.architecture.integration.tests.model.TestSubsystemSetupInfo;
import ai.gebo.architecture.integration.tests.model.TestSubsystemSetupInfo.Product;
import ai.gebo.architecture.integration.tests.model.TestSystemSetup;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.model.OperationStatus;
import ai.gebo.monolithic.api.client.api.AuthControllerApi;
import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;
import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.GeboFastInstallationSetupControllerApi;
import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;
import ai.gebo.monolithic.api.client.api.GoogleDriveSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.GoogleSearchConfigurationControllerApi;
import ai.gebo.monolithic.api.client.api.JiraSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;
import ai.gebo.monolithic.api.client.api.SecretsControllerApi;
import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;
import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;
import ai.gebo.monolithic.api.client.api.TokenRenewControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.AuthResponse;
import ai.gebo.monolithic.api.client.model.BraveSearchConfig;
import ai.gebo.monolithic.api.client.model.ComponentSetupStatus;
import ai.gebo.monolithic.api.client.model.ComputedWorkflowResult;
import ai.gebo.monolithic.api.client.model.ComputedWorkflowStatus;
import ai.gebo.monolithic.api.client.model.FastConfluenceSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.FastConfluenceSystemInsertRequest.ConfluenceVersionEnum;
import ai.gebo.monolithic.api.client.model.FastGoogleDriveSystemInsert;
import ai.gebo.monolithic.api.client.model.FastInstallationSetupData;
import ai.gebo.monolithic.api.client.model.FastJiraSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.FastSharepointSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.FastSharepointSystemInsertRequest.SharepointVersionEnum;
import ai.gebo.monolithic.api.client.model.GBraveSearchApiCredentials;
import ai.gebo.monolithic.api.client.model.GGoogleSearchApiCredentials;
import ai.gebo.monolithic.api.client.model.GSearxngSearchApiCredentials;
import ai.gebo.monolithic.api.client.model.GSerpapiSearchApiCredentials;
import ai.gebo.monolithic.api.client.model.GTavilySearchApiCredentials;
import ai.gebo.monolithic.api.client.model.GUserMessage;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.GeboOauth2SecretContent;
import ai.gebo.monolithic.api.client.model.GeboTokenContent;
import ai.gebo.monolithic.api.client.model.GoogleSearchConfig;
import ai.gebo.monolithic.api.client.model.JobSummary;
import ai.gebo.monolithic.api.client.model.LLMAutoconfigureCreationData;
import ai.gebo.monolithic.api.client.model.LLMModelPresetChoice;
import ai.gebo.monolithic.api.client.model.LLMSModelsPresets;
import ai.gebo.monolithic.api.client.model.LLMSModelsPresets.TypeEnum;
import ai.gebo.monolithic.api.client.model.LLMSSetupConfiguration;
import ai.gebo.monolithic.api.client.model.LLMSSetupConfigurationData;
import ai.gebo.monolithic.api.client.model.LoginRequest;
import ai.gebo.monolithic.api.client.model.OperationStatusAuthResponse;
import ai.gebo.monolithic.api.client.model.OperationStatusBoolean;
import ai.gebo.monolithic.api.client.model.OperationStatusGConfluenceSystem;
import ai.gebo.monolithic.api.client.model.OperationStatusGGoogleDriveSystem;
import ai.gebo.monolithic.api.client.model.OperationStatusGJiraSystem;
import ai.gebo.monolithic.api.client.model.OperationStatusGSharepointContentManagementSystem;
import ai.gebo.monolithic.api.client.model.OperationStatusList;
import ai.gebo.monolithic.api.client.model.OperationStatusListGBaseModelConfig;
import ai.gebo.monolithic.api.client.model.SearxngSearchConfig;
import ai.gebo.monolithic.api.client.model.SecretInfo;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboTokenContent;
import ai.gebo.monolithic.api.client.model.SerpapiSearchConfig;
import ai.gebo.monolithic.api.client.model.SecurityHeaderData;
import ai.gebo.monolithic.api.client.model.TavilySearchConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.core.JacksonException;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

/****************************************************************************************
 * Base for llm multivendor integration tests, taking admin account, llm vendor
 * presettings,subsystems integrations setup from a secret (in json format)
 */

public class AbstractVendorSetupAndUseTest extends AbstractGeboMonolithicIntegrationTests {
	protected static QdrantContainer qdrantContainer = new QdrantContainer("qdrant/qdrant:latest");
	protected static boolean qdrantStartedUp = false;
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected static final String FULL_SETUP_ENVIRONMENT_JSON_STRING = "FullSetupSecret";
	protected static ObjectMapper objectMapper = new ObjectMapper();
	static {
		// Supporto per java.time.*
		// object
		// Date -> ISO-8601 (string), non timestamp numerico
		// objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// Date parsing ISO-8601 robusto (accetta anche offset/Z)
		// objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		// Timezone coerente (scegli UTC per evitare sorprese tra ambienti)
		// objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
		// Opzionali ma spesso utili
		// objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
		// objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	@Autowired
	protected GeboVectorStoreConfigurationService vectorStoreConfigurationService;
	@Autowired
	protected Environment environment;
	/**
	 * Port the in-process Gebo.ai web server actually bound to. Populated by
	 * Spring Boot when the test context boots with {@code WebEnvironment.RANDOM_PORT}
	 * (or {@code DEFINED_PORT}); remains {@code 0} for non-web contexts. When set,
	 * it overrides the {@code host}/{@code port} declared in the
	 * {@code FullSetupSecret} setup so that every API client built from
	 * {@link #executeSystemSetupBySecret()} points at the in-process server instead
	 * of a fixed port (e.g. 12999), preventing collisions with any other Gebo.ai
	 * instance already running on that fixed port.
	 */
	@Value("${local.server.port:0}")
	protected int localServerPort = 0;

	protected ApiClient createApiClient(String host, int port, SecurityHeaderData header) {
		RestTemplate restTemplate = createRestTemplate();

		ApiClient geboClient = new ApiClient(restTemplate);
		geboClient.setBasePath("http://" + host + ":" + port);
		if (header != null) {
			geboClient.setApiKey(header.getToken());
		}
		return geboClient;
	}

	protected void renew(ApiClient apiClient) {
		TokenRenewControllerApi tr = new TokenRenewControllerApi(apiClient);
		SecurityHeaderData renewed = tr.renew();
		apiClient.setApiKey(renewed.getToken());
	}

	static class ChatRequestPlaybook extends ArrayList<GeboChatRequest> {
	}

	protected List<GeboChatRequest> loadChatRequests(String resource) throws IOException {
		InputStream is = getClass().getResourceAsStream(resource);
		if (is == null) {
			java.nio.file.Path path = java.nio.file.Path.of(resource);
			if (Files.exists(path)) {
				is = Files.newInputStream(path);
			}
		}
		if (is == null)
			throw new FileNotFoundException(
					"The resource " + resource + " cannot be load nor as intern or extern resource");
		return mapper.readValue(is, ChatRequestPlaybook.class);
	}

	protected RestTemplate createRestTemplate() {
		RestTemplate rt = new RestTemplate();

		// Rimuovi eventuali converter Jackson predefiniti
		List<HttpMessageConverter<?>> converters = new ArrayList<>(rt.getMessageConverters());
		converters.removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);

		// Crea il converter Jackson con il tuo mapper
		MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();

		// (Opzionale) aggiungi anche text/plain se alcuni endpoint rispondono JSON con
		// content-type sbagliato
		List<MediaType> mediaTypes = new ArrayList<>(jackson.getSupportedMediaTypes());
		mediaTypes.add(MediaType.TEXT_PLAIN);
		jackson.setSupportedMediaTypes(mediaTypes);

		// Metti il converter Jackson in testa (priorità)
		converters.add(0, jackson);
		rt.setMessageConverters(converters);
		return rt;
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
		assertFalse(result.isHasErrorMessages(), "Change of vector store to qdrant have to be without errors");
		LOGGER.info("Change to qdrant vector store: " + mapper.writeValueAsString(result));
		vectorStoreConfigurationService.save(actualConfiguration);
	}

	@Override
	protected void enableWorkflowSteps(GKnowledgeBase kb, GProject project, GProjectEndpoint endpoint)
			throws GeboPersistenceException {

	}

	protected SecurityHeaderData executingPredefinedSystemSetup(@NotNull @Valid TestSystemSetup setup) {
		return executingPredefinedSystemSetup(setup.getUsername(), setup.getPassword(), setup.getVendorId(),
				setup.getVendorUser(), setup.getVendorApiKey(), setup.getHost(), setup.getPort(), setup.getModels());
	}

	protected SecurityHeaderData executingPredefinedSystemSetup(String username, String password, String vendorId,
			String vendorUser, String vendorApiKey, String host, int port, List<TestLLMSetup> models) {
		LOGGER.info("Begin executingPredefinedSystemSetup(...)");
		ApiClient geboClient = createApiClient(host, port, null);
		geboClient.setBasePath("http://" + host + ":" + port);
		LOGGER.info("Running initial admin registration setup"); 
		GeboFastInstallationSetupControllerApi fastInstallationSetup = new GeboFastInstallationSetupControllerApi(
				geboClient);
		FastInstallationSetupData installationSetupData = new FastInstallationSetupData();
		installationSetupData.setLang("en");
		installationSetupData.setLicenceAgreement("Here's the blody licence");
		installationSetupData.setUsername(username);
		installationSetupData.setPassword(password);
		installationSetupData.setPasswordC(password);
		OperationStatusBoolean setupResult = fastInstallationSetup.createSetup(installationSetupData);
		printMessages(setupResult.getMessages());
		assertFalse(setupResult.isHasErrorMessages(), "The setup cannot return errors");
		LOGGER.info("Initial admin registration setup SUCCESSFULL!!");
		LOGGER.info("Logging in as admin");
		AuthControllerApi authController = new AuthControllerApi(geboClient);
		LoginRequest login = new LoginRequest();
		login.setUsername(username);
		login.setPassword(password);
		OperationStatusAuthResponse authResult = authController.authenticateUser(login);
		printMessages(authResult.getMessages());
		assertFalse(authResult.isHasErrorMessages(), "The login cannot return errors");
		AuthResponse currentAuth = authResult.getResult();
		geboClient.setApiKey(currentAuth.getSecurityHeaderData().getToken());
		// Create llms api key
		SecretsControllerApi secretsApi = new SecretsControllerApi(geboClient);
		SecretWrapperGeboTokenContent llmTokenSecret = new SecretWrapperGeboTokenContent();
		llmTokenSecret.setDescription("Api key for " + vendorId);
		llmTokenSecret.setContextCode(vendorId);
		GeboTokenContent llmTokenContent = new GeboTokenContent();
		llmTokenContent.setUser(vendorUser);
		llmTokenContent.setToken(vendorApiKey);
		llmTokenSecret.setSecretContent(llmTokenContent);
		SecretInfo secretInfo = secretsApi.createTokenSecret(llmTokenSecret);
		LOGGER.info("Created llm api key secret: " + secretInfo.getCode());
		GeboFastLlmsSetupControllerApi llmSetupApi = new GeboFastLlmsSetupControllerApi(geboClient);
		LLMSSetupConfigurationData configuration = llmSetupApi.getActualLLMSConfiguration();
		Optional<LLMSSetupConfiguration> vendorInfoOptional = configuration.getConfigurations().stream().filter(
				x -> x.getParentModel().getVendorId() != null && x.getParentModel().getVendorId().equals(vendorId))
				.findFirst();
		assertFalse(vendorInfoOptional.isEmpty(),
				"The vendor passed (" + vendorId + ") must match some configuration and presets");
		LLMSSetupConfiguration vendorInfos = vendorInfoOptional.get();

		LLMSModelsPresets chatPresets = vendorInfos.getLibraryModel().stream().filter(x -> x.getType() == TypeEnum.CHAT)
				.findFirst().get();
		LLMSModelsPresets embeddingPresets = vendorInfos.getLibraryModel().stream()
				.filter(x -> x.getType() == TypeEnum.EMBEDDING).findFirst().get();
		LLMAutoconfigureCreationData autoConfigureData = new LLMAutoconfigureCreationData();
		autoConfigureData.setVendorId(vendorId);
		autoConfigureData.setSecretId(secretInfo.getCode());
		if (models == null || models.isEmpty()) {
			List<LLMModelPresetChoice> chatModelChoices = chatPresets.getChoices();
			List<LLMModelPresetChoice> embeddingChoices = embeddingPresets.getChoices();
			for (LLMModelPresetChoice chatChoice : chatModelChoices) {
				if (chatChoice.isDefaultChoice() != null && chatChoice.isDefaultChoice()) {
					autoConfigureData.setDefaultChatModel(chatChoice.getCode());
				}
				if (chatChoice.getUses() != null
						&& chatChoice.getUses().contains(LLMModelPresetChoice.UsesEnum.INTERNAL_SERVICES)) {
					autoConfigureData.setInternalServicesModel(chatChoice.getCode());
				}
			}
			for (LLMModelPresetChoice embedChoice : embeddingChoices) {
				if (embedChoice.isDefaultChoice() != null && embedChoice.isDefaultChoice()) {
					autoConfigureData.setEmbeddingModel(embedChoice.getCode());
				}
			}
		} else {
			for (TestLLMSetup model : models) {
				switch (model.getRole()) {
				case DEFAULT_CHAT: {
					autoConfigureData.setDefaultChatModel(model.getModelCode());
				}
					break;
				case DEFAULT_EMBEDDING: {
					autoConfigureData.setEmbeddingModel(model.getModelCode());
				}
					break;
				case INTERNAL_SERVICES: {
					autoConfigureData.setInternalServicesModel(model.getModelCode());
				}
					break;
				}
			}
		}
		assertFalse(
				autoConfigureData.getDefaultChatModel() == null || autoConfigureData.getEmbeddingModel() == null
						|| autoConfigureData.getInternalServicesModel() == null,
				"Setup defaultChatModel,embeddingModel,internalServicesModel have to be not null");
		OperationStatusListGBaseModelConfig llmCreationInfos = llmSetupApi.createLLMByAutoconfigure(autoConfigureData);
		printMessages(llmCreationInfos.getMessages());
		assertFalse(llmCreationInfos.isHasErrorMessages(),
				"The vendor passed (" + vendorId + ") cannot be setup correctly");
		LOGGER.info("End executingPredefinedSystemSetup(...)");
		TokenRenewControllerApi tokenRenewApi = new TokenRenewControllerApi(geboClient);
		SecurityHeaderData securityHeader = tokenRenewApi.renew();
		return securityHeader;
	}

	protected SecurityHeaderData renew(@NotNull @Valid SecurityHeaderData securityHeader, String host, int port) {
		ApiClient geboClient = createApiClient(host, port, securityHeader);
		TokenRenewControllerApi tokenRenewApi = new TokenRenewControllerApi(geboClient);
		return tokenRenewApi.renew();
	}

	/**
	 * Configures every subsystem declared in the setup secret against a freshly
	 * installed Gebo.ai.
	 * <p>
	 * Web search gets special treatment. Since the multi-provider refactoring the
	 * monolith exposes a provider's web-search tool to the LLM only while that
	 * provider has stored credentials, so <em>exactly one</em> provider is meant to
	 * be active, and the admin wizard enforces it by clearing every provider before
	 * storing the selected one. This method applies the same rule: the setup secret
	 * may name at most one {@code *_SEARCH} product, every provider is wiped first,
	 * and after the insert the single-active invariant is asserted against the
	 * per-provider status endpoints.
	 */
	protected void setupProducts(@NotNull @Valid List<TestSubsystemSetupInfo> setupInfos,
			SecurityHeaderData securityHeader, String host, int port) {
		ApiClient geboClient = createApiClient(host, port, securityHeader);
		List<TestSubsystemSetupInfo> webSearchSetups = setupInfos.stream()
				.filter(x -> x.getProduct() != null && x.getProduct().isWebSearchProvider()).toList();
		assertTrue(webSearchSetups.size() <= 1,
				"Only ONE web search provider can be active at a time, the setup secret declares: "
						+ webSearchSetups.stream().map(x -> x.getProduct().name()).toList());
		if (!webSearchSetups.isEmpty()) {
			// Same order as the admin wizard: wipe every provider, then store the chosen
			// one, so a re-run over an already configured system cannot leave two active.
			clearAllWebSearchProviders(geboClient);
		}
		for (TestSubsystemSetupInfo productSetupInfo : setupInfos) {
			switch (productSetupInfo.getProduct()) {
			case GOOGLE_SEARCH: {
				GoogleSearchConfigurationControllerApi api = new GoogleSearchConfigurationControllerApi(geboClient);
				GoogleSearchConfig searchConfig = new GoogleSearchConfig();
				searchConfig.setEnabled(true);
				searchConfig.setApiKey(productSetupInfo.getApiKey());
				searchConfig.setCustomSearchEngineId(productSetupInfo.getId());
				GGoogleSearchApiCredentials outcome = api.fastInsertGoogleSearchApiCredentials(searchConfig);
				LOGGER.info("Inserted google search api account config:" + outcome.getCode());
			}
				break;
			case TAVILY_SEARCH: {
				TavilySearchConfigurationControllerApi api = new TavilySearchConfigurationControllerApi(geboClient);
				TavilySearchConfig searchConfig = new TavilySearchConfig();
				searchConfig.setEnabled(true);
				searchConfig.setApiKey(productSetupInfo.getApiKey());
				GTavilySearchApiCredentials outcome = api.fastInsertTavilySearchApiCredentials(searchConfig);
				LOGGER.info("Inserted tavily search api account config:" + outcome.getCode());
			}
				break;
			case BRAVE_SEARCH: {
				BraveSearchConfigurationControllerApi api = new BraveSearchConfigurationControllerApi(geboClient);
				BraveSearchConfig searchConfig = new BraveSearchConfig();
				searchConfig.setEnabled(true);
				searchConfig.setApiKey(productSetupInfo.getApiKey());
				GBraveSearchApiCredentials outcome = api.fastInsertBraveSearchApiCredentials(searchConfig);
				LOGGER.info("Inserted brave search api account config:" + outcome.getCode());
			}
				break;
			case SEARXNG_SEARCH: {
				SearxngSearchConfigurationControllerApi api = new SearxngSearchConfigurationControllerApi(geboClient);
				SearxngSearchConfig searchConfig = new SearxngSearchConfig();
				searchConfig.setEnabled(true);
				// Self-hosted: the instance URL is what identifies it, the key is optional.
				searchConfig.setBaseUrl(productSetupInfo.getBasePath());
				searchConfig.setApiKey(productSetupInfo.getApiKey());
				GSearxngSearchApiCredentials outcome = api.fastInsertSearxngSearchApiCredentials(searchConfig);
				LOGGER.info("Inserted searxng search api account config:" + outcome.getCode());
			}
				break;
			case SERPAPI_SEARCH: {
				SerpapiSearchConfigurationControllerApi api = new SerpapiSearchConfigurationControllerApi(geboClient);
				SerpapiSearchConfig searchConfig = new SerpapiSearchConfig();
				searchConfig.setEnabled(true);
				searchConfig.setApiKey(productSetupInfo.getApiKey());
				GSerpapiSearchApiCredentials outcome = api.fastInsertSerpapiSearchApiCredentials(searchConfig);
				LOGGER.info("Inserted serpapi search api account config:" + outcome.getCode());
			}
				break;
			case SHAREPOINT: {
				SharepointSystemsControllerApi api = new SharepointSystemsControllerApi(geboClient);
				FastSharepointSystemInsertRequest config = new FastSharepointSystemInsertRequest();
				config.setBaseUri(productSetupInfo.getBasePath());
				config.setDescription("Sharepoint system");

				GeboOauth2SecretContent oauth2Credentials = productSetupInfo.getOauth2Credentials();
				config.setOauth2Credentials(oauth2Credentials);
				config.setSharepointVersion(SharepointVersionEnum.CLOUD_VERSION);
				OperationStatusGSharepointContentManagementSystem outcome = api.fastSharepointConfig(config);
				printMessages(outcome.getMessages());
				assertFalse(outcome.isHasErrorMessages(), "Sharepoint configuration cannot lead to errors");
			}
				break;
			case CONFLUENCE_CLOUD:
			case CONFLUENCE_ONPREMISE: {
				ConfluenceSystemsControllerApi confluenceSystems = new ConfluenceSystemsControllerApi(geboClient);
				FastConfluenceSystemInsertRequest config = new FastConfluenceSystemInsertRequest();
				config.baseUri(productSetupInfo.getBasePath());
				config.confluenceVersion(
						productSetupInfo.getProduct() == Product.CONFLUENCE_CLOUD ? ConfluenceVersionEnum.CLOUD
								: ConfluenceVersionEnum.ONPREMISE7X);
				config.setDescription("Confluence system");
				config.setUsername(productSetupInfo.getUser());
				config.setPassword(productSetupInfo.getApiKey());
				config.setToken(productSetupInfo.getApiKey());
				OperationStatusGConfluenceSystem outcome = confluenceSystems.fastConfluenceConfig(config);
				printMessages(outcome.getMessages());
				assertFalse(outcome.isHasErrorMessages(), "Confluence configuration cannot lead to errors");

			}
				break;
			case GOOGLE_WORKSPACE: {
				GoogleDriveSystemsControllerApi api = new GoogleDriveSystemsControllerApi(geboClient);
				FastGoogleDriveSystemInsert config = new FastGoogleDriveSystemInsert();
				config.setDescription("Google drive system");
				config.setGoogleJsonCredentials(productSetupInfo.getGoogleJsonCredentials());
				OperationStatusGGoogleDriveSystem outcome = api.fastGoogleDriveConfig(config);
				printMessages(outcome.getMessages());
				assertFalse(outcome.isHasErrorMessages(), "Google drive configuration cannot lead to errors");
			}
				break;
			case JIRA_CLOUD: {
				JiraSystemsControllerApi api = new JiraSystemsControllerApi(geboClient);
				FastJiraSystemInsertRequest config = new FastJiraSystemInsertRequest();
				config.setBaseUri(productSetupInfo.getBasePath());
				config.setToken(productSetupInfo.getApiKey());
				config.setPassword(productSetupInfo.getApiKey());
				config.setUsername(productSetupInfo.getUser());
				config.setDescription("Jira system");
				OperationStatusGJiraSystem outcome = api.fastJiraConfig(config);
				printMessages(outcome.getMessages());
				assertFalse(outcome.isHasErrorMessages(), "Jira configuration cannot lead to errors");
			}
				break;
			}

		}
		if (!webSearchSetups.isEmpty()) {
			assertSingleActiveWebSearchProvider(geboClient, webSearchSetups.get(0).getProduct());
		}
	}

	/**
	 * Deletes the stored credentials of every web-search provider, which is what
	 * "deactivates" them: each provider's tool callback and its {@code isEnabled()}
	 * are gated on its credentials repository being non-empty.
	 */
	protected void clearAllWebSearchProviders(ApiClient geboClient) {
		GoogleSearchConfigurationControllerApi google = new GoogleSearchConfigurationControllerApi(geboClient);
		for (GGoogleSearchApiCredentials credentials : google.getGoogleSearchApiCredentials()) {
			google.deleteGGoogleSearchApiCredentials(credentials);
		}
		TavilySearchConfigurationControllerApi tavily = new TavilySearchConfigurationControllerApi(geboClient);
		for (GTavilySearchApiCredentials credentials : tavily.getTavilySearchApiCredentials()) {
			tavily.deleteGTavilySearchApiCredentials(credentials);
		}
		BraveSearchConfigurationControllerApi brave = new BraveSearchConfigurationControllerApi(geboClient);
		for (GBraveSearchApiCredentials credentials : brave.getBraveSearchApiCredentials()) {
			brave.deleteGBraveSearchApiCredentials(credentials);
		}
		SearxngSearchConfigurationControllerApi searxng = new SearxngSearchConfigurationControllerApi(geboClient);
		for (GSearxngSearchApiCredentials credentials : searxng.getSearxngSearchApiCredentials()) {
			searxng.deleteGSearxngSearchApiCredentials(credentials);
		}
		SerpapiSearchConfigurationControllerApi serpapi = new SerpapiSearchConfigurationControllerApi(geboClient);
		for (GSerpapiSearchApiCredentials credentials : serpapi.getSerpapiSearchApiCredentials()) {
			serpapi.deleteGSerpapiSearchApiCredentials(credentials);
		}
		LOGGER.info("Cleared the credentials of every web search provider");
	}

	/**
	 * Asserts the post-condition of the single-active rule: the expected provider
	 * reports itself set up and every other one does not.
	 */
	protected void assertSingleActiveWebSearchProvider(ApiClient geboClient, Product expected) {
		GoogleSearchConfigurationControllerApi google = new GoogleSearchConfigurationControllerApi(geboClient);
		TavilySearchConfigurationControllerApi tavily = new TavilySearchConfigurationControllerApi(geboClient);
		BraveSearchConfigurationControllerApi brave = new BraveSearchConfigurationControllerApi(geboClient);
		SearxngSearchConfigurationControllerApi searxng = new SearxngSearchConfigurationControllerApi(geboClient);
		SerpapiSearchConfigurationControllerApi serpapi = new SerpapiSearchConfigurationControllerApi(geboClient);
		assertWebSearchProviderStatus(Product.GOOGLE_SEARCH, expected, google.getGoogleSearchStatus());
		assertWebSearchProviderStatus(Product.TAVILY_SEARCH, expected, tavily.getTavilySearchStatus());
		assertWebSearchProviderStatus(Product.BRAVE_SEARCH, expected, brave.getBraveSearchStatus());
		assertWebSearchProviderStatus(Product.SEARXNG_SEARCH, expected, searxng.getSearxngSearchStatus());
		assertWebSearchProviderStatus(Product.SERPAPI_SEARCH, expected, serpapi.getSerpapiSearchStatus());
		LOGGER.info("Active web search provider: " + expected);
	}

	private void assertWebSearchProviderStatus(Product provider, Product expected, ComponentSetupStatus status) {
		boolean isSetup = status != null && Boolean.TRUE.equals(status.isIsSetup());
		if (provider == expected) {
			assertTrue(isSetup, "The web search provider " + provider + " declared by the setup secret must be active");
		} else {
			assertFalse(isSetup, "Only " + expected + " may be active, but " + provider + " is configured too");
		}
	}

	protected void printMessages(List<GUserMessage> messages) {
		if (messages != null) {
			for (GUserMessage msg : messages) {
				if (msg.getSeverity() != null) {
					switch (msg.getSeverity()) {
					case WARN: {
						LOGGER.warn(msg.getSummary() + " -> " + msg.getDetail());
					}
						break;
					case INFO: {
						LOGGER.info("(info) " + msg.getSummary() + " -> " + msg.getDetail());
					}
						break;

					case SUCCESS: {
						LOGGER.info("(success)" + msg.getSummary() + " -> " + msg.getDetail());
					}
						break;
					case ERROR:
					default: {
						LOGGER.error(msg.getSummary() + " -> " + msg.getDetail());
					}
						break;
					}
				}
			}
		}
	}

	protected TestGeboSystemInfo executingSystemSetup(@NotNull @Valid IntegrationTestSetup setup) {
		SecurityHeaderData header = executingPredefinedSystemSetup(setup.getSystemSetup());
		if (setup.getSubsystems() != null && !setup.getSubsystems().isEmpty()) {
			setupProducts(setup.getSubsystems(), header, setup.getSystemSetup().getHost(),
					setup.getSystemSetup().getPort());
		}
		header = renew(header, setup.getSystemSetup().getHost(), setup.getSystemSetup().getPort());
		return new TestGeboSystemInfo(setup.getSystemSetup().getHost(), setup.getSystemSetup().getPort(),
				setup.getSystemSetup().getUsername(), setup.getSystemSetup().getPassword(), header);
	}

	protected TestGeboSystemInfo executeSystemSetupBySecret() throws DatabindException, JacksonException {
		String jsonSetup = environment.getProperty(FULL_SETUP_ENVIRONMENT_JSON_STRING);
		assertFalse(jsonSetup == null || jsonSetup.trim().length() == 0, "The environment property "
				+ FULL_SETUP_ENVIRONMENT_JSON_STRING + " must contain a valid json setup configuration");
		ObjectMapper objectMapper = new ObjectMapper();
		IntegrationTestSetup setup = objectMapper.readValue(jsonSetup, IntegrationTestSetup.class);
		// Redirect every client at the in-process server's actual port so the suite
		// never competes with another Gebo.ai instance bound to the fixed 12999 port.
		if (localServerPort > 0 && setup.getSystemSetup() != null) {
			setup.getSystemSetup().setHost("localhost");
			setup.getSystemSetup().setPort(localServerPort);
		}
		return executingSystemSetup(setup);
	}

	protected void printSummary(JobSummary summary) {
		LOGGER.info("************ LOG FOR JOB:" + summary.getCode()
				+ " **************************************************");
		ComputedWorkflowResult status = summary.getWorkflowStatus();
		printStatus(status.getRootStatus(), "-");
		LOGGER.info("************************************************************************************************");
	}

	protected void printStatus(ComputedWorkflowStatus rootStatus, String prefix) {
		LOGGER.info(prefix + " stepId:" + rootStatus.getWorkflowStepId() + " input:"
				+ rootStatus.getBatchDocumentsInput() + " processed:" + rootStatus.getBatchDocumentsProcessed()
				+ " errors:" + rootStatus.getBatchDocumentsProcessingErrors() + " tokens: "
				+ rootStatus.getTokensProcessed());
		for (ComputedWorkflowStatus childStatus : rootStatus.getChilds()) {
			printStatus(childStatus, prefix + prefix);
		}

	}

	protected <T> T loadJsonDataModel(String resource, Class<T> type)
			throws StreamReadException, DatabindException, IOException {
		InputStream is = getClass().getResourceAsStream(resource);
		if (is == null)
			is = getClass().getClassLoader().getResourceAsStream(resource);
		if (is == null)
			throw new IllegalStateException("The resource " + resource + " is not found");
		try {
			return mapper.readValue(is, type);
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
		}

	}

}
