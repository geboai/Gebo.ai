/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.hierarchy.local.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knowledgebase.hierarchy.local.LocalKnowledgeBaseHierarchyLookupService;
import ai.gebo.systems.abstraction.layer.IGKnowledgeBaseHierarchyLookupService;

/**
 * Publishes the local, Mongo-backed {@link IGKnowledgeBaseHierarchyLookupService}
 * on a service that owns the GProject/GKnowledgeBase store.
 *
 * <p>
 * Ordered first so that, should a misconfigured deployment ever put both
 * implementation modules on one classpath, the owner wins: a service that can
 * read the store itself has no business proxying the question to another
 * service. The remote client then backs off on {@code @ConditionalOnMissingBean}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(GeboKnowledgeBaseLocalCacheProperties.class)
public class GeboLocalKnowledgeBaseHierarchyLookupAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(IGKnowledgeBaseHierarchyLookupService.class)
	public LocalKnowledgeBaseHierarchyLookupService localKnowledgeBaseHierarchyLookupService(
			IGPersistentObjectManager persistentObjectManager, GeboKnowledgeBaseLocalCacheProperties properties) {
		return new LocalKnowledgeBaseHierarchyLookupService(persistentObjectManager, properties.getCacheTtl(),
				properties.getCacheMaxEntries());
	}
}
