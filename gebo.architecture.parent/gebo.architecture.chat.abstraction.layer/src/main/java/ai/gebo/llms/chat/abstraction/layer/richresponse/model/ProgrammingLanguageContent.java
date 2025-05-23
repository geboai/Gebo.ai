/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.richresponse.model;

/**
 * Gebo.ai comment agent
 *
 * Represents content related to a programming language which includes 
 * the file name, language type, and the actual software code.
 */
public class ProgrammingLanguageContent extends RichresponseChild {
    // Name of the file
    public String fileName = null;
    // Programming language of the source code
    public String language = null;
    // The actual software code
    public String softwareCode = null;

    /**
     * Default constructor for ProgrammingLanguageContent.
     */
    public ProgrammingLanguageContent() {

    }

    /**
     * Gets the file name.
     * 
     * @return the file name as a String.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     * 
     * @param fileName the name of the file to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the programming language.
     * 
     * @return the language as a String.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the programming language.
     * 
     * @param language the programming language to set.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the software code.
     * 
     * @return the software code as a String.
     */
    public String getSoftwareCode() {
        return softwareCode;
    }

    /**
     * Sets the software code.
     * 
     * @param softwareCode the software code to set.
     */
    public void setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
    }
}