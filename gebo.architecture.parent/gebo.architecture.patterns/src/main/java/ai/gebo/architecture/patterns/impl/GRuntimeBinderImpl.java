/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns.impl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeBinder;

/**
 * Gebo.ai comment agent
 * GRuntimeBinderImpl is a service class that implements the IGRuntimeBinder interface.
 * It is responsible for obtaining implementations of specified types using the Spring BeanFactory.
 */
@Service
public class GRuntimeBinderImpl implements IGRuntimeBinder {

    // Autowired annotation is used to inject the BeanFactory into this class
    @Autowired
    BeanFactory factory;

    /**
     * Default constructor for GRuntimeBinderImpl.
     */
    public GRuntimeBinderImpl() {

    }

    /**
     * This method retrieves an instance of the specified type from the Spring BeanFactory.
     *
     * @param <T>  The type of the bean to be returned.
     * @param type The class object of the desired bean type.
     * @return An instance of the specified type.
     */
    @Override
    public <T> T getImplementationOf(Class<T> type) {
        return factory.getBean(type);
    }

}