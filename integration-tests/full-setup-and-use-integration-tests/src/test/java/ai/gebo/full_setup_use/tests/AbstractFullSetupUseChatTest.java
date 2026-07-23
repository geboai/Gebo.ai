package ai.gebo.full_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag_threasholds_autotune.repository.ThreasholdAutotuneProcessResultRepository;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.full_setup_use.tests.model.RegisteredInteractionTestModel;
import ai.gebo.full_setup_use.tests.model.RegisteredInteractionTestSession;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;
import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;
import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;
import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.GFileSystemShareReference;
import ai.gebo.monolithic.api.client.model.GObjectRefGProjectEndpoint;
import ai.gebo.monolithic.api.client.model.GResponseDocumentRef;
import ai.gebo.monolithic.api.client.model.GVirtualFilesystemRoot;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.JobSummary;
import ai.gebo.monolithic.api.client.model.OperationStatusGJobStatus;
import ai.gebo.monolithic.api.client.model.PathInfo;
import ai.gebo.monolithic.api.client.model.PathInfo.MetaTypeEnum;
import ai.gebo.monolithic.api.client.model.PipelineEnvironment;
import ai.gebo.monolithic.api.client.model.PipelineRequestBody;
import ai.gebo.monolithic.api.client.model.VFilesystemReference;
import lombok.Data;

/**
 * Shared driver for the full setup-and-use integration tests: it performs the
 * complete admin/LLM setup, creates a filesystem-backed knowledge base, launches
 * and waits for the publication job, waits for the RAG threshold autotune, then
 * replays a registered chat session against the default chat pipeline.
 * <p>
 * Subclasses differ only in whether the default network of agents is enabled
 * (via {@code ai.gebo.agents.standard.enabled}) and in how they verify the
 * routing decision taken for each interaction (see
 * {@link #verifyRoutingDecision(RegisteredInteractionTestModel, GeboChatResponse)}):
 * <ul>
 * <li>{@link FullSetupUseAndPipelineTest} runs with the network of agents
 * <em>disabled</em>, exercising the default pipeline router and its workers
 * (RAG / deep-search / tools / pure-llm);</li>
 * <li>{@link FullSetupUseAndAgenticChatTest} runs with the default network of
 * agents <em>enabled</em>, so every request is delegated to the agents
 * network.</li>
 * </ul>
 */
public abstract class AbstractFullSetupUseChatTest extends AbstractVendorSetupAndUseTest {
	private static final String filesIndex = "/test_files/index.json";
	@Autowired
	IGRuntimeBinder runtimeBinder;

	@Data
	public static class ClasspathFilePath {
		String path = null;
	}

	public static class ClasspathFileArray extends ArrayList<ClasspathFilePath> {
	};

