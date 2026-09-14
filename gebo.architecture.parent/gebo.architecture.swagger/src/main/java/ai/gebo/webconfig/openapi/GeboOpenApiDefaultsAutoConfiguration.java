/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.webconfig.openapi;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * AI generated comments
 *
 * Contributes the OpenAPI defaults shared by every Gebo application shipping this
 * module - see {@code gebo-openapi-defaults.properties}, most notably the version
 * of the served specification, which has to stay readable by the swagger-codegen
 * generators building the client stubs.
 *
 * <p>
 * It is an auto configuration rather than a plain {@code @Configuration} on
 * purpose: a library default must not depend on how the application happens to
 * have aimed its component scan. {@link SwaggerConfig} sits in
 * {@code ai.gebo.webconfig.openapi} and is only picked up by the services scanning
 * the whole of {@code ai.gebo}; eureka.gebo.ai declares a bare
 * {@code @SpringBootApplication} and gateway.gebo.ai a deliberately narrow
 * {@code scanBasePackages}, so both of them used to miss these defaults and served
 * a 3.1 specification while every other service served 3.0.
 *
 * <p>
 * Values reaching the environment through {@link PropertySource} sit below the
 * application's own configuration in the Spring Boot precedence order, so an
 * application.yml always overrides what is declared here.
 */
@AutoConfiguration
@PropertySource("classpath:gebo-openapi-defaults.properties")
public class GeboOpenApiDefaultsAutoConfiguration {

}
