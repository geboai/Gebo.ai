/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.async.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

/**
 * Gebo.ai comment agent
 * Configuration class for setting up asynchronous task execution capabilities in the application.
 * Extends AsyncConfigurerSupport to provide custom executor configuration.
 */
@Configuration
public class AsyncConfiguration extends AsyncConfigurerSupport {

    /**
     * Override the default asynchronous executor configuration.
     * This method is typically used to provide a custom {@link Executor} for executing asynchronous tasks.
     *
     * @return the executor to be used for asynchronous execution, defaults to the super implementation.
     */
    @Override
    public Executor getAsyncExecutor() {
        return super.getAsyncExecutor();
    }
}