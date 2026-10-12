package ai.gebo.secrets.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.secrets.services.IGSecretsAdditionalProvider;
import ai.gebo.secrets.services.IGSecretsAdditionalProviderRepositoryPattern;

/**
 * Collects every {@link IGSecretsAdditionalProvider} on the classpath.
 *
 * The injected list is optional: the community platform binds no provider at
 * all, so the repository is legitimately empty and the secrets access service
 * simply falls straight through to its predefined resolution chain.
 */
@Service
public class GSecretsAdditionalProviderRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGSecretsAdditionalProvider>
		implements IGSecretsAdditionalProviderRepositoryPattern {

	public GSecretsAdditionalProviderRepositoryPatternImpl(
			@Autowired(required = false) List<IGSecretsAdditionalProvider> implementations) {
		super(implementations);
	}

	@Override
	public String getCodeValue(IGSecretsAdditionalProvider x) {
		return x.getProviderId();
	}
}
