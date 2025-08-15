package ai.gebo.security.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.repository.Oauth2RuntimeConfigurationRepository;
import ai.gebo.security.services.IGOauth2ProvidersLibraryDao;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import lombok.AllArgsConstructor;

@Service
public class GOauth2RuntimeConfigurationDaoImpl extends GAbstractRuntimeConfigurationDao<Oauth2RuntimeConfiguration>
		implements IGOauth2RuntimeConfigurationDao {
	private final Oauth2RuntimeConfigurationRepository repo;
	private static final Logger LOGGER = LoggerFactory.getLogger(GOauth2RuntimeConfigurationDaoImpl.class);
	private final IGOauth2ProvidersLibraryDao providersLibraryDao;

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

	public GOauth2RuntimeConfigurationDaoImpl(GeboSecurityConfig staticConfigs,
			Oauth2RuntimeConfigurationRepository repo, IGOauth2ProvidersLibraryDao providersLibraryDao) {
		super(makeReadOnlyAndValidate(staticConfigs.getOauth2configs()), new Oauth2RuntimeConfigurationSource(repo));
		this.repo = repo;
		this.providersLibraryDao = providersLibraryDao;

	}

	private static List<Oauth2RuntimeConfiguration> makeReadOnlyAndValidate(
			List<Oauth2RuntimeConfiguration> oauth2configs) {
		if (oauth2configs != null) {
			for (Oauth2RuntimeConfiguration config : oauth2configs) {
				config.setReadOnly(true);
				if (config.getClient() == null || config.getClient().getClientId() == null
						|| config.getClient().getSecret() == null) {
					final String msg = "The Oauth2 configuration for registrationId:" + config.getRegistrationId()
							+ " does not have a full oauth2 client plain configuration, shutting down, please correct yaml ";
					LOGGER.error(msg);
					throw new IllegalStateException(msg);
				}
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
		return entry.isPresent() ? completeProvider(entry.get()) : null;
	}

	@Override
	public void insert(Oauth2RuntimeConfiguration configuration) {
		if (configuration.getReadOnly() != null && configuration.getReadOnly()) {
			throw new RuntimeException("The configuration with id:" + configuration.getRegistrationId()
					+ " cannot be saved because is readonly");
		}
		// with provider in the library i do not save it
		if (configuration.getProvider() != AuthProvider.oauth2_generic
				&& providersLibraryDao.findByCode(configuration.getProvider().name()) != null) {
			configuration = new Oauth2RuntimeConfiguration(configuration);
			configuration.setProviderConfig(null);
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

	@Override
	public Oauth2RuntimeConfiguration findByPredicate(Predicate<Oauth2RuntimeConfiguration> predicate) {

		return completeProvider(super.findByPredicate(predicate));
	}

	private Oauth2RuntimeConfiguration completeProvider(Oauth2RuntimeConfiguration byPredicate) {
		Oauth2RuntimeConfiguration runtimeConfiguration = byPredicate;
		if (byPredicate.getProviderConfig() == null
				&& runtimeConfiguration.getProvider() != AuthProvider.oauth2_generic) {
			Oauth2ProviderConfig providerConfig = this.providersLibraryDao
					.findByCode(runtimeConfiguration.getProvider().name());
			runtimeConfiguration = new Oauth2RuntimeConfiguration(byPredicate);
			runtimeConfiguration.setProviderConfig(providerConfig);

		}
		return runtimeConfiguration;
	}

	@Override
	public List<Oauth2RuntimeConfiguration> findListByPredicate(Predicate<Oauth2RuntimeConfiguration> predicate) {

		return completeProviders(super.findListByPredicate(predicate));
	}

	private List<Oauth2RuntimeConfiguration> completeProviders(List<Oauth2RuntimeConfiguration> listByPredicate) {

		return listByPredicate != null ? listByPredicate.stream().map(x -> this.completeProvider(x)).toList()
				: List.of();
	}

}
