package ai.gebo.microservices_architecture_tests.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import tools.jackson.databind.ObjectMapper;

import gebo.microservices.api.client.brain.api.GeboFastLlmsSetupControllerApi;
import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;
import gebo.microservices.api.client.brain.api.ProjectsControllerApi;
import gebo.microservices.api.client.brain.invoker.ApiClient;
import gebo.microservices.api.client.brain.model.GKnowledgeBase;
import gebo.microservices.api.client.brain.model.GProject;
import gebo.microservices.api.client.brain.model.LLMAutoconfigureCreationData;
import gebo.microservices.api.client.brain.model.LLMModelPresetChoice;
import gebo.microservices.api.client.brain.model.LLMSModelsPresets;
import gebo.microservices.api.client.brain.model.LLMSSetupConfiguration;
import gebo.microservices.api.client.brain.model.LLMSSetupConfigurationData;
import gebo.microservices.api.client.brain.model.OperationStatusListGBaseModelConfig;
import gebo.microservices.api.client.filesystem.api.FileSystemSharesSettingControllerApi;
import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;
import gebo.microservices.api.client.filesystem.model.GFileSystemShareReference;
import gebo.microservices.api.client.filesystem.model.GFilesystemProjectEndpoint;
import gebo.microservices.api.client.filesystem.model.GVirtualFilesystemRoot;
import gebo.microservices.api.client.filesystem.model.PathInfo;
import gebo.microservices.api.client.filesystem.model.ReindexingProgrammedTable;
import gebo.microservices.api.client.filesystem.model.ReindexingTime;
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
import gebo.microservices.api.client.tyr.model.DataPage;
import gebo.microservices.api.client.tyr.model.JobSummary;
import gebo.microservices.api.client.tyr.model.JobsEntriesForProjectEndpointFilter;
import gebo.microservices.api.client.tyr.model.PageGJobStatusItem;

/**
 * Testcontainers-managed architectural test: a deliberately narrow 5-service
 * slice of the microservices cluster - heimdall (authn/secrets), brain (core
 * orchestration/KB), filesystem (a content handler), tyr (central scheduler +
 * job tracking), vectorizator (the embedding pipeline) - brought up and torn
 * down entirely by this JUnit test via {@link ComposeContainer}, wrapping the
 * SAME {@code dockers/gebo.microservices/docker-compose.yml} the rest of the
 * cluster uses.
 *
 * <p>
 * Unlike {@code setup-use-microservices-tests} (external, operator-managed
 * lifecycle), this test owns its own containers: JUnit ties start/stop to the
 * test lifecycle, so teardown happens automatically even on failure. See
 * {@code pom.xml} for why this module opts out of the parent's
 * {@code manage-cluster} profile (that one brings up the FULL ~20-service
 * stack, which would be redundant here).
 * </p>
 *
 * <p>
 * <b>Precondition</b>: this test does not build Docker images. The 5 service
 * images (+ infra) must already be built with {@code -P docker,swagger-on}
 * and {@code docker load}-ed before running.
 * </p>
 *
 * <p>
 * <b>Scenario</b>: heimdall system-setup/auth (reusing the exact same
 * {@code FullSetupSecret} system-property convention as
 * {@code setup-use-microservices-tests}) &rarr; brain creates a KB + project
 * &rarr; filesystem creates an endpoint and schedules a near-term one-shot run
 * &rarr; tyr's central scheduler dispatches it &rarr; filesystem launches the
 * job &rarr; vectorizator actually processes the embedding step, observed via
 * tyr's {@code getJobsEntriesForProjectEndpoint} (find the dispatched job)
 * then {@code getJobSummary} (wait for the aggregated workflow - including
 * embedding - to finish). This single flow exercises all 5 services for a
 * real reason each, not just a liveness ping.
 * </p>
 */
