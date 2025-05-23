/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a lookup entry derived from {@code GBaseObject}.
 * It provides utility functions to instantiate and convert objects of type {@code GBaseObject} into {@code GLookupEntry}.
 * <p>AI generated comments</p>
 */
public class GLookupEntry extends GBaseObject {

    /**
     * Default constructor for {@code GLookupEntry}.
     */
    public GLookupEntry() {

    }

    /**
     * Converts a {@code GBaseObject} into a {@code GLookupEntry}.
     *
     * @param entry the {@code GBaseObject} to be converted
     * @return a {@code GLookupEntry} object if {@code entry} is not null; otherwise, {@code null}
     */
    public static <T extends GBaseObject> GLookupEntry of(T entry) {
        if (entry == null)
            return null;
        GLookupEntry _entry = new GLookupEntry();
        _entry.setCode(entry.getCode());
        _entry.setDescription(entry.getDescription());
        return _entry;
    }

    /**
     * Converts a list of {@code GBaseObject} entries into a list of {@code GLookupEntry}.
     *
     * @param entries the list of {@code GBaseObject} entries to be converted
     * @return a list of {@code GLookupEntry} objects if {@code entries} is not null; otherwise, {@code null}
     */
    public static <T extends GBaseObject> List<GLookupEntry> of(List<T> entries) {
        if (entries == null)
            return null;
        List<GLookupEntry> _entries = new ArrayList<GLookupEntry>();
        for (T t : entries) {
            _entries.add(of(t)); // Convert each GBaseObject in the list to a GLookupEntry
        }
        return _entries;
    }

}