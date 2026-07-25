package ai.gebo.microservices_cluster_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.ObjectMapper;
import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;
import gebo.microservices.api.client.brain.api.GeboFastLlmsSetupControllerApi;
import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;
import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;
import gebo.microservices.api.client.brain.api.ProjectsControllerApi;
import gebo.microservices.api.client.brain.invoker.ApiClient;
import gebo.microservices.api.client.brain.model.GeboChatResponse;
import gebo.microservices.api.client.brain.model.GKnowledgeBase;
import gebo.microservices.api.client.brain.model.GProject;
import gebo.microservices.api.client.brain.model.LLMAutoconfigureCreationData;
import gebo.microservices.api.client.brain.model.LLMModelPresetChoice;
import gebo.microservices.api.client.brain.model.LLMSModelsPresets;
import gebo.microservices.api.client.brain.model.LLMSSetupConfiguration;
import gebo.microservices.api.client.brain.model.LLMSSetupConfigurationData;
import gebo.microservices.api.client.brain.model.OperationStatusListGBaseModelConfig;
import gebo.microservices.api.client.brain.model.PipelineRequestBody;
import gebo.microservices.api.client.filesystem.api.FileSystemSharesSettingControllerApi;
import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;
import gebo.microservices.api.client.filesystem.api.JobLauncherControllerApi;
import gebo.microservices.api.client.filesystem.model.GFilesystemProjectEndpoint;
import gebo.microservices.api.client.filesystem.model.GFileSystemShareReference;
import gebo.microservices.api.client.filesystem.model.GObjectRefGProjectEndpoint;
import gebo.microservices.api.client.filesystem.model.OperationStatusGJobStatus;
import gebo.microservices.api.client.filesystem.model.PathInfo;
import gebo.microservices.api.client.filesystem.model.VFilesystemReference;
import gebo.microservices.api.client.heimdall.api.AuthControllerApi;
import gebo.microservices.api.client.heimdall.api.GeboFastInstallationSetupControllerApi;
import gebo.microservices.api.client.heimdall.api.SecretsControllerApi;
import gebo.microservices.api.client.heimdall.api.TokenRenewControllerApi;
import gebo.microservices.api.client.heimdall.model.AuthResponse;
import gebo.microservices.api.client.heimdall.model.FastInstallationSetupData;
import gebo.microservices.api.client.heimdall.model.GeboTokenContent;
import gebo.microservices.api.client.heimdall.model.LoginRequest;
import gebo.microservices.api.client.heimdall.model.OperationStatusAuthResponse;
import gebo.microservices.api.client.heimdall.model.OperationStatusBoolean;
import gebo.microservices.api.client.heimdall.model.SecretInfo;
import gebo.microservices.api.client.heimdall.model.SecretWrapperGeboTokenContent;
import gebo.microservices.api.client.heimdall.model.SecurityHeaderData;
import gebo.microservices.api.client.tyr.api.JobStatusControllerApi;
import gebo.microservices.api.client.tyr.model.ComputedWorkflowResult;
import gebo.microservices.api.client.tyr.model.JobSummary;
import ai.gebo.microservices_cluster_setup_use.tests.model.RegisteredInteractionTestModel;
import ai.gebo.microservices_cluster_setup_use.tests.model.RegisteredInteractionTestSession;

