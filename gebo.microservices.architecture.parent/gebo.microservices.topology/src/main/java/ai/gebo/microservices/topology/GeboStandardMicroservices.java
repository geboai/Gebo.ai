/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology;

import java.util.List;

/**
 * The built-in Gebo.ai microservices topology (three levels:
 * {@code microserviceId -> messagingModuleId -> [messagingSystemId]}), deduced
 * from the messaging components in the codebase.
 *
 * <p>
 * These are shipped as the <b>default</b> topology. At runtime the Spring layer
 * ({@link ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration})
 * seeds a topology from this list and lets any deployment - including a
 * third-party maker's service - add or override entries via
 * {@code gebo.microservices.topology.*} in {@code application.yml} (the gateway
 * ships the full map there, editable). Outside Spring,
 * {@link GeboMicroservicesTopology#defaults()} exposes exactly this set.
 * </p>
 *
 * <p>
 * System-id notes: the processing modules expose stable, code-declared system
 * ids. Content-handler modules additionally register a
 * {@code Content.Handler.<code>} system <b>dynamically per configured endpoint</b>
 * (only git/filesystem/uploads/userspace ship a static default code); those
 * three content-infrastructure systems - {@code module-ioc-dispatcher-component},
 * {@code resources-dispose-component}, {@code system-settings-controller-component}
 * - come from the content abstraction-layer base classes each handler extends.
 * The dynamic systems are intentionally left to the editable config.
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class GeboStandardMicroservices {

	/**
	 * The API gateway's microservice id (its {@code spring.application.name}
	 * {@code gateway.gebo.ai} in the canonical, dot-free underscore form). The
	 * gateway is the routing edge, not a routable backend, so it is intentionally
	 * absent from {@link #DEFAULTS}; it is exposed here as the default target for
	 * {@link ai.gebo.microservices.topology.GeboMicroserviceUrlResolver.Strategy#GATEWAY}
	 * url resolution.
	 */
	public static final String GATEWAY_MICROSERVICE_ID = "gateway_gebo_ai";

	/** Immutable built-in topology, in declaration order. */
	public static final List<GeboMicroservice> DEFAULTS = List.of(

			// --- Core / orchestration services -------------------------------
			// Names are the dot-free underscore form (the '.' -> '_' rule); the
			// GeboMicroservice constructor also normalises, so either form works.
			GeboMicroservice.named("brain_gebo_ai")
					.module("core-module", "user-messages-concentrator-component", "mongo-dispose-documents-component",
							"session-shrinker", "sessionLifeCycleService")
					// async-publishing-job-component/job-status-notifier: brain isn't a content
					// handler, but gebo.core (added to brain so it can host the KB/project admin
					// controllers under microservices) pulls gebo.architecture.contentsystems.abstraction.layer
					// onto brain's classpath transitively, and with it GGeboIngestionJobQueueServiceImpl,
					// which requires a concrete AbstractJobLaunchManager/AbstractJobStatusEmitter bean
					// regardless of whether brain ever actually owns a published endpoint - see
					// BrainJobLaunchManager/BrainJobStatusEmitter in brain.gebo.ai.
					// USAGE-CONCENTRATOR: brain's own instance of the LLM-usage emitter
					// (BrainLLMSUsageCrudService) - registered under brain's own module, never
					// the shared LLMS-USAGE-MONITOR target constant (tyr's, declared below),
					// to avoid colliding with the RabbitMQ bridge's remote proxy for it.
					.module("brain-module", "async-publishing-job-component", "job-status-notifier",
							"USAGE-CONCENTRATOR")
					.build(),

			// AuthN/AuthZ, OAuth2 integration. Owns no messaging module (REST-only edge).
			GeboMicroservice.named("heimdall_gebo_ai").build(),

			GeboMicroservice.named("chunker_gebo_ai")
					.module("tokenizer-module", "tokenizer-component", "dispose-chunking-session-for-jobs")
					.build(),

			// rag-threashold-autotune-module.rag-threashold-autotune-component is NOT
			// implemented by vectorizator - it's RagThreasholdAutotuneFinishedWorkflowReceiver
			// (gebo.architecture.rag-threasholds-autotune, pulled in transitively via
			// gebo.architecture.chat.abstraction.layer/gebo.architecture.agents.standard),
			// which only brain.gebo.ai has on its classpath among microservices. It reacts
			// to a same-service GFinishedWorkflowPayload broadcast, never a cross-service
			// target, so it must stay out of the topology entirely - same reasoning as
			// tyr_gebo_ai's dropped identities above.
			// USAGE-CONCENTRATOR: vectorizator's own instance of the LLM-usage emitter
			// (VectorizatorLLMSUsageCrudService) - see the identical note under
			// brain-module above.
			GeboMicroservice.named("vectorizator_gebo_ai")
					.module("vectorizator-module", "vectorization-component", "vectorization-emitter-component",
							"vectorization-dispose-component", "USAGE-CONCENTRATOR")
					.build(),

			// fulltextor is the renamed textsearch microservice (full-text host).
			GeboMicroservice.named("fulltextor_gebo_ai")
					.module("fulltext-module", "fulltext-indexing-component")
					.build(),

			// graphicator is the renamed graphsearch microservice (knowledge-graph host).
			// USAGE-CONCENTRATOR: graphicator's own instance of the LLM-usage emitter
			// (GraphicatorLLMSUsageCrudService) - see the identical note under
			// brain-module above.
			GeboMicroservice.named("graphicator_gebo_ai")
					.module("knowledge-graph-module", "knowledge-graph-component", "USAGE-CONCENTRATOR")
					.build(),

			// tyr is the workflows/usage/jobs-tracking microservice, and now also hosts the
			// CENTRAL publish scheduler (gebo.architecture.scheduling's
			// AbstractCentralSchedulingService / ClusteredCentralSchedulingService): every
			// content-handler sends its reschedule requests to scheduler-module.scheduler-component
			// here instead of running its own local scheduler, and tyr dispatches
			// PublishProjectEndpointMessagePayload back to each handler's own
			// <handler>-module.async-publishing-job-component when a run is due (see each
			// content-handler's own AbstractJobLaunchManager/AbstractJobStatusEmitter
			// subclass below, registered under that handler's own module id -
			// ai.gebo.jobs.services.impl.AbstractJobLaunchManager/AbstractJobStatusEmitter).
			//
			// async-publishing-job-module.job-status-replication-receiver remains tyr's one
			// genuine local component under that shared constant (GJobStatusReplicatorReceiverService,
			// gebo.architecture.compute.workflow) - async-publishing-job-component/job-status-notifier
			// are NOT declared here: they are per-handler identities now (each content-handler's
			// own module below), never tyr's.
			//
			// jobs-master-module is the OTHER genuine cross-service target tyr hosts:
			// GWorkflowsConcentratorMessagesReceiverFactory's user-messages-concentrator-component
			// is where every content-processing microservice (vectorizator, fulltextor,
			// graphicator, every content-handler via GIOCModuleContentsDispatcher, ...) sends
			// GContentsProcessingStatusUpdatePayload, which feeds ContentsBatchProcessedRepository
			// - the data AbstractWorkflowStatusHandler.computeWorkflowStatus() aggregates to decide
			// a job is finished. Without this module declared, none of those senders could ever
			// build a remote proxy for it, so tyr's own copy of ContentsBatchProcessedRepository
			// stayed permanently empty and GWorkflowStatusDeamonServiceImpl could never observe a
			// completed batch. end-of-workflow-compute-service is that same concentrator's own
			// same-service loopback (GWorkflowsConcentratorMessagesEmitterImpl emits
			// ComputeWorkflowEndPayload to it, entirely within tyr) - harmless to also expose,
			// nothing elsewhere implements this identity.
			// LLMS-USAGE-MONITOR.USAGE-CONCENTRATOR (LLMUsageConcentratorReceiverFactory) is
			// tyr's LLM-usage-tracking aggregator by design: brain/vectorizator/graphicator's
			// LLMSUsageCrudServiceImpl each send here via a TARGETED
			// envelope.setTargetModule(...), not a broadcast, so without this declared the
			// senders' RabbitMQ bridge could never build a remote proxy for it and
			// cross-service usage events would silently fail to route. Found undeclared
			// during a messaging-topology audit (docs/MICROSERVICES-MESSAGING-TOPOLOGY.md);
			// tyr ownership itself was confirmed intentional, only the topology entry was
			// missing.
			GeboMicroservice.named("tyr_gebo_ai")
					.module("async-publishing-job-module", "job-status-replication-receiver")
					.module("jobs-master-module", "user-messages-concentrator-component",
							"end-of-workflow-compute-service")
					.module("scheduler-module", "scheduler-component")
					.module("LLMS-USAGE-MONITOR", "USAGE-CONCENTRATOR")
					.build(),

			// --- Content services (one per gebo.systems.parent handler) ------
			// Each hosts the shared content-infrastructure systems; some also a static default Content.Handler.<code>.
			// Each also hosts its own <Handler>JobStatusReplicatorService subclass
			// (AbstractJobStatusReplicatorService, gebo.architecture.contentsystems.abstraction.layer)
			// - one per handler module, @ConditionalOnMicroservices, each hardcoding
			// its own already-owned module id in its constructor (mirroring
			// AbstractJobLaunchManager's subclasses below - no @Value, no
			// application.yml override). A messaging module belongs to exactly one
			// microservice (GeboMicroservicesTopology enforces it), so its systemId -
			// job-status-replicator - is declared under each service's OWN
			// already-owned module, not a shared one. That's what lets
			// RabbitMqExternalMessageEmitterProviderSource register it as a known
			// emitter on every OTHER microservice (concretely, tyr, which needs to
			// recognise the sender of a replicated GJobStatus).
			//
			// async-publishing-job-component/job-status-notifier follow the identical
			// pattern, now for the job-launch/job-status pair: each handler's own
			// <Handler>JobLaunchManager/<Handler>JobStatusEmitter (in that handler's own
			// module, @ConditionalOnMicroservices) registers under its own already-owned
			// module id instead of the shared monolithic constant, so tyr's central
			// scheduler (scheduler-module.scheduler-component, above) can address each
			// handler's job launcher as a real, unique, topology-routable target.
			GeboMicroservice.named("git_gebo_ai")
					.module("git-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.GIT.CONTENT.HANDLER",
							"job-status-replicator", "async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("filesystem_gebo_ai")
					.module("shared-filesystem-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.FILESYSTEM.CONTENT.HANDLER",
							"job-status-replicator", "async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("uploads_gebo_ai")
					.module("uploads-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.UPLOADS.CONTENT.HANDLER",
							"job-status-replicator", "async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("userspace_gebo_ai")
					.module("userspace-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.USERSPACE-CONTENTSYSTEM",
							"job-status-replicator", "async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("sharepoint_gebo_ai")
					.module("sharepoint-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("confluence_gebo_ai")
					.module("confluence-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("jira_gebo_ai")
					.module("jira-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("aws_s3_gebo_ai")
					.module("aws-s3-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("googledrive_gebo_ai")
					.module("google-drive-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("mcpclient_gebo_ai")
					.module("mcp-client-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build(),

			GeboMicroservice.named("integration_gebo_ai")
					.module("integration-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator",
							"async-publishing-job-component", "job-status-notifier")
					.build());

	/**
	 * Built-in set of microservices that participate in the LLM
	 * models-replication cache (the services that instantiate memory-resident live
	 * LLM clients via {@code IGRuntimeModelConfigurationDao} and must stay in sync).
	 * Shipped as the shared default; overridable via
	 * {@code gebo.microservices.topology.models-replication-participants} in
	 * {@code application.yml}. Ids are the dot-free underscore form.
	 */
	public static final List<String> DEFAULT_MODELS_REPLICATION_PARTICIPANTS = List.of("brain_gebo_ai",
			"vectorizator_gebo_ai", "graphicator_gebo_ai");

	private GeboStandardMicroservices() {
		// constants holder
	}
}
