/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

import ai.gebo.architecture.patterns.IGRuntimeBinder;

/**
 * Gebo.ai comment agent
 *
 * This interface defines a factory for creating instances of IGRunnable.
 * Implementors are responsible for defining the creation logic using the provided IGRuntimeBinder.
 */
public interface IGRunnableFactory {

    /**
     * Creates an instance of IGRunnable using the given runtime binder.
     *
     * @param runtimeBinder An instance of IGRuntimeBinder to be used in the creation process.
     * @return A new instance of IGRunnable.
     */
    public IGRunnable create(IGRuntimeBinder runtimeBinder);
}