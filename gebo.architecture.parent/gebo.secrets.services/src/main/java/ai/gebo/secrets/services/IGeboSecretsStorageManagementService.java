package ai.gebo.secrets.services;

import ai.gebo.crypting.services.GeboCryptSecretException;

public interface IGeboSecretsStorageManagementService {
	boolean isMigrationToExternalPossible();

	boolean isMigrationToExternalDone();

	void migrateToExternalStorage() throws GeboCryptSecretException;

	void migrateFromExternalStorage() throws GeboCryptSecretException;
}