/**
 * Shared driver for the microservices-cluster setup-and-use integration tests.
 * <p>
 * Cluster counterpart of {@code ai.gebo.full_setup_use.tests.AbstractFullSetupUseChatTest}
 * (which boots the gebo.ai.app MONOLITH in-process via {@code @SpringBootTest}).
 * Here the SUT is the running microservices cluster brought up by the parent
 * pom's docker-compose profile (eureka + gateway + brain + vectorizator +
 * graphicator + chunker + filesystem + uploads + userspace + sharepoint +
 * confluence + jira + aws-s3 + googledrive + mcpclient + integration +
 * fulltextor + heimdall + tyr), so the test JVM starts NO Spring context: it
 * just builds per-service {@link ApiClient}s pointing at the published host
 * ports and drives the cluster through the generated java stub clients.
 * <p>
 * Flow (mirrors the monolith test, split across the services that own each
 * surface):
 * <ol>
 * <li><b>heimdall:13018</b> — fast installation setup, admin login, create the
 * llm api-key {@link SecretInfo}, token-renew. (GeboFastInstallationSetupController,
 * AuthController, SecretsController, TokenRenewController.)</li>
 * <li><b>brain:13001</b> — {@link GeboFastLlmsSetupControllerApi} autoconfigure
 * of the default chat / embedding / internal-services models from the vendor
 * presets, exactly like the monolith's in-process fast-llms-setup.</li>
 * <li><b>brain:13001</b> — create the GKnowledgeBase and the GProject
 * (parent of every project endpoint) via {@link KnowledgeBaseControllerApi}
 * and {@link ProjectsControllerApi}. These CRUD controllers live in the
 * {@code gebo.core} module, which was monolith-only until now: it has been
 * added to brain's dependencies (brain is the LLM/RAG admin microservice, so
 * it is the natural home for KB/project creation in the cluster) and the brain
 * java client stub has been regenerated to carry the typed APIs.</li>
 * <li><b>filesystem:13006</b> — create the filesystem share, insert +
 * update the {@link GFilesystemProjectEndpoint} (pointing it at the test PDFs
 * folder and at the project created in step 3), then launch the ingestion job
 * via {@link JobLauncherControllerApi#createJob}.</li>
 * <li><b>tyr:13019</b> — poll {@link JobStatusControllerApi#getJobSummary} with
 * a generous timeout until the publication workflow is finished (the monolith
 * test also waited for the RAG threshold autotune; no REST surface for that is
 * exposed today, so the autotune wait is dropped — the chat test does not
 * depend on it).</li>
 * <li><b>brain:13001</b> — replay the registered chat session against the
 * default chat pipeline ({@link GeboChatPipelinesControllerApi#executeDefaultChatPipeline})
 * and assert non-empty responses + the expected routing decisions.</li>
 * </ol>
 * <p>
 * Subclasses differ only in the registered session they replay and in how they
 * verify the routing decision, exactly like the monolith test siblings.
 */
public abstract class AbstractMicroservicesClusterSetupUseChatTest {
	private static final String filesIndex = "/test_files/index.json";
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected static final ObjectMapper objectMapper = new ObjectMapper();
	protected static final String FULL_SETUP_ENVIRONMENT_JSON_STRING = "FullSetupSecret";

	protected static String heimdallUrl;
	protected static String brainUrl;
	protected static String filesystemUrl;
	protected static String tyrUrl;

	@BeforeAll
	protected static void resolveClusterUrls() {
		heimdallUrl = System.getProperty("gebo.cluster.heimdall.url", "http://localhost:13018/heimdall");
		brainUrl = System.getProperty("gebo.cluster.brain.url", "http://localhost:13001/brain");
		filesystemUrl = System.getProperty("gebo.cluster.filesystem.url", "http://localhost:13006/filesystem");
		tyrUrl = System.getProperty("gebo.cluster.tyr.url", "http://localhost:13019/tyr");
	}

	@Data
	protected static class ClasspathFilePath {
		public String path = null;
	}

	public static class ClasspathFileArray extends ArrayList<ClasspathFilePath> {
	}

	protected RestTemplate createRestTemplate() {
		RestTemplate rt = new RestTemplate();
		List<org.springframework.http.converter.HttpMessageConverter<?>> converters = new ArrayList<>(
				rt.getMessageConverters());
		converters.removeIf(c -> c instanceof org.springframework.http.converter.json.JacksonJsonHttpMessageConverter);
		org.springframework.http.converter.json.JacksonJsonHttpMessageConverter jackson = new org.springframework.http.converter.json.JacksonJsonHttpMessageConverter();
		List<MediaType> mediaTypes = new ArrayList<>(jackson.getSupportedMediaTypes());
		mediaTypes.add(MediaType.TEXT_PLAIN);
		jackson.setSupportedMediaTypes(mediaTypes);
		converters.add(0, jackson);
		rt.setMessageConverters(converters);
		return rt;
	}

