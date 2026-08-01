/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.knowledgebase.client.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the knowledge-base hierarchy client, bound from
 * {@code ai.gebo.knowledgebase.client.*}:
 *
 * <pre>
 * ai.gebo.knowledgebase.client:
 *   microservice-id: brain_gebo_ai
 *   projects-base-path: api/admin/ProjectsController
 *   knowledge-base-base-path: api/admin/KnowledgeBaseController
 * </pre>
 *
 * <p>
 * Note there is no {@code base-url}: the address of the knowledge-base
 * microservice is resolved from the topology by the
 * {@code GeboMicroserviceUrlResolver}, so it follows the deployment's addressing
 * strategy instead of being pinned here.
 * </p>
 */
@ConfigurationProperties(prefix = "ai.gebo.knowledgebase.client")
public class GeboKnowledgeBaseClientProperties {

	/** Id of the microservice hosting GProject/GKnowledgeBase - the RAG/LLM admin service. */
	private String microserviceId = "brain_gebo_ai";

	/** Base path of gebo.core's ProjectsController on the owning microservice. */
	private String projectsBasePath = "api/admin/ProjectsController";

	/** Base path of gebo.core's KnowledgeBaseController on the owning microservice. */
	private String knowledgeBaseBasePath = "api/admin/KnowledgeBaseController";

	/**
	 * How long a lookup is cached, keyed by code. Both types are read constantly
	 * during content ingestion/browsing and written rarely (project/knowledge-base
	 * admin operations), so the same trade-off as the security/secrets/acl clients
	 * applies: a change made elsewhere becomes visible here only after this window.
	 */
	private Duration cacheTtl = Duration.ofSeconds(60);

	/** Hard bound on cached entries; past it the cache is emptied rather than grown. */
	private int cacheMaxEntries = 2000;

	public String getMicroserviceId() {
		return microserviceId;
	}

	public void setMicroserviceId(String microserviceId) {
		this.microserviceId = microserviceId;
	}

	public String getProjectsBasePath() {
		return projectsBasePath;
	}

	public void setProjectsBasePath(String projectsBasePath) {
		this.projectsBasePath = projectsBasePath;
	}

	public String getKnowledgeBaseBasePath() {
		return knowledgeBaseBasePath;
	}

	public void setKnowledgeBaseBasePath(String knowledgeBaseBasePath) {
		this.knowledgeBaseBasePath = knowledgeBaseBasePath;
	}

	public Duration getCacheTtl() {
		return cacheTtl;
	}

	public void setCacheTtl(Duration cacheTtl) {
		this.cacheTtl = cacheTtl;
	}

	public int getCacheMaxEntries() {
		return cacheMaxEntries;
	}

	public void setCacheMaxEntries(int cacheMaxEntries) {
		this.cacheMaxEntries = cacheMaxEntries;
	}
}