@Testcontainers
public class ArchitecturalComposeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArchitecturalComposeTest.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final String FULL_SETUP_ENVIRONMENT_JSON_STRING = "FullSetupSecret";

	/**
	 * Matches {@code dockers/gebo.microservices/.env}'s GEBO_VERSION - overridable
	 * with {@code -Dgebo.image.version=...} if a different tag was built/loaded.
	 */
	private static final String GEBO_IMAGE_VERSION = System.getProperty("gebo.image.version", "1.0.2.2-SNAPSHOT");

	private static final File COMPOSE_FILE = resolveComposeFile();
	private static final File DEBUG_LOGGING_OVERRIDE_FILE = resolveDebugLoggingOverrideFile();

	private static File resolveComposeFile() {
		// user.dir is this module's own directory during `mvn test`; the canonical
		// compose file lives at the repo root's dockers/gebo.microservices/.
		File f = new File(System.getProperty("user.dir"), "../../dockers/gebo.microservices/docker-compose.yml")
				.getAbsoluteFile();
		if (!f.isFile()) {
			throw new IllegalStateException("Cannot find the microservices docker-compose.yml at " + f);
		}
		return f;
	}

	private static File resolveDebugLoggingOverrideFile() {
		File f = new File(System.getProperty("user.dir"),
				"src/test/resources/docker-compose.debug-logging.override.yml").getAbsoluteFile();
		if (!f.isFile()) {
			throw new IllegalStateException("Cannot find the debug-logging override compose file at " + f);
		}
		return f;
	}

	@Container
	// ComposeContainer#withEnv(...) only substitutes ${VARIABLE} references
	// INSIDE the compose file text (e.g. GEBO_VERSION below) - it does NOT
	// inject arbitrary environment variables into each container's own runtime
	// environment. Container-runtime env vars (the DEBUG logging levels, the
	// models-replication-participants override) live in
	// DEBUG_LOGGING_OVERRIDE_FILE instead, layered on top of the base compose
	// file via this multi-file constructor - kept as a separate file rather
	// than edited into the shared base compose file so its default behaviour
	// (used by setup-use-microservices-tests, manual bring-up, CI, ...) stays
	// unaffected.
	static ComposeContainer compose = new ComposeContainer(List.of(COMPOSE_FILE, DEBUG_LOGGING_OVERRIDE_FILE))
			.withServices("eureka", "rabbit", "mongo", "qdrant", "heimdall", "brain", "vectorizator", "tyr",
					"filesystem")
			.withEnv("GEBO_VERSION", GEBO_IMAGE_VERSION)
			.withExposedService("heimdall", 13018,
					Wait.forHttp("/heimdall/v3/api-docs").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(3)))
			.withExposedService("tyr", 13019,
					Wait.forHttp("/tyr/v3/api-docs").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(3)))
			.withExposedService("filesystem", 13006,
					Wait.forHttp("/filesystem/v3/api-docs").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(3)))
			.withExposedService("brain", 13001,
					Wait.forHttp("/brain/v3/api-docs").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(6)))
			.withExposedService("vectorizator", 13002,
					Wait.forHttp("/vectorizator/v3/api-docs").forStatusCode(200)
							.withStartupTimeout(Duration.ofMinutes(6)))
			.withLogConsumer("heimdall", new Slf4jLogConsumer(LOGGER).withPrefix("heimdall"))
			.withLogConsumer("brain", new Slf4jLogConsumer(LOGGER).withPrefix("brain"))
			.withLogConsumer("filesystem", new Slf4jLogConsumer(LOGGER).withPrefix("filesystem"))
			.withLogConsumer("tyr", new Slf4jLogConsumer(LOGGER).withPrefix("tyr"))
			.withLogConsumer("vectorizator", new Slf4jLogConsumer(LOGGER).withPrefix("vectorizator"));

	private static String heimdallUrl;
	private static String brainUrl;
	private static String filesystemUrl;
	private static String tyrUrl;

	@BeforeAll
	static void resolveClusterUrls() {
		heimdallUrl = "http://" + compose.getServiceHost("heimdall", 13018) + ":"
				+ compose.getServicePort("heimdall", 13018) + "/heimdall";
		brainUrl = "http://" + compose.getServiceHost("brain", 13001) + ":" + compose.getServicePort("brain", 13001)
				+ "/brain";
		filesystemUrl = "http://" + compose.getServiceHost("filesystem", 13006) + ":"
				+ compose.getServicePort("filesystem", 13006) + "/filesystem";
		tyrUrl = "http://" + compose.getServiceHost("tyr", 13019) + ":" + compose.getServicePort("tyr", 13019)
				+ "/tyr";
		LOGGER.info("Resolved cluster URLs: heimdall={} brain={} filesystem={} tyr={}", heimdallUrl, brainUrl,
				filesystemUrl, tyrUrl);

		// The wait strategies above only prove each container's own /v3/api-docs
		// responds - not that heimdall's Eureka CLIENT-side registry cache has
		// converged to recognize the other services as legitimate cluster
		// participants yet (ClusterParticipantsGuard fails closed until it does).
		// Reproduced on two consecutive cold starts of this exact 9-container
		// subset: brain's very first authenticated call (inside
		// executeSystemSetupBySecret) landed on heimdall before that convergence
		// finished, getting a 403 "not a registered cluster participant" that
		// surfaces as a 500 one level up. A heavier bring-up (e.g. the full
		// ~20-service stack used elsewhere in this repo) has enough incidental
		// startup contention to avoid this in practice; this leaner, faster subset
		// does not. Settling here - after every container already answers
		// /v3/api-docs, before any authenticated call - is more deterministic than
		// retrying the call after the fact.
		try {
			Thread.sleep(Duration.ofSeconds(35).toMillis());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@AfterAll
	static void logTeardown() {
		LOGGER.info("Test finished - ComposeContainer will now tear the 5-service subset down automatically");
	}

	private RestTemplate createRestTemplate() {
		RestTemplate rt = new RestTemplate();
		List<org.springframework.http.converter.HttpMessageConverter<?>> converters = new ArrayList<>(
				rt.getMessageConverters());
		converters.removeIf(c -> c instanceof JacksonJsonHttpMessageConverter);
		JacksonJsonHttpMessageConverter jackson = new JacksonJsonHttpMessageConverter();
		List<MediaType> mediaTypes = new ArrayList<>(jackson.getSupportedMediaTypes());
		mediaTypes.add(MediaType.TEXT_PLAIN);
		jackson.setSupportedMediaTypes(mediaTypes);
		converters.add(0, jackson);
		rt.setMessageConverters(converters);
		return rt;
	}

	private gebo.microservices.api.client.heimdall.invoker.ApiClient heimdallClient(SecurityHeaderData header) {
		gebo.microservices.api.client.heimdall.invoker.ApiClient c = new gebo.microservices.api.client.heimdall.invoker.ApiClient(
				createRestTemplate());
		c.setBasePath(heimdallUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	private ApiClient brainClient(SecurityHeaderData header) {
		ApiClient c = new ApiClient(createRestTemplate());
		c.setBasePath(brainUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	private gebo.microservices.api.client.filesystem.invoker.ApiClient filesystemClient(SecurityHeaderData header) {
		gebo.microservices.api.client.filesystem.invoker.ApiClient c = new gebo.microservices.api.client.filesystem.invoker.ApiClient(
				createRestTemplate());
		c.setBasePath(filesystemUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	private gebo.microservices.api.client.tyr.invoker.ApiClient tyrClient(SecurityHeaderData header) {
		gebo.microservices.api.client.tyr.invoker.ApiClient c = new gebo.microservices.api.client.tyr.invoker.ApiClient(
				createRestTemplate());
		c.setBasePath(tyrUrl);
		if (header != null && header.getToken() != null) {
			c.setApiKey(String.valueOf(header.getToken()));
		}
		return c;
	}

	private void renew(SecurityHeaderData header) {
		TokenRenewControllerApi tr = new TokenRenewControllerApi(heimdallClient(header));
		SecurityHeaderData renewed = tr.renew();
		header.setToken(renewed.getToken());
	}

	/**
	 * Reused, unmodified in substance, from
	 * {@code AbstractMicroservicesClusterSetupUseChatTest.executeSystemSetupBySecret}:
	 * heimdall fast-install + admin login + vendor API-key secret, then brain's
	 * LLM autoconfigure from the vendor presets - real chat AND embedding models,
	 * the latter being what vectorizator needs to actually process the job below.
	 */
	private SecurityHeaderData executeSystemSetupBySecret() throws IOException {
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

	private String createKnowledgeBaseAndProject(SecurityHeaderData header, String description) {
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

	@Test
	void schedulerDispatchesAndVectorizatorFinishesTheEmbedding() throws Exception {
		Path folder = null;
		try {
			SecurityHeaderData header = executeSystemSetupBySecret();
			renew(header);

			folder = Files.createTempDirectory("gebo-architecture-testcontainers-tests");
			LOGGER.info("New shared folder: {}", folder);

			gebo.microservices.api.client.filesystem.invoker.ApiClient fs = filesystemClient(header);
			FileSystemSharesSettingControllerApi sharesApi = new FileSystemSharesSettingControllerApi(fs);
			GFileSystemShareReference shareReference = new GFileSystemShareReference();
			shareReference.setMongoConfigured(true);
			shareReference.setDescription("Architectural test shared filesystem");
			VFilesystemReference reference = new VFilesystemReference();
			reference.setPath(new PathInfo());
			reference.setRoot(new GVirtualFilesystemRoot());
			reference.getRoot().setAbsolutePath(folder.toAbsolutePath().toString());
			reference.getPath().setAbsolutePath(folder.toAbsolutePath().toString());
			reference.getPath().setMetaType("FOLDER");
			reference.getPath().setName(folder.getFileName().toString());
			reference.getPath().setFolder(true);
			shareReference.setReference(reference);
			sharesApi.insertFileSystemShareReference(shareReference);
			renew(header);

			String projectCode = createKnowledgeBaseAndProject(header, "architectural test project");
			renew(header);

			FileSystemsControllerApi fileSystemsApi = new FileSystemsControllerApi(filesystemClient(header));
			GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
			endpoint.setDescription("architectural test endpoint");
			endpoint.setParentProjectCode(projectCode);
			endpoint.setPath(List.of(reference));
			endpoint.setPublished(false);
			GFilesystemProjectEndpoint inserted = fileSystemsApi.insertFilesystemEndpoint(endpoint);
			assertNotNull(inserted.getCode(), "The inserted filesystem endpoint must have a code");
			renew(header);

			long scheduledAt = System.currentTimeMillis() + Duration.ofMinutes(2).toMillis();
			ReindexingTime time = new ReindexingTime();
			time.setCreatedTime(System.currentTimeMillis());
			time.setTimeComponent(List.of(scheduledAt));
			ReindexingProgrammedTable table = new ReindexingProgrammedTable();
			table.setFrequency("DATES");
			table.setTimes(List.of(time));
			inserted.setProgrammedTables(List.of(table));
			inserted.setSynchPeriodically(true);
			inserted.setPublished(true);

			// Goes through the controller (not a direct persistence call), so the
			// server-side GAbstractSystemsArchitectureController actually sends the
			// reschedule request to tyr's central scheduler.
			GFilesystemProjectEndpoint updated = fileSystemsApi.updateFilesystemEndpoint(inserted);
			renew(header);
			LOGGER.info("Endpoint {} scheduled for {} - waiting for tyr's central scheduler to dispatch it",
					updated.getCode(), scheduledAt);

			gebo.microservices.api.client.tyr.model.GObjectRefGProjectEndpoint ref = new gebo.microservices.api.client.tyr.model.GObjectRefGProjectEndpoint();
			ref.setCode(updated.getCode());
			ref.setClassName("ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint");

			// The run is ~2 minutes out and the scheduler ticks every 60s.
			Thread.sleep(Duration.ofSeconds(90).toMillis());

			String launchedJobCode = null;
			int nCycles = 0;
			int NMAXCYCLES = 10;
			do {
				JobsEntriesForProjectEndpointFilter filter = new JobsEntriesForProjectEndpointFilter();
				filter.setEndpointRef(ref);
				filter.setJobType("CONTENTS_READING_VECTORIZING");
				DataPage page = new DataPage();
				page.setPage(0);
				page.setPageSize(10);
				page.setSort(List.of());
				filter.setPage(page);
				JobStatusControllerApi jobStatusApi = new JobStatusControllerApi(tyrClient(header));
				PageGJobStatusItem result = jobStatusApi.getJobsEntriesForProjectEndpoint(filter);
				List<Map<String, Object>> content = objectMapper.convertValue(result.getContent(),
						objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
				LOGGER.info("On cycle=>{} jobs found for endpoint so far: {}", nCycles,
						content == null ? 0 : content.size());
				if (content != null && !content.isEmpty()) {
					launchedJobCode = String.valueOf(content.get(0).get("code"));
					break;
				}
				Thread.sleep(Duration.ofSeconds(20).toMillis());
				renew(header);
				nCycles++;
			} while (nCycles < NMAXCYCLES);

			assertNotNull(launchedJobCode,
					"tyr's central scheduler must have dispatched a publish for the scheduled endpoint by now");

			// The job exists - now wait for the AGGREGATED workflow (all steps,
			// including vectorizator's embedding step) to actually finish. This is
			// the same aggregate used by AbstractMicroservicesClusterSetupUseChatTest,
			// proven this session to correctly reflect a stalled embedding step.
			final long sleepTime = 10000;
			final long maxIterationTime = 6 * 60 * 1000;
			long initialTime = System.currentTimeMillis();
			long currentTime;
			JobSummary summary;
			ComputedWorkflowResult workflowStatus;
			do {
				Thread.sleep(sleepTime);
				JobStatusControllerApi jobStatusApi = new JobStatusControllerApi(tyrClient(header));
				summary = jobStatusApi.getJobSummary(launchedJobCode);
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
					"The publication job (including vectorizator's embedding step) has to finish within the timeout");
			assertFalse(Boolean.TRUE.equals(workflowStatus.getHasErrors()),
					"The publication job must not have finished with errors");
		} finally {
			if (folder != null) {
				try {
					Files.delete(folder);
				} catch (Throwable t) {
				}
			}
		}
	}
}
