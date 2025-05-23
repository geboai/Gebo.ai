/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines a repository pattern for managing implementations of a certain interface.
 * It provides methods to retrieve, search, map, and add implementations.
 *
 * @param <ImplementedInterface> The interface type that implementations will adhere to.
 */
public interface IGImplementationsRepositoryPattern<ImplementedInterface> {
    
    /**
     * Retrieves a list of all implementations of the specified interface.
     * 
     * @return A list of all interface implementations.
     */
    public List<ImplementedInterface> getImplementations();
    
    /**
     * Finds a specific implementation of the interface using a given predicate filter.
     * 
     * @param filter A predicate used to test each implementation.
     * @return The implementation that matches the filter, or null if none found.
     */
    public default ImplementedInterface findImplementation(Predicate<ImplementedInterface> filter) {
        List<ImplementedInterface> impls = getImplementations();
        ImplementedInterface riface = null;
        if (impls != null) {
            for (ImplementedInterface iface : impls) {
                if (filter.test(iface)) {
                    riface = iface;
                }
            }
        }
        return riface;
    }
    
    /**
     * Maps each implementation to a new form using a specified function.
     * 
     * @param <T> The type of the objects resulting from the mapping function.
     * @param function A function to apply to each implementation.
     * @return A list of mapped objects.
     */
    public default <T> List<T> map(Function<ImplementedInterface, T> function) {
        return new ArrayList<T>(getImplementations().stream().map(function).toList());
    }
    
    /**
     * Retrieves a code value for a given implementation.
     * 
     * @param x The implementation instance.
     * @return The code value of the implementation.
     */
    public String getCodeValue(ImplementedInterface x);
    
    /**
     * Finds all code values for the current implementations.
     * 
     * @return A list of code values.
     */
    public default List<String> findAllCodes() {
        List<String> codes = new ArrayList<String>();
        List<ImplementedInterface> impls = getImplementations();
        ImplementedInterface riface = null;
        if (impls != null) {
            for (ImplementedInterface iface : impls) {
                codes.add(getCodeValue(riface));
            }
        }
        return codes;
    }
    
    /**
     * Finds a specific implementation by its code value.
     * 
     * @param code The code to search by.
     * @return The implementation with the matching code, or null if not found.
     */
    public default ImplementedInterface findByCode(String code) {
        return findImplementation((x) -> {
            return getCodeValue(x) != null && code != null && getCodeValue(x).equals(code);
        });
    }
    
    /**
     * Adds a new implementation to the repository.
     * 
     * @param iface The implementation instance to add.
     */
    public void addImplementation(ImplementedInterface iface);
}