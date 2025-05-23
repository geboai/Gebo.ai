/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

/**
 * `IGInstallationTypeHolder` interface provides a contract for classes to return an installation type.
 * It contains a nested interface `IType` which should define a method to get the name of the type.
 * <p>
 * Gebo.ai comment agent
 */
public interface IGInstallationTypeHolder {

    /**
     * The `IType` interface represents a type with a `name` method.
     */
    public static interface IType {
        /**
         * Returns the name of the type.
         * @return the name of the type as a String
         */
        public String name();
    }

    /**
     * Retrieves the current installation type.
     * @return an instance of `IType` representing the installation type
     */
    public IType get();
}