package ai.gebo.microservices_cluster_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gebo.microservices.api.client.brain.api.LogViewControllerApi;
import gebo.microservices.api.client.brain.model.DataPage;
import gebo.microservices.api.client.brain.model.GObjectRefGProjectEndpoint;
import gebo.microservices.api.client.brain.model.JobsEntriesForProjectEndpointFilter;
import gebo.microservices.api.client.brain.model.PageGJobStatusItem;
import gebo.microservices.api.client.filesystem.api.FileSystemSharesSettingControllerApi;
import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;
import gebo.microservices.api.client.filesystem.model.GFileSystemShareReference;
import gebo.microservices.api.client.filesystem.model.GFilesystemProjectEndpoint;
import gebo.microservices.api.client.filesystem.model.GVirtualFilesystemRoot;
import gebo.microservices.api.client.filesystem.model.PathInfo;
import gebo.microservices.api.client.filesystem.model.ReindexingProgrammedTable;
import gebo.microservices.api.client.filesystem.model.ReindexingTime;
import gebo.microservices.api.client.filesystem.model.VFilesystemReference;
import gebo.microservices.api.client.heimdall.model.SecurityHeaderData;

/**
 * Cluster counterpart of {@code SchedulerIntegrationTests} in the monolith
 * (gebo.ai.app): proves the centralized publish scheduler works end-to-end
 * under the microservices architecture too - here that means tyr's
 * {@code ClusteredCentralSchedulingService} (leader-lease-gated
 * {@code AbstractCentralSchedulingService}), reached via
 * scheduler-module.scheduler-component from the filesystem microservice's
 * {@code GAbstractSystemsArchitectureController.processReschedule}, and
 * dispatching back to filesystem's own
 * {@code FilesystemJobLaunchManager}/{@code shared-filesystem-module.async-publishing-job-component}
 * when the run comes due.
 * <p>
 * A project endpoint is programmed with a single one-shot run ~2 minutes in
 * the future (never launched directly), then the test polls
 * gebo.core's {@code LogViewController} - exposed on <b>brain</b>, since
 * gebo.core is a brain dependency - for a job entry to appear against that
 * endpoint, which can only happen if tyr's scheduler tick actually fired it.
 */
public class SetupUseMicroservicesClusterSchedulerIT extends AbstractMicroservicesClusterSetupUseChatTest {

	@Test
	public void scheduledPublishIsLaunchedByCentralScheduler() throws Exception {
		Path folder = null;
		try {
			SecurityHeaderData header = executeSystemSetupBySecret();
			renew(header);

			folder = Files.createTempDirectory("gebo-microservices-scheduler-tests");
			LOGGER.info("New shared folder: {}", folder);

			gebo.microservices.api.client.filesystem.invoker.ApiClient fs = filesystemClient(header);
			FileSystemSharesSettingControllerApi sharesApi = new FileSystemSharesSettingControllerApi(fs);
			GFileSystemShareReference shareReference = new GFileSystemShareReference();
			shareReference.setMongoConfigured(true);
			shareReference.setDescription("Scheduler test shared filesystem");
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

			String projectCode = createKnowledgeBaseAndProject(header, "scheduler test project");
			renew(header);

			FileSystemsControllerApi fileSystemsApi = new FileSystemsControllerApi(filesystemClient(header));
			GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
			endpoint.setDescription("scheduler test endpoint");
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

			// Goes through the controller (insert/updateFilesystemEndpoint), not a direct
			// persistence call, so the server-side GAbstractSystemsArchitectureController
			// actually sends the reschedule request to tyr's central scheduler.
			GFilesystemProjectEndpoint updated = fileSystemsApi.updateFilesystemEndpoint(inserted);
			renew(header);
			LOGGER.info("Endpoint {} scheduled for {} - waiting for tyr's central scheduler to dispatch it",
					updated.getCode(), scheduledAt);

			GObjectRefGProjectEndpoint ref = new GObjectRefGProjectEndpoint();
			ref.setCode(updated.getCode());
			// The className the filesystem service resolves via Class.forName is its OWN
			// server-side domain type, not this generated client stub's model class.
			ref.setClassName("ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint");

			// The run is ~2 minutes out and the scheduler ticks every 60s, so give it
			// comfortable room either side rather than polling from the very start.
			Thread.sleep(Duration.ofSeconds(90).toMillis());

			boolean launched = false;
			int nCycles = 0;
			int NMAXCYCLES = 10;
			do {
				JobsEntriesForProjectEndpointFilter filter = new JobsEntriesForProjectEndpointFilter();
				filter.setEndpointRef(ref);
				DataPage page = new DataPage();
				page.setPage(0);
				page.setPageSize(10);
				filter.setPage(page);
				// Rebuilt every iteration - see the identical note in the chat pipeline
				// driver: an already-built ApiClient never picks up a later renew(header).
				LogViewControllerApi logViewApi = new LogViewControllerApi(brainClient(header));
				PageGJobStatusItem result = logViewApi.getJobsEntriesForProjectEndpoint(filter);
				List<Map<String, Object>> content = objectMapper.convertValue(result.getContent(),
						objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
				LOGGER.info("On cycle=>" + nCycles + " jobs launched for endpoint so far:"
						+ (content == null ? 0 : content.size()));
				launched = content != null && !content.isEmpty();
				if (launched) {
					break;
				}
				Thread.sleep(Duration.ofSeconds(20).toMillis());
				renew(header);
				nCycles++;
			} while (nCycles < NMAXCYCLES);

			assertTrue(launched,
					"tyr's central scheduler must have dispatched a publish for the scheduled endpoint by now");
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
