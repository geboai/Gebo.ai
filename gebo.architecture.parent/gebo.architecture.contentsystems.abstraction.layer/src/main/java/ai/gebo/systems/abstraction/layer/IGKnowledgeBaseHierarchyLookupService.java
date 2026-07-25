/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;

/**
 * Looks up a {@link GProject} or its root {@link GKnowledgeBase} by code.
 *
 * <p>
 * Both are owned by brain.gebo.ai (the RAG/LLM admin microservice); every other
 * content-handler service - filesystem, git, jira, confluence, sharepoint,
 * googledrive, aws-s3, uploads, mcpclient - only ever navigates the hierarchy a
 * project endpoint hangs off of, never stores it. A direct
 * {@code IGPersistentObjectManager.findById(GProject.class, ...)} call from one of
 * those services reads its OWN, unrelated Mongo database and returns {@code null}.
 * </p>
 *
 * <p>
 * <b>It lives in its own implementation, decided by what a service packages.</b> A
 * service that owns the store (brain, the monolith) depends on
 * {@code gebo.knowledgebase.hierarchy.local} and reads Mongo directly through
 * {@code IGPersistentObjectManager}; a service that does not depends on
 * {@code gebo.microservices.knowledgebase.client} and gets a REST client onto
 * brain's {@code KnowledgeBaseController}/{@code ProjectsController} instead. The
 * two are never on the same classpath.
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface IGKnowledgeBaseHierarchyLookupService {

	/**
	 * Finds a project by its code.
	 *
	 * @param code the project code; {@code null} yields {@code null}
	 * @return the project, or {@code null} if no such project exists
	 * @throws GeboPersistenceException if the lookup fails
	 */
	GProject findProjectByCode(String code) throws GeboPersistenceException;

	/**
	 * Finds a knowledge base by its code.
	 *
	 * @param code the knowledge base code; {@code null} yields {@code null}
	 * @return the knowledge base, or {@code null} if no such knowledge base exists
	 * @throws GeboPersistenceException if the lookup fails
	 */
	GKnowledgeBase findKnowledgeBaseByCode(String code) throws GeboPersistenceException;
}
