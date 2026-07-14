/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.acl.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.microservices.acl.client.RestAclAliasesDao;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * Wires the remote, locally-cached ACL store.
 *
 * <p>
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean(IAclAliasesDao.class)}:
 * the owner, which packages {@code gebo.acl.mongo}, keeps its local Mongo DAO and
 * this backs off. The two modules are never on one classpath, so the choice is made
 * in the pom.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboAclClientProperties.class)
public class GeboAclMicroserviceClientAutoConfiguration {

	static final String WEB_CLIENT_BEAN = "geboAclClientWebClient";

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboAclClientWebClient() {
		return WebClient.builder().build();
	}

	@Bean
	@ConditionalOnMissingBean(IAclAliasesDao.class)
	public IAclAliasesDao restAclAliasesDao(@Qualifier(WEB_CLIENT_BEAN) WebClient geboAclClientWebClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboAclClientProperties properties) {
		return new RestAclAliasesDao(geboAclClientWebClient, urlResolver, tokenPropagator,
				properties.getMicroserviceId(), properties.getBasePath(), properties.getCacheTtl());
	}
}
