package ai.gebo.secrets.model;

/**
 * Inner class to store key information about a secret.
 */
public class SecretInfo {

	/**
	 * Default constructor for creating an empty SecretInfo object.
	 */
	public SecretInfo() {
	}

	/**
	 * Constructor for creating a SecretInfo object based on a GeboSecret instance.
	 * 
	 * @param secret a GeboSecret object containing the details of the secret.
	 */
	public SecretInfo(GeboSecret secret) {
		this.code = secret.getCode();
		this.description = secret.getDescription();
		this.secretType = secret.getSecretType();
		this.contextCode = secret.getContextCode();
	}

	/**
	 * Constructor for the metadata of a configuration-declared secret, which has no
	 * {@link GeboSecret} record behind it.
	 *
	 * @param code        the unique code of the secret
	 * @param description the description of the secret
	 * @param secretType  the type of the secret
	 * @param contextCode the context the secret belongs to
	 * @param readOnly    whether the secret may only be read
	 */
	public SecretInfo(String code, String description, GeboSecretType secretType, String contextCode,
			Boolean readOnly) {
		this.code = code;
		this.description = description;
		this.secretType = secretType;
		this.contextCode = contextCode;
		this.readOnly = readOnly;
	}

	// Unique identifier for the secret.
	private String code = null;

	// Description for the secret.
	private String description = null;

	// Type of the secret.
	private GeboSecretType secretType = null;

	// Context code associated with the secret.
	private String contextCode = null;

	/**
	 * {@code true} when the secret is declared in the configuration
	 * ({@code ai.gebo.secrets.config.*}) and therefore cannot be updated or
	 * deleted; {@code null} for an ordinary stored secret. Mirrors
	 * {@code AbstractGeboSecretContent#getReadOnly()} so that a client - the admin
	 * UI in particular - can tell the two apart without fetching the content.
	 */
	private Boolean readOnly = null;

	/**
	 * Retrieves the unique code of the secret.
	 * 
	 * @return the unique code of the secret.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code of the secret.
	 * 
	 * @param code the unique code to be set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Retrieves the description of the secret.
	 * 
	 * @return the description of the secret.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the secret.
	 * 
	 * @param description the description to be set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieves the type of the secret.
	 * 
	 * @return the secret type.
	 */
	public GeboSecretType getSecretType() {
		return secretType;
	}

	/**
	 * Sets the type of the secret.
	 * 
	 * @param secretType the secret type to be set.
	 */
	public void setSecretType(GeboSecretType secretType) {
		this.secretType = secretType;
	}

	public String getContextCode() {
		return contextCode;
	}

	public void setContextCode(String contextCode) {
		this.contextCode = contextCode;
	}

	/**
	 * Whether this secret is configuration-declared and hence read-only.
	 *
	 * @return {@code true} for a read-only secret, {@code null} otherwise.
	 */
	public Boolean getReadOnly() {
		return readOnly;
	}

	/**
	 * Sets the read-only marker of the secret.
	 *
	 * @param readOnly the marker to set.
	 */
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
}