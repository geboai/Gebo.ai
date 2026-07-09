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

			GeboMicroservice.named("vectorizator_gebo_ai")
					.module("vectorizator-module", "vectorization-component", "vectorization-emitter-component",
							"vectorization-dispose-component")
					.module("rag-threashold-autotune-module", "rag-threashold-autotune-component")
					.build(),

			// fulltextor is the renamed textsearch microservice (full-text host).
			GeboMicroservice.named("fulltextor_gebo_ai")
					.module("fulltext-module", "fulltext-indexing-component")
					.build(),

			// graphicator is the renamed graphsearch microservice (knowledge-graph host).
			GeboMicroservice.named("graphicator_gebo_ai")
					.module("knowledge-graph-module", "knowledge-graph-component")
					.build(),

			GeboMicroservice.named("scheduler_gebo_ai")
					.module("scheduler-module", "scheduler-component")
					.build(),

			GeboMicroservice.named("jobs_gebo_ai")
					.module("async-publishing-job-module", "async-publishing-job-component", "job-status-notifier")
					.build(),

			// --- Content services (one per gebo.systems.parent handler) ------
			// Each hosts the shared content-infrastructure systems; some also a static default Content.Handler.<code>.
			GeboMicroservice.named("git_gebo_ai")
					.module("git-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.GIT.CONTENT.HANDLER")
					.build(),

			GeboMicroservice.named("filesystem_gebo_ai")
					.module("shared-filesystem-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.FILESYSTEM.CONTENT.HANDLER")
					.build(),

			GeboMicroservice.named("uploads_gebo_ai")
					.module("uploads-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.DEFAULT.UPLOADS.CONTENT.HANDLER")
					.build(),

			GeboMicroservice.named("userspace_gebo_ai")
					.module("userspace-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component", "Content.Handler.USERSPACE-CONTENTSYSTEM")
					.build(),

			GeboMicroservice.named("sharepoint_gebo_ai")
					.module("sharepoint-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component")
					.build(),

			GeboMicroservice.named("confluence_gebo_ai")
					.module("confluence-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component")
					.build(),

			GeboMicroservice.named("jira_gebo_ai")
					.module("jira-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component")
					.build(),

			GeboMicroservice.named("googledrive_gebo_ai")
					.module("google-drive-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component")
					.build(),

			GeboMicroservice.named("mcpclient_gebo_ai")
					.module("mcp-client-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component")
					.build(),

			GeboMicroservice.named("integration_gebo_ai")
					.module("integration-module", "module-ioc-dispatcher-component", "resources-dispose-component",
							"system-settings-controller-component")
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
