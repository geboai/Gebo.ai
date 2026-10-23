package ai.gebo.security.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
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
	private final IGeboSecretsAccessService secretsService;

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
			Oauth2RuntimeConfigurationRepository repo, IGOauth2ProvidersLibraryDao providersLibraryDao,
			IGeboSecretsAccessService secretsService) {
		super(makeReadOnlyAndValidate(staticConfigs.getOauth2configs()), new Oauth2RuntimeConfigurationSource(repo));
		this.repo = repo;
		this.providersLibraryDao = providersLibraryDao;
		this.secretsService = secretsService;

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
				if (config.getClient().getProviderName()==null && config.getProvider()!=null) {
					config.getClient().setProviderName(config.getProvider().name());
				}
			}
		}
		return oauth2configs;
	}

	@Override
	public Oauth2RuntimeConfiguration findByCode(String code) {
		Oauth2RuntimeConfiguration data = dynamic.findByCode(code);
		if (data != null)
			return completeProvider(data);
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
		repo.insert(forStorage(configuration));
	}

	/**
	 * What may be written to the store, out of a configuration that may well be one
	 * of the resolved copies handed out by the reads below.
	 *
	 * <p>
	 * Two things are deliberately not persisted. The endpoints of a provider that has
	 * a library entry: they belong to the library, shared and templated, and freezing
	 * a resolved copy of them into the document would quietly pin the configuration
	 * to today's URLs. And the client: it lives in the secret store, encrypted, under
	 * {@code clientSecretId}, so writing it here would leave a second, clear copy of
	 * the client secret in an ordinary document.
	 * </p>
	 */
	private Oauth2RuntimeConfiguration forStorage(Oauth2RuntimeConfiguration configuration) {
		Oauth2RuntimeConfiguration storable = new Oauth2RuntimeConfiguration(configuration);
		storable.setClient(null);
		if (configuration.getProvider() != AuthProvider.oauth2_generic
				&& providersLibraryDao.findByCode(configuration.getProvider().name()) != null) {
			storable.setProviderConfig(null);
		}
		return storable;
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
	public List<Oauth2RuntimeConfiguration> findByProviderAndConfigurationType(AuthProvider provider,
			Oauth2ConfigurationType type) {
		return findListByPredicate(x -> x.getProvider() != null && x.getProvider() == provider
				&& x.getConfigurationType() ==type);

	}

	@Override
	public List<Oauth2RuntimeConfiguration> findByConfigurationType(Oauth2ConfigurationType authentication) {
		return findListByPredicate(
				x -> x.getConfigurationType() ==authentication);
	}

	@Override
	public void save(Oauth2RuntimeConfiguration data) {
		if (data.getReadOnly() != null && data.getReadOnly()) {
			throw new RuntimeException("The configuration with id:" + data.getRegistrationId()
					+ " cannot be saved because is readonly and comes from spring boot configuration");

		}
		repo.save(forStorage(data));

	}

	@Override
	public Oauth2RuntimeConfiguration findByPredicate(Predicate<Oauth2RuntimeConfiguration> predicate) {

		return completeProvider(super.findByPredicate(predicate));
	}

	/**
	 * A stored configuration as the rest of the application needs to see it: with the
	 * endpoints of its provider, with their placeholders filled, and with the client
	 * it was saved with.
	 *
	 * <p>
	 * What the store holds is deliberately partial. A provider that has a library
	 * entry keeps no endpoints of its own, and those endpoints are templates shared
	 * by every configuration of that provider: {@code {tenantId}} for an Entra
	 * organization, {@code {cognitoDomain}} and {@code {region}} for a user pool,
	 * {@code {serverUrl}} and {@code {realm}} for a Keycloak. The values that fill
	 * them, and the client id and secret, live in the secret store.
	 * </p>
	 *
	 * <p>
	 * The browser login resolved all of this when it built its client registration,
	 * but every path that reads configurations from here did not, and that is the
	 * whole of why bearer tokens of those providers could not be accepted: discovery
	 * ran against an address still carrying braces, the issuer of a presented token
	 * matched no configuration, and introspection failed outright because the client
	 * it needed was never loaded. Resolving here puts the two halves back on the same
	 * footing.
	 * </p>
	 *
	 * <p>
	 * A configuration whose secret cannot be read is returned as it stands rather
	 * than thrown away: these reads serve lists, and one unreadable configuration
	 * must not take authentication down with it.
	 * </p>
	 */
	private Oauth2RuntimeConfiguration completeProvider(Oauth2RuntimeConfiguration byPredicate) {
		if (byPredicate == null) {
			return null;
		}
		Oauth2RuntimeConfiguration runtimeConfiguration = byPredicate;
		if (byPredicate.getProviderConfig() == null
				&& runtimeConfiguration.getProvider() != AuthProvider.oauth2_generic) {
			Oauth2ProviderConfig providerConfig = this.providersLibraryDao
					.findByCode(runtimeConfiguration.getProvider().name());
			runtimeConfiguration = new Oauth2RuntimeConfiguration(byPredicate);
			runtimeConfiguration.setProviderConfig(providerConfig);

		}
		return withStoredClient(runtimeConfiguration);
	}

	/**
	 * Adds the client the configuration was saved with, and resolves its endpoints
	 * against the attributes stored beside it. Configurations that carry their client
	 * already, the ones declared in the application configuration, are only resolved.
	 */
	private Oauth2RuntimeConfiguration withStoredClient(Oauth2RuntimeConfiguration configuration) {
		GeboOauth2SecretContent client = configuration.getClient();
		if (client == null && configuration.getClientSecretId() != null) {
			try {
				AbstractGeboSecretContent content = secretsService
						.getSecretContentById(configuration.getClientSecretId());
				if (content instanceof GeboOauth2SecretContent oauth2Client) {
					client = oauth2Client;
				} else {
					LOGGER.warn("The secret {} of the oauth2 configuration {} is not an oauth2 client",
							configuration.getClientSecretId(), configuration.getRegistrationId());
				}
			} catch (GeboCryptSecretException | RuntimeException e) {
				LOGGER.warn("Cannot read the client of the oauth2 configuration {}: {}",
						configuration.getRegistrationId(), e.getMessage());
			}
		}
		if (client == null) {
			return configuration;
		}
		Oauth2RuntimeConfiguration resolved = new Oauth2RuntimeConfiguration(configuration);
		resolved.setClient(client);
		resolved.setProviderConfig(
				Oauth2ClientRegistration.resolveProviderConfig(configuration.getProviderConfig(),
						client.getCustomAttributes()));
		return resolved;
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
