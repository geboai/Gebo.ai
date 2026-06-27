/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.json.JsonMapper;

/**
 * Gebo.ai comment agent Configuration class to define bean for RestTemplate.
 */
@Configuration
public class RestTemplateConfig {

	/**
	 * Default constructor.
	 */
	public RestTemplateConfig() {

	}

	/**
	 * Creates a new RestTemplate bean that can be used for making REST calls.
	 * 
	 * @return a configured RestTemplate instance
	 */
	@Bean
	public RestTemplate restTemplate(JsonMapper objectMapper) {
		JacksonJsonHttpMessageConverter jackson = new JacksonJsonHttpMessageConverter(objectMapper);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, jackson);
		return restTemplate;
	}

}