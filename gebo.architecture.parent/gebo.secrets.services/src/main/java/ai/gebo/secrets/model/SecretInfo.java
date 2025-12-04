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
	}

	// Unique identifier for the secret.
	private String code = null;

	// Description for the secret.
	private String description = null;

	// Type of the secret.
	private GeboSecretType secretType = null;

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
}