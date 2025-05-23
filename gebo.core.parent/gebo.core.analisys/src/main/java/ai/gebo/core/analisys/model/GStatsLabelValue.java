/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.model;

/**
 * Represents a statistical label and its associated value.
 * Provides methods to get and set the label and value.
 * 
 * AI generated comments
 */
public class GStatsLabelValue {
	private String label = null; // The label associated with the value
	private Number value = null; // The numerical value associated with the label

	/**
	 * Retrieves the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Retrieves the value.
	 * 
	 * @return the value
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value the value to set
	 */
	public void setValue(Number value) {
		this.value = value;
	}

	/**
	 * Default constructor initializing empty label and value.
	 */
	public GStatsLabelValue() {
	}

	/**
	 * Constructs a GStatsLabelValue with specified label and value.
	 * 
	 * @param s the label
	 * @param n the value
	 */
	public GStatsLabelValue(String s, Number n) {
		this.label = s;
		this.value = n;
	}
	
}