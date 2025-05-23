/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.document.model;

/**
 * Represents a cataloging label with a code, label, and criteria for cataloging. 
 * Also supports hierarchical cataloging by having a parent label.
 * AI generated comments
 */
public class CatalogingLabel {
    
    // Code associated with the cataloging label
    private String code = null;
    
    // Human-readable label for the cataloging item
    private String label = null;

    // Criteria defining how cataloging should be applied
    private String catalogingCriteria = null;

    // Reference to a parent CatalogingLabel for hierarchical relationships
    private CatalogingLabel parent = null;

    /**
     * Default constructor.
     */
    public CatalogingLabel() {

    }

    /**
     * Gets the code of the cataloging label.
     * 
     * @return the code as a String
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code for the cataloging label.
     * 
     * @param code the new code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the label of the cataloging item.
     * 
     * @return the label as a String
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label for the cataloging item.
     * 
     * @param label the new label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the parent cataloging label in the hierarchy.
     * 
     * @return the parent CatalogingLabel
     */
    public CatalogingLabel getParent() {
        return parent;
    }

    /**
     * Sets the parent cataloging label for this label.
     * 
     * @param parent the CatalogingLabel to set as the parent
     */
    public void setParent(CatalogingLabel parent) {
        this.parent = parent;
    }

    /**
     * Gets the criteria for cataloging.
     * 
     * @return the cataloging criteria as a String
     */
    public String getCatalogingCriteria() {
        return catalogingCriteria;
    }

    /**
     * Sets the criteria for how this label should be cataloged.
     * 
     * @param catalogingCriteria the new cataloging criteria to set
     */
    public void setCatalogingCriteria(String catalogingCriteria) {
        this.catalogingCriteria = catalogingCriteria;
    }

}