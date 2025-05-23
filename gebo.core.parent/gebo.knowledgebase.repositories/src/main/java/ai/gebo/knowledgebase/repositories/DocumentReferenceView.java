/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import java.util.Date;

/**
 * AI generated comments
 * Interface representing a view of a document reference.
 * Provides methods to retrieve various attributes of a document reference.
 */
public interface DocumentReferenceView {

    /**
     * Gets the extension of the document.
     * @return the document's extension as a String.
     */
    public String getExtension();

    /**
     * Gets the content type of the document.
     * @return the document's content type as a String.
     */
    public String getContentType();

    /**
     * Gets the root knowledge base code associated with the document.
     * @return the root knowledge base code as a String.
     */
    public String getRootKnowledgebaseCode();

    /**
     * Gets the parent project code of the document.
     * @return the parent project code as a String.
     */
    public String getParentProjectCode();

    /**
     * Gets the parent virtual folder code in which the document resides.
     * @return the parent virtual folder code as a String.
     */
    public String getParentVirtualFolderCode();

    /**
     * Gets the name of the document.
     * @return the name of the document as a String.
     */
    public String getName();

    /**
     * Checks if the document is marked as deleted.
     * @return true if the document is deleted, false otherwise.
     */
    public Boolean getDeleted();

    /**
     * Gets the ID of the messaging module associated with the document.
     * @return the messaging module ID as a String.
     */
    public String getMessagingModuleId();

    /**
     * Gets the relative path of the document.
     * @return the relative path as a String.
     */
    public String getRelativePath();

    /**
     * Gets the unique code identifier for the document.
     * @return the document's code as a String.
     */
    public String getCode();

    /**
     * Gets the description of the document.
     * @return the document's description as a String.
     */
    public String getDescription();

    /**
     * Gets the creation date of the document.
     * @return the creation date as a Date object.
     */
    public Date getCreationDate();

    /**
     * Gets the modification date of the document.
     * @return the modification date as a Date object.
     */
    public Date getModificationDate();
}