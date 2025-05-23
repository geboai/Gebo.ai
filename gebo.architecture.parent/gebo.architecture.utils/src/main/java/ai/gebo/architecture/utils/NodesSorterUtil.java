/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Utility class providing sorting functionality for nodes.
 * 
 * Gebo.ai comment agent
 */
public class NodesSorterUtil {

    /**
     * Sorts a collection of objects using a provided extraction function. Objects 
     * that produce a null key are collected first, followed by keyed objects sorted 
     * based on their keys.
     *
     * @param <T> the type of the key used for sorting
     * @param <ObjectType> the type of the objects to be sorted
     * @param extractionFunction a function to extract the sort key from the objects
     * @param objects the collection of objects to sort
     * @return a sorted list of objects, with objects having null keys appearing first
     */
    public static <T, ObjectType> List<ObjectType> sort(Function<ObjectType, T> extractionFunction,
            Collection<ObjectType> objects) {
        // List to collect objects that have no keys
        final ArrayList<ObjectType> nokeys = new ArrayList<ObjectType>();
        // TreeMap to store objects by their keys
        final TreeMap<T, List<ObjectType>> sorter = new TreeMap<T, List<ObjectType>>();
        
        // Stream through each object in the collection
        objects.stream().forEach(x -> {
            // Extract the key using the provided function
            T key = extractionFunction.apply(x);
            
            if (key == null) {
                // Add object to 'nokeys' if key is null
                nokeys.add(x);
            } else {
                // If key is not null, initialize a new list for that key if needed
                if (!sorter.containsKey(key)) {
                    sorter.put(key, new ArrayList<ObjectType>());
                }
                // Add object to the appropriate list based on its key
                sorter.get(key).add(x);
            }
        });
        
        // Collect all sorted lists from the TreeMap
        Collection<List<ObjectType>> values = sorter.values();
        // Append all lists to 'nokeys' maintaining their order
        for (List<ObjectType> list : values) {
            nokeys.addAll(list);
        }
        
        // Return the final sorted list
        return nokeys;
    }
}