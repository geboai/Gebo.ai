/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.multilanguage.support.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * A class representing a source for multiple languages.
 */
public class LanguagesSource {

    /**
     * Inner class representing a language with its code and description.
     */
    public static final class Language {
        @NotNull
        private String langCode; // The language code (e.g., "en" for English)
        @NotNull
        private String description; // The language description (e.g., "English")

        /**
         * Default constructor for creating a Language instance.
         */
        public Language() {

        }

        /**
         * Constructor for creating a Language instance with specified language code and description.
         * @param langCode the language code
         * @param descr the language description
         */
        public Language(String langCode, String descr) {
            this.langCode = langCode;
            this.description = descr;
        }

        /**
         * Gets the language code.
         * @return the language code
         */
        public String getLangCode() {
            return langCode;
        }

        /**
         * Sets the language code.
         * @param langCode the language code
         */
        public void setLangCode(String langCode) {
            this.langCode = langCode;
        }

        /**
         * Gets the language description.
         * @return the language description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the language description.
         * @param description the language description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Returns a string representation of the Language instance, including both code and description.
         * @return the string representation of the language
         */
        @Override
        public String toString() {
            return "{langCode:\"" + langCode + "\",description:\"" + description + "\"}";
        }
    }

    // Uncomment the following line to set a default language (currently commented out)
    // public final static Language DEFAULT_LANGUAGE = new Language(Locale.ENGLISH.getLanguage(), "English");

    /**
     * Retrieves all available languages as a list of Language objects.
     * @return a list of Language objects containing all ISO-defined languages
     */
    public final static List<Language> getAllLanguages() {
        final List<Language> langs = new ArrayList<LanguagesSource.Language>();
        String isos[] = Locale.getISOLanguages();
        for (String langCode : isos) {
            Language lang = new Language(langCode, Locale.forLanguageTag(langCode).getDisplayLanguage());
            langs.add(lang);
        }
        return langs;
    }
}