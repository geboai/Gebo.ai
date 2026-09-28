package ai.gebo.secrets.services;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Repository of every {@link IGSecretsAdditionalProvider} bound in the
 * application. Cycled in order by the secrets access service before its own
 * predefined resolution chain.
 */
public interface IGSecretsAdditionalProviderRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGSecretsAdditionalProvider> {
}
