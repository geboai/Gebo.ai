/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.heavy_workload_integration_tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ai.gebo.ragsystem.vectorstores.test.services.TestVectorStoreConfig;

@Configuration
public class TestsConfig {

	public TestsConfig() {

	}

	@Bean
	public TestVectorStoreConfig testVectorStoreConfig() {
		TestVectorStoreConfig config=new TestVectorStoreConfig();
		config.setMillisecondResponseTime(2500);
		return config;
	}

}
