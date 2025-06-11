package ai.gebo.security.services;

import java.util.List;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

public interface IGOauth2RuntimeConfigurationDao extends IGRuntimeConfigurationDao<Oauth2RuntimeConfiguration> {

	public void insert(Oauth2RuntimeConfiguration configuration);

	public void delete(Oauth2RuntimeConfiguration oauth2RuntimeConfiguration);

	public List<Oauth2RuntimeConfiguration> findByProvider(AuthProvider provider);

	public List<Oauth2RuntimeConfiguration> findByProviderAndConfigurationTypesContains(AuthProvider provider,
			Oauth2ConfigurationType type);

	public List<Oauth2RuntimeConfiguration> findByConfigurationTypesContains(Oauth2ConfigurationType authentication);

	public void save(Oauth2RuntimeConfiguration data);

}
