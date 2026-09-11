/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GAbstractDeclaredEntitiesSeeder;
import ai.gebo.core.config.GeboKnowledgeBaseHierarchyConfig;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;

/**
 * Writes the knowledge bases declared under {@code ai.gebo.knowledgebases} into
 * the store at startup.
 *
 * <p>
 * First of the hierarchy, since a project names the knowledge base it belongs
 * to and a data source names the project.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GeboDeclaredKnowledgeBasesSeeder
		extends GAbstractDeclaredEntitiesSeeder<GKnowledgeBase, KnowledgeBaseRepository> {

	private final GeboKnowledgeBaseHierarchyConfig config;

	/**
	 * @param config             the declared hierarchy.
	 * @param repository         the knowledge base repository.
	 * @param applicationContext this bean's own context.
	 */
	public GeboDeclaredKnowledgeBasesSeeder(GeboKnowledgeBaseHierarchyConfig config,
			KnowledgeBaseRepository repository, ApplicationContext applicationContext) {
		super(repository, applicationContext);
		this.config = config;
	}

	@Override
	protected List<GKnowledgeBase> getDeclarations() {
		return config.getKnowledgebases();
	}

	@Override
	protected void setReadonly(GKnowledgeBase entity, Boolean value) {
		entity.setReadonly(value);
	}

	@Override
	protected Boolean getReadonly(GKnowledgeBase entity) {
		return entity.getReadonly();
	}

	@Override
	protected String describeKind() {
		return "knowledge base";
	}

	@Override
	public int getOrder() {
		return KNOWLEDGE_BASE_ORDER;
	}
}
