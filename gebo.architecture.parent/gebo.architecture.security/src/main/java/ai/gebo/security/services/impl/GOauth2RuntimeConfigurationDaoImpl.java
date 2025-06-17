package ai.gebo.security.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.security.config.GeboAppSecurityProperties;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.repository.Oauth2RuntimeConfigurationRepository;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import lombok.AllArgsConstructor;

@Service
public class GOauth2RuntimeConfigurationDaoImpl extends GAbstractRuntimeConfigurationDao<Oauth2RuntimeConfiguration>
		implements IGOauth2RuntimeConfigurationDao {
	private final Oauth2RuntimeConfigurationRepository repo;

	@AllArgsConstructor
	static class Oauth2RuntimeConfigurationSource implements IGDynamicConfigurationSource<Oauth2RuntimeConfiguration> {
		private final Oauth2RuntimeConfigurationRepository repo;

		@Override
		public List<Oauth2RuntimeConfiguration> getConfigurations() {

			return repo.findAll();
		}

		@Override
		public Oauth2RuntimeConfiguration findByCode(String code) {
			Optional<Oauth2RuntimeConfiguration> data = repo.findById(code);
			return data.isPresent() ? data.get() : null;
		}

	}

	public GOauth2RuntimeConfigurationDaoImpl(GeboAppSecurityProperties staticConfigs,
			Oauth2RuntimeConfigurationRepository repo) {
		super(makeReadOnly(staticConfigs.getOauth2configs()), new Oauth2RuntimeConfigurationSource(repo));
		this.repo = repo;

	}

	private static List<Oauth2RuntimeConfiguration> makeReadOnly(List<Oauth2RuntimeConfiguration> oauth2configs) {
		if (oauth2configs != null) {
			for (Oauth2RuntimeConfiguration config : oauth2configs) {
				config.setReadOnly(true);
			}
		}
		return oauth2configs;
	}

	@Override
	public Oauth2RuntimeConfiguration findByCode(String code) {
		Oauth2RuntimeConfiguration data = dynamic.findByCode(code);
		if (data != null)
			return data;
		Optional<Oauth2RuntimeConfiguration> entry = staticConfigs.stream()
				.filter(x -> x.getRegistrationId() != null && x.getRegistrationId().equals(code)).findFirst();
		return entry.isPresent() ? entry.get() : null;
	}

	@Override
	public void insert(Oauth2RuntimeConfiguration configuration) {
		if (configuration.getReadOnly() != null && configuration.getReadOnly()) {
			throw new RuntimeException("The configuration with id:" + configuration.getRegistrationId()
					+ " cannot be saved because is readonly");
		}
		repo.insert(configuration);
	}

	@Override
	public void delete(Oauth2RuntimeConfiguration oauth2RuntimeConfiguration) {
		if (oauth2RuntimeConfiguration.getReadOnly() != null && oauth2RuntimeConfiguration.getReadOnly()) {
			throw new RuntimeException("The configuration with id:" + oauth2RuntimeConfiguration.getRegistrationId()
					+ " cannot be saved because is readonly");
		}
		repo.delete(oauth2RuntimeConfiguration);
	}

	@Override
	public List<Oauth2RuntimeConfiguration> findByProvider(AuthProvider provider) {

		return findListByPredicate(x -> x.getProvider() != null && x.getProvider() == provider);
	}

	@Override
	public List<Oauth2RuntimeConfiguration> findByProviderAndConfigurationTypesContains(AuthProvider provider,
			Oauth2ConfigurationType type) {
		return findListByPredicate(x -> x.getProvider() != null && x.getProvider() == provider
				&& x.getConfigurationTypes() != null && x.getConfigurationTypes().contains(type));

	}

	@Override
	public List<Oauth2RuntimeConfiguration> findByConfigurationTypesContains(Oauth2ConfigurationType authentication) {
		return findListByPredicate(
				x -> x.getConfigurationTypes() != null && x.getConfigurationTypes().contains(authentication));
	}

	@Override
	public void save(Oauth2RuntimeConfiguration data) {
		if (data.getReadOnly() != null && data.getReadOnly()) {
			throw new RuntimeException("The configuration with id:" + data.getRegistrationId()
					+ " cannot be saved because is readonly and comes from spring boot configuration");

		}
		repo.save(data);

	}

}