	/**
	 * Runs the full setup, builds the knowledge base, waits for publication and
	 * autotune, then replays the given registered chat session against the default
	 * chat pipeline.
	 *
	 * @param registeredSessionResource classpath resource of the
	 *                                   {@link RegisteredInteractionTestSession} to
	 *                                   replay
	 */
	protected void runFullSetupAndChatSession(String registeredSessionResource) throws InterruptedException, IOException,
			InstantiationException, IllegalAccessException, GeboPersistenceException, LLMConfigException {
		Path folder = null;
		List<Path> files = new ArrayList<Path>();
		try {
			TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
			ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
					systemInfo.getSecurityHeader());
			Thread.currentThread().sleep(30000);
			renew(apiClient);
			GeboRagChatControllerApi chatProfilesLookupControllerApi = new GeboRagChatControllerApi(apiClient);

			List<ai.gebo.monolithic.api.client.model.GChatProfileConfiguration> chatProfiles = chatProfilesLookupControllerApi
					.getChatProfiles();

			assertFalse(chatProfiles == null || chatProfiles.isEmpty(),
					"The list of available chat profiles cannot be empty or null");
			folder = Files.createTempDirectory("gebo-tests-sharedfolder");
			LOGGER.info("New shared folder:" + folder);
			InputStream is = getClass().getResourceAsStream(filesIndex);
			ClasspathFileArray pathsList = super.mapper.readValue(is, ClasspathFileArray.class);
			for (ClasspathFilePath filePath : pathsList) {
				Path _filePath = super.copyResource(filePath.getPath(), folder.toAbsolutePath().toString());
				if (_filePath != null)
					files.add(_filePath);
			}
			files.add(folder);
			FileSystemSharesSettingControllerApi sharesSettingsApi = new FileSystemSharesSettingControllerApi(
					apiClient);
			GFileSystemShareReference shareReference = new GFileSystemShareReference();
			shareReference.setMongoConfigured(true);
			shareReference.setDescription("Shared filesystem");
			VFilesystemReference reference = new VFilesystemReference();
			ai.gebo.model.virtualfs.VFilesystemReference folderReference = ai.gebo.model.virtualfs.VFilesystemReference
					.from(folder);
			reference.setPath(new PathInfo());
			reference.setRoot(new GVirtualFilesystemRoot());
			reference.getRoot().setAbsolutePath(folderReference.root.getAbsolutePath());
			reference.getRoot().setCode(folderReference.root.getCode());
			reference.getPath().setAbsolutePath(folderReference.path.absolutePath);
			reference.getPath().setMetaType(MetaTypeEnum.FOLDER);
			reference.getPath().setName(folderReference.path.name);
			reference.getPath().setFolder(true);
			shareReference.setReference(reference);
			GFileSystemShareReference savedReference = sharesSettingsApi.insertFileSystemShareReference(shareReference);
			renew(apiClient);
			ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint endpoint = super.createAndPersist(
					"libreria tradizioni gnostiche",
					ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint.class);
			endpoint.setPath(List.of(folderReference));
			endpoint.setPublished(true);
			endpoint = persistentObjectManager.update(endpoint);
			renew(apiClient);
			JobLauncherControllerApi jobLauncherApi = new JobLauncherControllerApi(apiClient);
			GObjectRefGProjectEndpoint ref = new GObjectRefGProjectEndpoint();
			ref.setCode(endpoint.getCode());
			ref.setClassName(endpoint.getClass().getName());
			OperationStatusGJobStatus launchedJob = jobLauncherApi.createJob(ref);
			assertFalse(launchedJob.isHasErrorMessages(), "The error messages cannot appear");
			printMessages(launchedJob.getMessages());
			renew(apiClient);
			JobSummary summary = null;
			final long sleepTime = 10000;
			final long maxIterationTime = 10 * 60 * 1000;
			final long initialTime = System.currentTimeMillis();
			long currentTime = System.currentTimeMillis();
			do {
				Thread.currentThread().sleep(sleepTime);
				summary = jobLauncherApi.getJobSummary(launchedJob.getResult().getCode());
				printSummary(summary);
				renew(apiClient);
				currentTime = System.currentTimeMillis();
			} while (!summary.getWorkflowStatus().isFinished() && ((currentTime - initialTime) <= maxIterationTime));
			summary = jobLauncherApi.getJobSummary(launchedJob.getResult().getCode());
			assertTrue(summary.getWorkflowStatus().isFinished(), "The pubblication job has to be finished");
			ThreasholdAutotuneProcessResultRepository autotuneRepository = runtimeBinder
					.getImplementationOf(ThreasholdAutotuneProcessResultRepository.class);
			IRagThreasholdAutotuneService autotuneService = runtimeBinder
					.getImplementationOf(IRagThreasholdAutotuneService.class);
			Thread.currentThread().sleep(sleepTime);
			boolean whasAutotuneRunning = false;
			boolean endAutotunePolling = false;
			final long maxAutotuneLoggingTime = 30 * 60 * 1000;
			final long initialAutotuneLoggingTime = System.currentTimeMillis();
			do {
				Thread.currentThread().sleep(sleepTime);
				currentTime = System.currentTimeMillis();
				LOGGER.info("Polled autotune after:" + ((currentTime - initialAutotuneLoggingTime) / 1000) + " sec");
				if (!whasAutotuneRunning && autotuneService.isRunning()) {
					LOGGER.info("Start Autotune running discovered");
					whasAutotuneRunning = true;
				}
				if (whasAutotuneRunning && !autotuneService.isRunning()) {
					LOGGER.info("End Autotune running discovered!!!");
					endAutotunePolling = true;
				}
				if ((currentTime - initialAutotuneLoggingTime) > maxAutotuneLoggingTime) {
					LOGGER.error("The automatic theashold regulation is running toooo many time");
				}
				renew(apiClient);
			} while (!endAutotunePolling && (currentTime - initialAutotuneLoggingTime) <= maxAutotuneLoggingTime);
			assertTrue(whasAutotuneRunning, "The autotune service must have been runned");
			assertFalse(autotuneService.isRunning(), "The autotune is not currently runned");
			assertTrue(autotuneRepository.count() > 0l, "The threashold coefficient repo cannot be empty");
			RegisteredInteractionTestSession registeredTestSession = super.loadJsonDataModel(registeredSessionResource,
					RegisteredInteractionTestSession.class);
			GeboChatPipelinesControllerApi pipelinesControllerApi = new GeboChatPipelinesControllerApi(apiClient);

			int index = 1;
			String userChatContext = null;
			for (RegisteredInteractionTestModel registeredInteractionTestModel : registeredTestSession) {
				LOGGER.info("Running iteration nr:" + index);
				LOGGER.info(
						"**********************************************************************************************************************************************");
				LOGGER.info("Testing: " + registeredInteractionTestModel.getDescription());
				LOGGER.info(
						"**********************************************************************************************************************************************");
				PipelineRequestBody requestBody = new PipelineRequestBody();
				requestBody.setRequest(registeredInteractionTestModel.getRequest());
				requestBody.setEnvironment(getEnvironment(registeredInteractionTestModel));
				requestBody.getRequest().setChatProfileCode(chatProfiles.get(0).getCode());
				requestBody.getRequest().setUserChatContextCode(userChatContext);
				GeboChatResponse response = pipelinesControllerApi.executeDefaultChatPipeline(requestBody);
				assertNotNull(response, "The response cannot be null");
				assertNotNull(response.getQueryResponse(), "response text cannot be null");
				assertFalse(response.getQueryResponse().trim().length() == 0, "response text cannot be empty");
				assertNotNull(response.getPipelineRouterDecisionCode(), "The routing decision taken cannot be null");
				assertNotNull(response.getUserChatContextCode(), "The user chat context cannot be null");
				userChatContext = response.getUserChatContextCode();
				LOGGER.info("User:" + registeredInteractionTestModel.getRequest().getQuery());
				LOGGER.info("Routing code:" + response.getPipelineRouterDecisionCode());
				LOGGER.info("Assistant:" + response.getQueryResponse());
				verifyRoutingDecision(registeredInteractionTestModel, response);
				if (registeredInteractionTestModel.getResponseTestCriteria() != null
						&& registeredInteractionTestModel.getResponseTestCriteria().isCheckNotEmptyDocumentsList()) {
					checkAssertOnDocumentsList(registeredInteractionTestModel, response);
				}
				renew(apiClient);
				index++;
			}
		} finally {
			try {
				for (Path file : files) {
					try {
						Files.delete(file);
					} catch (Throwable t) {
					}

				}
			} catch (Throwable t) {
			}
		}
	}

	/**
	 * Verifies the routing decision taken by the pipeline for a single interaction.
	 * The default implementation checks the decision against the allowed routing
	 * decisions declared in the registered session (the pipeline-router behaviour).
	 * Subclasses testing the network of agents override this to assert that every
	 * request is delegated to the agents network.
	 */
	protected void verifyRoutingDecision(RegisteredInteractionTestModel registeredInteractionTestModel,
			GeboChatResponse response) {
		if (registeredInteractionTestModel.getResponseTestCriteria() != null
				&& registeredInteractionTestModel.getResponseTestCriteria().getAllowedRoutingDecisions() != null
				&& !registeredInteractionTestModel.getResponseTestCriteria().getAllowedRoutingDecisions().isEmpty()) {
			checkAssertOnRoutingDecisionTaken(registeredInteractionTestModel, response);
		}
	}

	protected void checkAssertOnDocumentsList(RegisteredInteractionTestModel registeredInteractionTestModel,
			GeboChatResponse response) {

		boolean foundDocsRefs = response.getDocumentsRef() != null && !response.getDocumentsRef().isEmpty();

		if (foundDocsRefs) {
			for (GResponseDocumentRef fnd : response.getDocumentsRef()) {
				LOGGER.info("Found doc:" + fnd.getDocumentCode() + " " + fnd.getName());
			}
		}

	}

	protected void checkAssertOnRoutingDecisionTaken(RegisteredInteractionTestModel registeredInteractionTestModel,
			GeboChatResponse response) {
		RespondingWith responseTaken = null;
		try {
			responseTaken = RespondingWith.valueOf(response.getPipelineRouterDecisionCode());
		} catch (Throwable th) {
			throw new IllegalStateException("The received response: " + response.getPipelineRouterDecisionCode()
					+ " does not match any RespondingWith default pipeline entries ");
		}
		boolean oneMatches = false;
		for (RespondingWith allowedCode : registeredInteractionTestModel.getResponseTestCriteria()
				.getAllowedRoutingDecisions()) {
			oneMatches |= allowedCode == responseTaken;
		}
		if (!oneMatches) {
			String message = "The routed decision:" + responseTaken + " is not contained in:"
					+ registeredInteractionTestModel.getResponseTestCriteria().getAllowedRoutingDecisions();
			LOGGER.error(message);
			throw new IllegalStateException(message);
		}

	}

	protected PipelineEnvironment getEnvironment(RegisteredInteractionTestModel model) {
		if (model.getDeepSearchEnvironment() != null)
			return model.getDeepSearchEnvironment();
		if (model.getShallowSearchEnvironment() != null)
			return model.getShallowSearchEnvironment();
		return null;
	}

}
