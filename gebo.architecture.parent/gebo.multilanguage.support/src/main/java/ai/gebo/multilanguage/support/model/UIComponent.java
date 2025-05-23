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

import jakarta.validation.constraints.NotNull;

public class UIComponent implements LanguageResource {
    
    // Gebo.ai comment agent
    // List of subcomponents contained within this UIComponent
	private List<UIComponent> subcomponents = new ArrayList<UIComponent>();
	
    // Language code associated with this UIComponent
	@NotNull
	private String langCode = null;
	
    // Unique identifier for this UIComponent
	@NotNull
	private String id = null;
	
    // Label for this UIComponent, marked as translatable
	@Translable
	private String label = null;
	
    // Title for this UIComponent, marked as translatable
	@Translable
	private String title = null;
	
    // Placeholder text for this UIComponent, marked as translatable
	@Translable
	private String placeholder = null;

    /**
     * Gets the language code.
     * @return the language code
     */
	public String getLangCode() {
		return langCode;
	}

    /**
     * Sets the language code.
     * @param langCode the language code to set
     */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

    /**
     * Gets the identifier of the UIComponent.
     * @return the identifier
     */
	public String getId() {
		return id;
	}

    /**
     * Sets the identifier of the UIComponent.
     * @param id the identifier to set
     */
	public void setId(String id) {
		this.id = id;
	}

    /**
     * Gets the label of the UIComponent.
     * @return the label
     */
	public String getLabel() {
		return label;
	}

    /**
     * Sets the label of the UIComponent.
     * @param label the label to set
     */
	public void setLabel(String label) {
		this.label = label;
	}

    /**
     * Gets the title of the UIComponent.
     * @return the title
     */
	public String getTitle() {
		return title;
	}

    /**
     * Sets the title of the UIComponent.
     * @param title the title to set
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Gets the placeholder of the UIComponent.
     * @return the placeholder
     */
	public String getPlaceholder() {
		return placeholder;
	}

    /**
     * Sets the placeholder of the UIComponent.
     * @param placeholder the placeholder text to set
     */
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

    /**
     * Gets the list of subcomponents.
     * @return the subcomponents list
     */
	public List<UIComponent> getSubcomponents() {
		return subcomponents;
	}

    /**
     * Sets the list of subcomponents.
     * @param subcomponents the list of subcomponents to set
     */
	public void setSubcomponents(List<UIComponent> subcomponents) {
		this.subcomponents = subcomponents;
	}
	
}