package ai.gebo.secrets.model;

import java.io.Serializable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Wrapper class to encapsulate secret content along with its metadata such as
 * context code and description.
 *
 * @param <SecretContentType> the type of the secret content
 */
public class SecretWrapper<SecretContentType extends AbstractGeboSecretContent> implements Serializable {
	@NotNull
	private String contextCode = null;
	@NotNull
	private String description = null;
	@NotNull
	@Valid
	private SecretContentType secretContent = null;

	/**
	 * Returns the context code associated with the secret.
	 *
	 * @return a string representing the context code
	 */
	public String getContextCode() {
		return contextCode;
	}

	/**
	 * Sets the context code for the secret.
	 *
	 * @param contextCode the context code to set
	 */
	public void setContextCode(String contextCode) {
		this.contextCode = contextCode;
	}

	/**
	 * Returns the description of the secret.
	 *
	 * @return a string representing the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description for the secret.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the secret content.
	 *
	 * @return the secret content
	 */
	public SecretContentType getSecretContent() {
		return secretContent;
	}

	/**
	 * Sets the secret content.
	 *
	 * @param secretContent the secret content to set
	 */
	public void setSecretContent(SecretContentType secretContent) {
		this.secretContent = secretContent;
	}
}