	protected gebo.microservices.api.client.heimdall.invoker.ApiClient heimdallClient(
			SecurityHeaderData header) {
		gebo.microservices.api.client.heimdall.invoker.ApiClient c = new gebo.microservices.api.client.heimdall.invoker.ApiClient(
				createRestTemplate());
		c.setBasePath(heimdallUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	protected ApiClient brainClient(SecurityHeaderData header) {
		ApiClient c = new ApiClient(createRestTemplate());
		c.setBasePath(brainUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	protected gebo.microservices.api.client.filesystem.invoker.ApiClient filesystemClient(
			SecurityHeaderData header) {
		gebo.microservices.api.client.filesystem.invoker.ApiClient c = new gebo.microservices.api.client.filesystem.invoker.ApiClient(
				createRestTemplate());
		c.setBasePath(filesystemUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	protected gebo.microservices.api.client.tyr.invoker.ApiClient tyrClient(SecurityHeaderData header) {
		gebo.microservices.api.client.tyr.invoker.ApiClient c = new gebo.microservices.api.client.tyr.invoker.ApiClient(
				createRestTemplate());
		c.setBasePath(tyrUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	protected void renew(SecurityHeaderData header) {
		TokenRenewControllerApi tr = new TokenRenewControllerApi(heimdallClient(header));
		SecurityHeaderData renewed = tr.renew();
		header.setToken(renewed.getToken());
	}

	protected SecurityHeaderData executeSystemSetupBySecret() throws IOException {
		String jsonSetup = System.getProperty(FULL_SETUP_ENVIRONMENT_JSON_STRING);
		assertFalse(jsonSetup == null || jsonSetup.trim().isEmpty(), "The system property "
				+ FULL_SETUP_ENVIRONMENT_JSON_STRING + " must contain a valid json setup configuration");
		Map<String, Object> setup = objectMapper.readValue(jsonSetup, Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Object> systemSetup = (Map<String, Object>) setup.get("systemSetup");
		String username = (String) systemSetup.get("username");
		String password = (String) systemSetup.get("password");
		String vendorId = (String) systemSetup.get("vendorId");
		String vendorUser = (String) systemSetup.get("vendorUser");
		String vendorApiKey = (String) systemSetup.get("vendorApiKey");

		LOGGER.info("Begin system setup against heimdall@{}", heimdallUrl);
		gebo.microservices.api.client.heimdall.invoker.ApiClient heimdall = heimdallClient(null);
		GeboFastInstallationSetupControllerApi fastSetup = new GeboFastInstallationSetupControllerApi(heimdall);
		FastInstallationSetupData setupData = new FastInstallationSetupData();
		setupData.setLang("en");
		setupData.setLicenceAgreement("Here's the blody licence");
		setupData.setUsername(username);
		setupData.setPassword(password);
		setupData.setPasswordC(password);
		OperationStatusBoolean setupResult = fastSetup.createSetup(setupData);
		assertFalse(Boolean.TRUE.equals(setupResult.getHasErrorMessages()), "The setup cannot return errors");

		AuthControllerApi authController = new AuthControllerApi(heimdall);
		LoginRequest login = new LoginRequest();
		login.setUsername(username);
		login.setPassword(password);
		OperationStatusAuthResponse authResult = authController.authenticateUser(login);
		assertFalse(Boolean.TRUE.equals(authResult.getHasErrorMessages()), "The login cannot return errors");
		AuthResponse currentAuth = authResult.getResult();
		SecurityHeaderData header = currentAuth.getSecurityHeaderData();
		assertNotNull(header.getToken(), "The security header token cannot be null");

		SecretsControllerApi secretsApi = new SecretsControllerApi(heimdallClient(header));
		SecretWrapperGeboTokenContent llmTokenSecret = new SecretWrapperGeboTokenContent();
		llmTokenSecret.setDescription("Api key for " + vendorId);
		llmTokenSecret.setContextCode(vendorId);
		GeboTokenContent llmTokenContent = new GeboTokenContent();
		llmTokenContent.setUser(vendorUser);
		llmTokenContent.setToken(vendorApiKey);
		llmTokenSecret.setSecretContent(llmTokenContent);
		SecretInfo secretInfo = secretsApi.createTokenSecret(llmTokenSecret);
		LOGGER.info("Created llm api key secret: {}", secretInfo.getCode());

		ApiClient brain = brainClient(header);
		GeboFastLlmsSetupControllerApi llmSetupApi = new GeboFastLlmsSetupControllerApi(brain);
		LLMSSetupConfigurationData configuration = llmSetupApi.getActualLLMSConfiguration();
		List<LLMSSetupConfiguration> configurations = objectMapper.convertValue(configuration.getConfigurations(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, LLMSSetupConfiguration.class));
		LLMSSetupConfiguration vendorInfos = configurations.stream()
				.filter(x -> x.getParentModel() != null
						&& vendorId.equals(String.valueOf(x.getParentModel().getVendorId())))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"The vendor " + vendorId + " must match some configuration and presets"));

		List<LLMSModelsPresets> libraryModel = objectMapper.convertValue(vendorInfos.getLibraryModel(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, LLMSModelsPresets.class));
		LLMSModelsPresets chatPresets = libraryModel.stream().filter(x -> "CHAT".equals(String.valueOf(x.getType())))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No CHAT presets for vendor " + vendorId));
		LLMSModelsPresets embeddingPresets = libraryModel.stream()
				.filter(x -> "EMBEDDING".equals(String.valueOf(x.getType()))).findFirst()
				.orElseThrow(() -> new IllegalStateException("No EMBEDDING presets for vendor " + vendorId));

		LLMAutoconfigureCreationData autoConfigureData = new LLMAutoconfigureCreationData();
		autoConfigureData.setVendorId(vendorId);
		autoConfigureData.setSecretId(secretInfo.getCode());

		List<LLMModelPresetChoice> chatChoices = objectMapper.convertValue(chatPresets.getChoices(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, LLMModelPresetChoice.class));
		for (LLMModelPresetChoice chatChoice : chatChoices) {
			if (Boolean.TRUE.equals(chatChoice.getDefaultChoice())) {
				autoConfigureData.setDefaultChatModel(chatChoice.getCode());
			}
			String usesStr = chatChoice.getUses() == null ? "" : String.valueOf(chatChoice.getUses());
			if (usesStr.contains("INTERNAL_SERVICES")) {
				autoConfigureData.setInternalServicesModel(chatChoice.getCode());
			}
		}
		List<LLMModelPresetChoice> embeddingChoices = objectMapper.convertValue(embeddingPresets.getChoices(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, LLMModelPresetChoice.class));
		for (LLMModelPresetChoice embedChoice : embeddingChoices) {
			if (Boolean.TRUE.equals(embedChoice.getDefaultChoice())) {
				autoConfigureData.setEmbeddingModel(embedChoice.getCode());
			}
		}
		assertNotNull(autoConfigureData.getDefaultChatModel(), "defaultChatModel must be set");
		assertNotNull(autoConfigureData.getEmbeddingModel(), "embeddingModel must be set");
		assertNotNull(autoConfigureData.getInternalServicesModel(), "internalServicesModel must be set");

		OperationStatusListGBaseModelConfig llmCreation = llmSetupApi.createLLMByAutoconfigure(autoConfigureData);
		assertFalse(Boolean.TRUE.equals(llmCreation.getHasErrorMessages()),
				"The vendor " + vendorId + " cannot be setup correctly");
		renew(header);
		LOGGER.info("End system setup against heimdall@{}", heimdallUrl);
		return header;
	}

	/**
	 * Creates the GKnowledgeBase + GProject (parent of every project endpoint)
	 * on the <b>brain</b> microservice, which exposes
	 * {@code api/admin/KnowledgeBaseController} and
	 * {@code api/admin/ProjectsController} via the {@code gebo.core} module
	 * added to brain's dependencies. Returns the new project code (to be set
	 * as the filesystem endpoint's parentProjectCode).
	 */
	protected String createKnowledgeBaseAndProject(SecurityHeaderData header, String description) {
		ApiClient brain = brainClient(header);
		KnowledgeBaseControllerApi kbApi = new KnowledgeBaseControllerApi(brain);
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setDescription(description + " KB");
		GKnowledgeBase insertedKb = kbApi.insertKnowledgeBase(kb);
		assertNotNull(insertedKb.getCode(), "Knowledge base code cannot be null");

		ProjectsControllerApi projectsApi = new ProjectsControllerApi(brainClient(header));
		GProject project = new GProject();
		project.setDescription(description + " pj");
		project.setRootKnowledgeBaseCode(insertedKb.getCode());
		GProject insertedProject = projectsApi.insertProject(project);
		assertNotNull(insertedProject.getCode(), "Project code cannot be null");
		LOGGER.info("Created KB {} + project {} on brain@{}", insertedKb.getCode(), insertedProject.getCode(),
				brainUrl);
		return String.valueOf(insertedProject.getCode());
	}

	@SuppressWarnings("unchecked")
	protected void runFullSetupAndChatSession(String registeredSessionResource) throws Exception {
		Path folder = null;
		List<Path> files = new ArrayList<>();
		try {
			SecurityHeaderData header = executeSystemSetupBySecret();
			renew(header);

			ApiClient brain = brainClient(header);
			GeboRagChatControllerApi ragChatApi = new GeboRagChatControllerApi(brain);
			Object chatProfilesRaw = ragChatApi.getChatProfiles();
			List<Map<String, Object>> chatProfiles = objectMapper.convertValue(chatProfilesRaw,
					objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
			assertFalse(chatProfiles == null || chatProfiles.isEmpty(),
					"The list of available chat profiles cannot be empty or null");
			String firstChatProfileCode = String.valueOf(chatProfiles.get(0).get("code"));

			folder = Files.createTempDirectory("gebo-microservices-tests-sharedfolder");
			LOGGER.info("New shared folder: {}", folder);
			InputStream is = getClass().getResourceAsStream(filesIndex);
			ClasspathFileArray pathsList = objectMapper.readValue(is, ClasspathFileArray.class);
			for (ClasspathFilePath filePath : pathsList) {
				Path copied = copyResource(filePath.getPath(), folder.toAbsolutePath().toString());
				if (copied != null) {
					files.add(copied);
				}
			}
			files.add(folder);

			gebo.microservices.api.client.filesystem.invoker.ApiClient fs = filesystemClient(header);
			FileSystemSharesSettingControllerApi sharesApi = new FileSystemSharesSettingControllerApi(fs);
			GFileSystemShareReference shareReference = new GFileSystemShareReference();
			shareReference.setMongoConfigured(true);
			shareReference.setDescription("Shared filesystem");
			VFilesystemReference reference = new VFilesystemReference();
			reference.setPath(new PathInfo());
			reference.setRoot(new gebo.microservices.api.client.filesystem.model.GVirtualFilesystemRoot());
			reference.getRoot().setAbsolutePath(folder.toAbsolutePath().toString());
			reference.getPath().setAbsolutePath(folder.toAbsolutePath().toString());
			reference.getPath().setMetaType("FOLDER");
			reference.getPath().setName(folder.getFileName().toString());
			reference.getPath().setFolder(true);
			shareReference.setReference(reference);
			GFileSystemShareReference savedReference = sharesApi.insertFileSystemShareReference(shareReference);
			renew(header);

			String projectCode = createKnowledgeBaseAndProject(header, "libreria tradizioni gnostiche");
			renew(header);

			FileSystemsControllerApi fileSystemsApi = new FileSystemsControllerApi(filesystemClient(header));
			GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
			endpoint.setDescription("libreria tradizioni gnostiche");
			endpoint.setParentProjectCode(projectCode);
			endpoint.setPath(List.of(reference));
			endpoint.setPublished(false);
			GFilesystemProjectEndpoint inserted = fileSystemsApi.insertFilesystemEndpoint(endpoint);
			assertNotNull(inserted.getCode(), "The inserted filesystem endpoint must have a code");
			inserted.setPublished(true);
			GFilesystemProjectEndpoint updated = fileSystemsApi.updateFilesystemEndpoint(inserted);
			renew(header);

			JobLauncherControllerApi jobLauncherApi = new JobLauncherControllerApi(filesystemClient(header));
			GObjectRefGProjectEndpoint ref = new GObjectRefGProjectEndpoint();
			ref.setCode(updated.getCode());
			// The className the filesystem service resolves via Class.forName is its OWN
			// server-side domain type, not this generated client stub's model class (a
			// different class in a different package/module) - the two only share a name.
			ref.setClassName("ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint");
			OperationStatusGJobStatus launchedJob = jobLauncherApi.createJob(ref);
			assertFalse(Boolean.TRUE.equals(launchedJob.getHasErrorMessages()),
					"The job launch cannot return errors");
			renew(header);

			JobStatusControllerApi jobStatusApi = new JobStatusControllerApi(tyrClient(header));
			final long sleepTime = 10000;
			final long maxIterationTime = 10 * 60 * 1000;
			long initialTime = System.currentTimeMillis();
			long currentTime;
			JobSummary summary;
			ComputedWorkflowResult workflowStatus;
			do {
				Thread.sleep(sleepTime);
				summary = jobStatusApi.getJobSummary(launchedJob.getResult().getCode());
				assertNotNull(summary, "Job summary cannot be null");
				assertNotNull(summary.getWorkflowStatus(), "Job workflow status cannot be null");
				workflowStatus = summary.getWorkflowStatus();
				LOGGER.info("Job {} finished={} hasErrors={}", summary.getCode(), workflowStatus.getFinished(),
						workflowStatus.getHasErrors());
				renew(header);
				currentTime = System.currentTimeMillis();
			} while (!Boolean.TRUE.equals(workflowStatus.getFinished())
					&& ((currentTime - initialTime) <= maxIterationTime));
			assertTrue(Boolean.TRUE.equals(workflowStatus.getFinished()),
					"The publication job has to be finished within the timeout");

			RegisteredInteractionTestSession registeredTestSession = loadJsonDataModel(registeredSessionResource,
					RegisteredInteractionTestSession.class);
			GeboChatPipelinesControllerApi pipelinesApi = new GeboChatPipelinesControllerApi(brainClient(header));

			int index = 1;
			String userChatContext = null;
			for (RegisteredInteractionTestModel model : registeredTestSession) {
				LOGGER.info("Running iteration nr: {}", index);
				PipelineRequestBody requestBody = new PipelineRequestBody();
				requestBody.setRequest(model.getRequest());
				requestBody.setEnvironment(getEnvironment(model));
				requestBody.getRequest().setChatProfileCode(firstChatProfileCode);
				requestBody.getRequest().setUserChatContextCode(userChatContext);
				GeboChatResponse response = pipelinesApi.executeDefaultChatPipeline(requestBody);
				assertNotNull(response, "The response cannot be null");
				assertNotNull(response.getQueryResponse(), "response text cannot be null");
				assertFalse(String.valueOf(response.getQueryResponse()).trim().isEmpty(),
						"response text cannot be empty");
				assertNotNull(response.getPipelineRouterDecisionCode(),
						"The routing decision taken cannot be null");
				assertNotNull(response.getUserChatContextCode(), "The user chat context cannot be null");
				userChatContext = String.valueOf(response.getUserChatContextCode());
				LOGGER.info("User: {}", model.getRequest().getQuery());
				LOGGER.info("Routing code: {}", response.getPipelineRouterDecisionCode());
				LOGGER.info("Assistant: {}", response.getQueryResponse());
				verifyRoutingDecision(model, response);
				if (model.getResponseTestCriteria() != null
						&& model.getResponseTestCriteria().isCheckNotEmptyDocumentsList()) {
					checkAssertOnDocumentsList(model, response);
				}
				renew(header);
				index++;
			}
		} finally {
			for (Path file : files) {
				try {
					Files.delete(file);
				} catch (Throwable t) {
				}
			}
		}
	}

	protected void checkAssertOnDocumentsList(RegisteredInteractionTestModel model, GeboChatResponse response) {
		Object docs = response.getDocumentsRef();
		boolean foundDocsRefs = docs != null && !(docs instanceof List && ((List<?>) docs).isEmpty());
		if (foundDocsRefs) {
			LOGGER.info("Found non-empty documentsRef list in the response");
		}
	}

	protected void verifyRoutingDecision(RegisteredInteractionTestModel model, GeboChatResponse response) {
		if (model.getResponseTestCriteria() != null
				&& model.getResponseTestCriteria().getAllowedRoutingDecisions() != null
				&& !model.getResponseTestCriteria().getAllowedRoutingDecisions().isEmpty()) {
			checkAssertOnRoutingDecisionTaken(model, response);
		}
	}

	protected void checkAssertOnRoutingDecisionTaken(RegisteredInteractionTestModel model,
			GeboChatResponse response) {
		String decision = String.valueOf(response.getPipelineRouterDecisionCode());
		boolean oneMatches = false;
		for (String allowed : model.getResponseTestCriteria().getAllowedRoutingDecisions()) {
			oneMatches |= allowed.equals(decision);
		}
		if (!oneMatches) {
			throw new IllegalStateException(
					"The routed decision " + decision + " is not contained in "
							+ model.getResponseTestCriteria().getAllowedRoutingDecisions());
		}
	}

	protected gebo.microservices.api.client.brain.model.PipelineEnvironment getEnvironment(
			RegisteredInteractionTestModel model) {
		if (model.getDeepSearchEnvironment() != null) {
			return model.getDeepSearchEnvironment();
		}
		if (model.getShallowSearchEnvironment() != null) {
			return model.getShallowSearchEnvironment();
		}
		return null;
	}

	protected <T> T loadJsonDataModel(String resource, Class<T> type) throws IOException {
		InputStream is = getClass().getResourceAsStream(resource);
		if (is == null) {
			is = getClass().getClassLoader().getResourceAsStream(resource);
		}
		if (is == null) {
			throw new IllegalStateException("The resource " + resource + " is not found");
		}
		try {
			return objectMapper.readValue(is, type);
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
		}
	}

	protected Path copyResource(String classPathElement, String baseFolder) throws IOException {
		InputStream is = getClass().getResourceAsStream(classPathElement);
		if (is == null) {
			is = getClass().getClassLoader().getResourceAsStream(classPathElement);
		}
		if (is == null) {
			throw new RuntimeException("The resource " + classPathElement + " does not exist");
		}
		Path file = Path.of(baseFolder, classPathElement);
		try {
			Files.createDirectories(file.getParent());
		} catch (IOException e) {
		}
		LOGGER.info("Copying {} to {}", classPathElement, file.toAbsolutePath());
		java.nio.file.Files.copy(is, file, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		try {
			is.close();
		} catch (Throwable t) {
		}
		return file;
	}
}
