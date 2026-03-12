package ai.gebo.full_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag_threasholds_autotune.repository.ThreasholdAutotuneProcessResultRepository;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;
import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;
import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.GFileSystemShareReference;
import ai.gebo.monolithic.api.client.model.GFilesystemProjectEndpoint;
import ai.gebo.monolithic.api.client.model.GObjectRefGProjectEndpoint;
import ai.gebo.monolithic.api.client.model.GVirtualFilesystemRoot;
import ai.gebo.monolithic.api.client.model.JobSummary;
import ai.gebo.monolithic.api.client.model.OperationStatusGJobStatus;
import ai.gebo.monolithic.api.client.model.PathInfo;
import ai.gebo.monolithic.api.client.model.PathInfo.MetaTypeEnum;
import ai.gebo.monolithic.api.client.model.VFilesystemReference;
import ai.gebo.monolithic.app.Main;
import lombok.Data;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class FullSetupUseAndPipelineTest extends AbstractVendorSetupAndUseTest {
	private static final String filesIndex = "/test_files/index.json";
	@Autowired
	IGRuntimeBinder runtimeBinder;

	@Data
	public static class ClasspathFilePath {
		String path = null;
	}

	public static class ClasspathFileArray extends ArrayList<ClasspathFilePath> {
	};

	@Test
	public void setupCreateKnowledgeBaseAndRunPipelineTest() throws InterruptedException, IOException,
			InstantiationException, IllegalAccessException, GeboPersistenceException {
		Path folder = null;
		List<Path> files = new ArrayList<Path>();
		try {
			TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
			ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
					systemInfo.getSecurityHeader());
			Thread.currentThread().sleep(30000);
			renew(apiClient);
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
					"libreria tradizioni", ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint.class);
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
			} while (!endAutotunePolling && (currentTime - initialAutotuneLoggingTime) <= maxAutotuneLoggingTime);
			assertTrue(whasAutotuneRunning, "The autotune service must have been runned");
			assertFalse(autotuneService.isRunning(), "The autotune is not currently runned");
			assertTrue(autotuneRepository.count() > 0l, "The threashold coefficient repo cannot be empty");
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

}
