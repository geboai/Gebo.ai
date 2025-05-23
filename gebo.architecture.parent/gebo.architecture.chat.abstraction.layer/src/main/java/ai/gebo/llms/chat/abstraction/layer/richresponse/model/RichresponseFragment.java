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
 * Represents a rich response fragment that can contain various types of content such as programming language content,
 * table content, rich HTML text, and preformatted text.
 * 
 * Gebo.ai comment agent
 */
public class RichresponseFragment {

    // Enum indicating the type of fragment
    private RichresponseFragmentType fragmentType = null;

    // Content specific to programming languages
    private ProgrammingLanguageContent programmingLanguageContent = null;

    // Content representing a table structure
    private TableContent tableContent = null;

    // Content containing rich HTML text
    private RichHtmlText richHtmlText = null;

    // Content containing preformatted text
    private PreformattedText preformattedText = null;

    /**
     * Gets the fragment type.
     *
     * @return the current fragment type
     */
    public RichresponseFragmentType getFragmentType() {
        return fragmentType;
    }

    /**
     * Sets the fragment type.
     *
     * @param fragmentType the desired fragment type
     */
    public void setFragmentType(RichresponseFragmentType fragmentType) {
        this.fragmentType = fragmentType;
    }

    /**
     * Gets the programming language content.
     *
     * @return the programming language content of the fragment
     */
    public ProgrammingLanguageContent getProgrammingLanguageContent() {
        return programmingLanguageContent;
    }

    /**
     * Sets the programming language content.
     *
     * @param programmingLanguageContent the content to be set for programming languages
     */
    public void setProgrammingLanguageContent(ProgrammingLanguageContent programmingLanguageContent) {
        this.programmingLanguageContent = programmingLanguageContent;
    }

    /**
     * Gets the table content.
     *
     * @return the table content of the fragment
     */
    public TableContent getTableContent() {
        return tableContent;
    }

    /**
     * Sets the table content.
     *
     * @param tableContent the content to be set for the table
     */
    public void setTableContent(TableContent tableContent) {
        this.tableContent = tableContent;
    }

    /**
     * Gets the rich HTML text.
     *
     * @return the rich HTML text of the fragment
     */
    public RichHtmlText getRichHtmlText() {
        return richHtmlText;
    }

    /**
     * Sets the rich HTML text.
     *
     * @param richHtmlText the content to be set for the rich HTML text
     */
    public void setRichHtmlText(RichHtmlText richHtmlText) {
        this.richHtmlText = richHtmlText;
    }

    /**
     * Gets the preformatted text.
     *
     * @return the preformatted text of the fragment
     */
    public PreformattedText getPreformattedText() {
        return preformattedText;
    }

    /**
     * Sets the preformatted text.
     *
     * @param preformattedText the content to be set for preformatted text
     */
    public void setPreformattedText(PreformattedText preformattedText) {
        this.preformattedText = preformattedText;
    }
}