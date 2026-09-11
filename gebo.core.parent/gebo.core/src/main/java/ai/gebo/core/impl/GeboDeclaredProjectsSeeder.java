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
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knowledgebase.repositories.ProjectRepository;

/**
 * Writes the projects declared under {@code ai.gebo.projects} into the store at
 * startup, after the knowledge bases they reference.
 *
 * <p>
 * A declared project naming a {@code rootKnowledgeBaseCode} that nothing
 * provides is NOT refused here. The reference is resolved when it is used, as
 * everywhere else in this configuration, and a deployment may legitimately point
 * a declared project at a knowledge base an admin created. What the ordering
 * does guarantee is that a knowledge base declared in the same file is already
 * written by the time the project that names it is.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GeboDeclaredProjectsSeeder extends GAbstractDeclaredEntitiesSeeder<GProject, ProjectRepository> {

	private final GeboKnowledgeBaseHierarchyConfig config;

	/**
	 * @param config             the declared hierarchy.
	 * @param repository         the project repository.
	 * @param applicationContext this bean's own context.
	 */
	public GeboDeclaredProjectsSeeder(GeboKnowledgeBaseHierarchyConfig config, ProjectRepository repository,
			ApplicationContext applicationContext) {
		super(repository, applicationContext);
		this.config = config;
	}

	@Override
	protected List<GProject> getDeclarations() {
		return config.getProjects();
	}

	@Override
	protected void setReadonly(GProject entity, Boolean value) {
		entity.setReadonly(value);
	}

	@Override
	protected Boolean getReadonly(GProject entity) {
		return entity.getReadonly();
	}

	@Override
	protected String describeKind() {
		return "project";
	}

	@Override
	public int getOrder() {
		return PROJECT_ORDER;
	}
}
