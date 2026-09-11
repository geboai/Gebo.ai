/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The top of the knowledge base hierarchy, declared by the deployment:
 *
 * <pre>
 * ai.gebo:
 *   knowledgebases:
 *     - code: COMPANY-KB
 *       description: Company knowledge base
 *       accessibleToAll: true
 *   projects:
 *     - code: COMPANY-DOCS
 *       description: Corporate documents
 *       rootKnowledgeBaseCode: COMPANY-KB
 *       accessibleToAll: true
 * </pre>
 *
 * <p>
 * Together with {@code ai.gebo.<content handler>.systems} and
 * {@code ai.gebo.<content handler>.datasources} this completes the chain: a
 * knowledge base holds projects, a project is what a data source feeds through
 * its {@code parentProjectCode}, and a data source reads through a content
 * management system. A deployment can therefore come up with the whole
 * hierarchy already in place.
 * </p>
 *
 * <h2>One bean for both lists</h2>
 * <p>
 * They are bound together because they are one decision - the shape of the
 * knowledge base - and because the order they are seeded in matters: a project
 * names the knowledge base it belongs to, so the knowledge bases have to exist
 * first. Keeping the two lists in one place makes that relationship visible
 * where it is configured rather than only in the seeders.
 * </p>
 *
 * <p>
 * Both are written into Mongo at startup and marked {@code readonly}, for the
 * same reason data sources are: projects and knowledge bases are resolved by
 * code through {@code IGPersistentObjectManager} from everywhere, and none of
 * those paths consults a configuration bean.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo")
@Validated
@Data
public class GeboKnowledgeBaseHierarchyConfig {

	/** {@code ai.gebo.knowledgebases} */
	private List<@Valid GKnowledgeBase> knowledgebases = new ArrayList<GKnowledgeBase>();

	/** {@code ai.gebo.projects} */
	private List<@Valid GProject> projects = new ArrayList<GProject>();
}
