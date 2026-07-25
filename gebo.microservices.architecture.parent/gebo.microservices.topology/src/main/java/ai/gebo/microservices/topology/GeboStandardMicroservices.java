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
					.module("brain-module")
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
			GeboMicroservice.named("vectorizator_gebo_ai")
					.module("vectorizator-module", "vectorization-component", "vectorization-emitter-component",
							"vectorization-dispose-component")
					.build(),

			// fulltextor is the renamed textsearch microservice (full-text host).
			GeboMicroservice.named("fulltextor_gebo_ai")
					.module("fulltext-module", "fulltext-indexing-component")
					.build(),

			// graphicator is the renamed graphsearch microservice (knowledge-graph host).
			GeboMicroservice.named("graphicator_gebo_ai")
					.module("knowledge-graph-module", "knowledge-graph-component")
					.build(),

			// tyr is the workflows/usage/jobs-tracking microservice. It does NOT depend on
			// gebo.architecture.scheduling or gebo.architecture.contentsystems.abstraction.layer,
			// so it never implements scheduler-module.scheduler-component,
			// async-publishing-job-module.async-publishing-job-component, or
			// async-publishing-job-module.job-status-notifier - those three identities are
			// pure same-service local self-loops that each content-handler runs against its
			// own local GSchedulingTimeServiceImpl/JobLaunchManagerImpl/JobStatusEmitter beans
			// (gebo.architecture.scheduling and gebo.architecture.contentsystems.abstraction.layer
			// are on every content-handler's classpath), never routed cross-service - so they must
			// stay out of the topology entirely (GeboMicroservicesTopology requires exactly one
			// owner per module, and declaring tyr as that owner here was never accurate: it
			// caused RabbitMqExternalMessage{Emitter,Receiver}ProviderSource, once RabbitMQ was
			// enabled, to build a remote proxy for these on every content-handler that collided
			// with its own local bean under the identical identity in GBaseMessageBroker).
			// job-status-replication-receiver is tyr's one genuine local component under this
			// module (GJobStatusReplicatorReceiverService, gebo.architecture.compute.workflow).
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
			GeboMicroservice.named("tyr_gebo_ai")
					.module("async-publishing-job-module", "job-status-replication-receiver")
					.module("jobs-master-module", "user-messages-concentrator-component",
							"end-of-workflow-compute-service")
					.build(),

			// --- Content services (one per gebo.systems.parent handler) ------
			// Each hosts the shared content-infrastructure systems; some also a static default Content.Handler.<code>.
			// Each also hosts GJobStatusReplicatorService (gebo.architecture.contentsystems.abstraction.layer
			// is on every one of their classpaths). A messaging module belongs to
			// exactly one microservice (GeboMicroservicesTopology enforces it), so
			// its systemId - job-status-replicator - is declared under each
			// service's OWN already-owned module, not a shared one; each service's
			// own application.yml points GJobStatusReplicatorService at that same
			// module id (ai.gebo.jobs.replicator.module-id). That's what lets
			// RabbitMqExternalMessageEmitterProviderSource register it as a known
			// emitter on every OTHER microservice (concretely, tyr, which needs to
			// recognise the sender of a replicated GJobStatus).
			GeboMicroservice.named("git_gebo_ai")
					.module("git-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.GIT.CONTENT.HANDLER",
							"job-status-replicator")
					.build(),

			GeboMicroservice.named("filesystem_gebo_ai")
					.module("shared-filesystem-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.FILESYSTEM.CONTENT.HANDLER",
							"job-status-replicator")
					.build(),

			GeboMicroservice.named("uploads_gebo_ai")
					.module("uploads-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.UPLOADS.CONTENT.HANDLER",
							"job-status-replicator")
					.build(),

			GeboMicroservice.named("userspace_gebo_ai")
					.module("userspace-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.USERSPACE-CONTENTSYSTEM",
							"job-status-replicator")
					.build(),

			GeboMicroservice.named("sharepoint_gebo_ai")
					.module("sharepoint-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
					.build(),

			GeboMicroservice.named("confluence_gebo_ai")
					.module("confluence-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
					.build(),

			GeboMicroservice.named("jira_gebo_ai")
					.module("jira-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
					.build(),

			GeboMicroservice.named("aws_s3_gebo_ai")
					.module("aws-s3-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
					.build(),

			GeboMicroservice.named("googledrive_gebo_ai")
					.module("google-drive-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
					.build(),

			GeboMicroservice.named("mcpclient_gebo_ai")
					.module("mcp-client-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
					.build(),

			GeboMicroservice.named("integration_gebo_ai")
					.module("integration-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "job-status-replicator")
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
