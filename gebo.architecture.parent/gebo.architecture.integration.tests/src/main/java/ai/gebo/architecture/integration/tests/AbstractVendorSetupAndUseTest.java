package ai.gebo.architecture.integration.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testcontainers.qdrant.QdrantContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.integration.tests.model.ProductSetupInfo;
import ai.gebo.architecture.integration.tests.model.ProductSetupInfo.Product;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.model.OperationStatus;
import ai.gebo.monolithic.api.client.api.AuthControllerApi;
import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.GeboFastInstallationSetupControllerApi;
import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;
import ai.gebo.monolithic.api.client.api.GoogleDriveSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.GoogleSearchConfigurationControllerApi;
import ai.gebo.monolithic.api.client.api.JiraSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.SecretsControllerApi;
import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.TokenRenewControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.AuthResponse;
import ai.gebo.monolithic.api.client.model.FastConfluenceSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.FastConfluenceSystemInsertRequest.ConfluenceVersionEnum;
import ai.gebo.monolithic.api.client.model.FastGoogleDriveSystemInsert;
import ai.gebo.monolithic.api.client.model.FastInstallationSetupData;
import ai.gebo.monolithic.api.client.model.FastJiraSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.FastSharepointSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.FastSharepointSystemInsertRequest.SharepointVersionEnum;
import ai.gebo.monolithic.api.client.model.GGoogleSearchApiCredentials;
import ai.gebo.monolithic.api.client.model.GUserMessage;
import ai.gebo.monolithic.api.client.model.GeboOauth2SecretContent;
import ai.gebo.monolithic.api.client.model.GeboTokenContent;
import ai.gebo.monolithic.api.client.model.GoogleSearchConfig;
import ai.gebo.monolithic.api.client.model.LLMAutoconfigureCreationData;
import ai.gebo.monolithic.api.client.model.LLMCreateModelData.UsesEnum;
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
import ai.gebo.monolithic.api.client.model.SecretInfo;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboTokenContent;
import ai.gebo.monolithic.api.client.model.SecurityHeaderData;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;

public class AbstractVendorSetupAndUseTest extends AbstractGeboMonolithicIntegrationTests {
	static QdrantContainer qdrantContainer = new QdrantContainer("qdrant/qdrant:latest");
	static boolean qdrantStartedUp = false;
	Logger LOGGER = LoggerFactory.getLogger(getClass());
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

	protected SecurityHeaderData executingPredefinedSystemSetup(String username, String password, String vendorId,
			String vendorUser, String vendorApiKey, String host, int port) {
		LOGGER.info("Begin executingPredefinedSystemSetup(...)");
		ApiClient geboClient = new ApiClient();
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
		List<LLMModelPresetChoice> chatModelChoices = chatPresets.getChoices();
		List<LLMModelPresetChoice> embeddingChoices = embeddingPresets.getChoices();
		for (LLMModelPresetChoice chatChoice : chatModelChoices) {
			if (chatChoice.isDefaultChoice() != null && chatChoice.isDefaultChoice()) {
				autoConfigureData.setDefaultChatModel(chatChoice.getCode());
			}
			if (chatChoice.getUses() != null && chatChoice.getUses().contains(UsesEnum.INTERNAL_SERVICES)) {
				autoConfigureData.setInternalServicesModel(chatChoice.getCode());
			}
		}
		for (LLMModelPresetChoice embedChoice : embeddingChoices) {
			if (embedChoice.isDefaultChoice() != null && embedChoice.isDefaultChoice()) {
				autoConfigureData.setEmbeddingModel(embedChoice.getCode());
			}
		}
		OperationStatusList llmCreationInfos = llmSetupApi.createLLMByAutoconfigure(autoConfigureData);
		printMessages(llmCreationInfos.getMessages());
		assertFalse(llmCreationInfos.isHasErrorMessages(),
				"The vendor passed (" + vendorId + ") cannot be setup correctly");
		LOGGER.info("End executingPredefinedSystemSetup(...)");
		TokenRenewControllerApi tokenRenewApi = new TokenRenewControllerApi(geboClient);
		SecurityHeaderData securityHeader = tokenRenewApi.renew();
		return securityHeader;
	}

	protected SecurityHeaderData renew(SecurityHeaderData securityHeader, String host, int port) {
		ApiClient geboClient = new ApiClient();
		geboClient.setBasePath("http://" + host + ":" + port);
		geboClient.setApiKey(securityHeader.getToken());
		TokenRenewControllerApi tokenRenewApi = new TokenRenewControllerApi(geboClient);
		return tokenRenewApi.renew();

	}

	protected void setupProducts(List<ProductSetupInfo> setupInfos, SecurityHeaderData securityHeader, String host,
			int port) {
		ApiClient geboClient = new ApiClient();
		geboClient.setBasePath("http://" + host + ":" + port);
		geboClient.setApiKey(securityHeader.getToken());
		for (ProductSetupInfo productSetupInfo : setupInfos) {
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
					case ERROR: {
						LOGGER.error(msg.getSummary() + " -> " + msg.getDetail());
					}
						break;
					case SUCCESS: {
						LOGGER.info("(success)" + msg.getSummary() + " -> " + msg.getDetail());
					}
						break;
					}
				}
			}
		}

	}
}
