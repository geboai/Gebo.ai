/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.multilanguage.support.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.multilanguage.support.config.RCFolderConfig;
import ai.gebo.multilanguage.support.model.LanguageCollection;
import ai.gebo.multilanguage.support.model.UIComponent;

/**
 * AI generated comments
 * Service for managing multilingual resources, specifically UI components. 
 * Handles loading, caching, and updating of language-specific resources.
 */
@Component
@Scope("singleton")
public class MultiLanguageService {

    // ObjectMapper for JSON parsing
    private static final ObjectMapper mapper = new ObjectMapper();
    
    // Prefix used for resource file paths
    public static final String MLANGUAGE_RESOURCES_PREFIX = "mlanguage/";

    /**
     * Class representing language-specific resources for UI components.
     */
    public static class UILangResources extends LanguageCollection<UIComponent> {
        
        /**
         * Default constructor for UILangResources with 'en' as the default language code.
         */
        public UILangResources() {
            setLangCode("en");
            setId("UIResources");
        }

        /**
         * Parameterized constructor for UILangResources.
         * @param langCode Language code for the resources.
         */
        public UILangResources(String langCode) {
            setLangCode(langCode);
            setId("UIResources");
        }
    }

    // Configuration for resource folder path and mode
    @Autowired
    RCFolderConfig config;
    
    // Cache to store language-specific resources
    static Map<String, UILangResources> cache = new HashMap<String, UILangResources>();

    /**
     * Retrieves the UIComponent labels based on the component ID and language code.
     * @param id The ID of the UIComponent.
     * @param langCode The language code.
     * @return The UIComponent matching the criteria; otherwise null.
     * @throws IOException if an error occurs during reading.
     */
    public UIComponent getUIComponentLabels(String id, String langCode) throws IOException {
        LanguageCollection<UIComponent> languageCollection = getCollection(langCode);
        Optional<UIComponent> found = languageCollection.getResources().stream()
                .filter(x -> x.getId() != null && x.getId().equals(id)).findAny();
        return found.isPresent() ? found.get() : null;
    }

    /**
     * Retrieves all UIComponent resources for a specified language code.
     * @param langCode The language code.
     * @return A list of all UIComponents for the language.
     * @throws IOException if an error occurs during reading.
     */
    public List<UIComponent> getAllResources(String langCode) throws IOException {
        return getCollection(langCode).getResources();
    }

    /**
     * Updates or adds a UIComponent in the resources.
     * @param component The UIComponent to update/add.
     * @return The updated UIComponent.
     * @throws IOException if an error occurs during update.
     */
    public synchronized UIComponent update(UIComponent component) throws IOException {
        if (config.isClassPathMode())
            return component;

        UILangResources resources = getCollection(component.getLangCode());
        boolean replaced = false;

        // Check and replace the existing component
        for (int i = 0; i < resources.getResources().size(); i++) {
            UIComponent ui = resources.getResources().get(i);
            if (ui.getId().equals(component.getId())) {
                resources.getResources().set(i, component);
                replaced = true;
                break;
            }
        }
        
        // Add new component if it was not replaced
        if (!replaced) {
            resources.getResources().add(component);
        }

        // Determine file path and write updated resources
        String folder = (config.getFolderPrefix() != null ? config.getFolderPrefix() : ".");
        Path path = Path.of(folder, component.getLangCode() + ".json");
        mapper.writeValue(path.toFile(), resources);

        // Update the cache
        cache.put(component.getLangCode(), resources);
        return component;
    }

    /**
     * Retrieves language resources from the cache or loads them from file.
     * @param langCode The language code.
     * @return The UILangResources for the specified language.
     * @throws IOException if an error occurs while reading.
     */
    private UILangResources getCollection(String langCode) throws IOException {
        if (!cache.containsKey(langCode)) {
            InputStream is = null;
            try {
                // Load from classpath if configured
                if (config.isClassPathMode()) {
                    is = getClass().getResourceAsStream(MLANGUAGE_RESOURCES_PREFIX + langCode + ".json");
                } else {
                    // Load from file system
                    String prefix = config.getFolderPrefix();
                    if (prefix == null) {
                        prefix = ".";
                    }
                    Path path = Path.of(prefix, langCode + ".json");
                    if (path.toFile().exists()) {
                        is = new FileInputStream(path.toFile());
                    }
                }
                
                // Read and cache resources
                if (is != null) {
                    cache.put(langCode, mapper.readValue(is, UILangResources.class));
                }
            } finally {
                // Ensure stream is closed
                if (is != null) {
                    try {
                        is.close();
                    } catch (Throwable th) {
                        // Ignore close errors
                    }
                }
            }
        }
        
        // Return cached resources or a new instance if not found
        return cache.containsKey(langCode) ? cache.get(langCode) : new UILangResources(langCode);
    }
}