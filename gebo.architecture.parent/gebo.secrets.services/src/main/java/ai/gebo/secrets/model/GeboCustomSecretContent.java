/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

/**
 * Gebo.ai comment agent
 * This class represents the custom secret content for Gebo.
 * It extends {@link AbstractGeboSecretContent} and provides additional fields
 * specific to custom secret content, including a description, content, and content type.
 */
public class GeboCustomSecretContent extends AbstractGeboSecretContent {
    // Description of the custom content
	private String customContentDescription = null;
	
    // The actual content stored in this secret
	private String content = null;
	
    // Type of the content, such as text, JSON, etc.
	private String contentType = null;

    /**
     * Default constructor for GeboCustomSecretContent.
     */
	public GeboCustomSecretContent() {

	}

    /**
     * Returns the type of the secret, which is always CUSTOM_SECRET.
     *
     * @return the type of the secret as {@link GeboSecretType}
     */
	@Override
	public GeboSecretType type() {
		return GeboSecretType.CUSTOM_SECRET;
	}

    /**
     * Gets the content of the secret.
     *
     * @return the content as a String
     */
	public String getContent() {
		return content;
	}

    /**
     * Sets the content of the secret.
     *
     * @param content the content to set
     */
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * Gets the content type of the secret.
     *
     * @return the content type as a String
     */
	public String getContentType() {
		return contentType;
	}

    /**
     * Sets the content type of the secret.
     *
     * @param contentType the content type to set
     */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

    /**
     * Gets the description of the custom content.
     *
     * @return the description of the custom content as a String
     */
	public String getCustomContentDescription() {
		return customContentDescription;
	}

    /**
     * Sets the description of the custom content.
     *
     * @param customContentDescription the description to set
     */
	public void setCustomContentDescription(String customContentDescription) {
		this.customContentDescription = customContentDescription;
	}

}