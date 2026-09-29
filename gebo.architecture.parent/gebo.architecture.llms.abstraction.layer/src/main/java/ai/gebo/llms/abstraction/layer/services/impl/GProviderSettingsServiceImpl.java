package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderCurrency;
import ai.gebo.llms.abstraction.layer.model.GProviderSettings;
import ai.gebo.llms.abstraction.layer.repository.GProviderSettingsRepository;
import ai.gebo.llms.abstraction.layer.services.IGCurrenciesService;
import ai.gebo.llms.abstraction.layer.services.IGModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGProviderSettingsService;
import lombok.RequiredArgsConstructor;

/**
 * Mongo backed {@link IGProviderSettingsService}; the declared settings are read
 * from the model types of every family, through the support services
 * repositories: some support services, like the OpenAI compatible ones declared in
 * providers.yml, are registered there without being Spring beans.
 */
@Service
@RequiredArgsConstructor
public class GProviderSettingsServiceImpl implements IGProviderSettingsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GProviderSettingsServiceImpl.class);
	private final ObjectProvider<GProviderSettingsRepository> repositoryProvider;
	private final ObjectProvider<IGImplementationsRepositoryPattern<? extends IGModelConfigurationSupportService<?, ?, ?, ?>>> modelTypes;
	private final IGCurrenciesService currencies;

	@Override
	public GProviderCurrency getProviderCurrency(String providerId) {
		requireProvider(providerId);
		GProviderSettingsRepository repository = repositoryProvider.getIfAvailable();
		GProviderSettings settings = repository != null ? repository.findById(providerId).orElse(null) : null;
		GProviderCurrency currency = new GProviderCurrency();
		currency.setProviderId(providerId);
		currency.setDeclaredCurrencyCode(declaredCurrency(providerId));
		currency.setOverridden(settings != null && settings.getCurrencyCode() != null);
		currency.setCurrencyCode(currency.isOverridden() ? settings.getCurrencyCode() : currency.getDeclaredCurrencyCode());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getProviderCurrency(providerId=" + providerId + ") => " + currency);
		}
		return currency;
	}

	@Override
	public GProviderCurrency updateProviderCurrency(String providerId, String currencyCode) {
		requireProvider(providerId);
		GProviderSettingsRepository repository = repositoryProvider.getIfAvailable();
		if (repository == null) {
			throw new IllegalStateException("No MongoDB in this application, provider settings are not available");
		}
		String code = currencyCode != null && !currencyCode.isBlank() ? currencyCode.trim().toUpperCase() : null;
		if (code != null && !currencies.isKnown(code)) {
			throw new IllegalArgumentException("Unknown currency " + currencyCode);
		}
		GProviderSettings settings = repository.findById(providerId).orElseGet(() -> {
			GProviderSettings created = new GProviderSettings();
			created.setProviderId(providerId);
			return created;
		});
		settings.setCurrencyCode(code);
		settings.setDateModified(new Date());
		repository.save(settings);
		LOGGER.info(code != null ? "Set the currency of provider=" + providerId + " to " + code
				: "Reset the currency of provider=" + providerId + " to the declared one");
		return getProviderCurrency(providerId);
	}

	/** The currency the provider's model types declare, else {@link #FALLBACK_CURRENCY}. */
	private String declaredCurrency(String providerId) {
		return modelTypes.orderedStream().flatMap(repository -> repository.getImplementations().stream())
				.map(IGModelConfigurationSupportService::getType).filter(Objects::nonNull).map(GModelType.class::cast)
				.filter(type -> providerId.equals(type.getProviderId()) && type.getDefaultCurrencyCode() != null)
				.map(GModelType::getDefaultCurrencyCode).findFirst().orElse(FALLBACK_CURRENCY);
	}

	private static void requireProvider(String providerId) {
		if (providerId == null || providerId.isBlank()) {
			throw new IllegalArgumentException("Missing providerId");
		}
	}
}
