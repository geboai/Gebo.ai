package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderCurrency;
import ai.gebo.llms.abstraction.layer.model.GProviderSettings;
import ai.gebo.llms.abstraction.layer.repository.GProviderSettingsRepository;
import ai.gebo.llms.abstraction.layer.services.IGModelConfigurationSupportService;

class GProviderSettingsServiceImplTest {

	/** One support services repository holding a model type per given provider and currency. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ObjectProvider<IGImplementationsRepositoryPattern<? extends IGModelConfigurationSupportService<?, ?, ?, ?>>> types(
			String[]... providerAndCurrency) {
		java.util.List services = Stream.of(providerAndCurrency).map(pc -> {
			GChatModelType type = new GChatModelType();
			type.setProviderId(pc[0]);
			type.setDefaultCurrencyCode(pc[1]);
			IGModelConfigurationSupportService service = mock(IGModelConfigurationSupportService.class);
			when(service.getType()).thenReturn(type);
			return service;
		}).toList();
		IGImplementationsRepositoryPattern repository = mock(IGImplementationsRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn(services);
		ObjectProvider provider = mock(ObjectProvider.class);
		when(provider.orderedStream()).thenAnswer(x -> Stream.of(repository));
		return provider;
	}

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static GProviderSettingsServiceImpl service(GProviderSettingsRepository repository) {
		return new GProviderSettingsServiceImpl(provider(repository),
				types(new String[] { "regolo.ai", null }, new String[] { "regolo.ai", "EUR" },
						new String[] { "openai", null }),
				new GCurrenciesServiceImpl());
	}

	@Test
	void theDeclaredCurrencyAppliesElseUsd() {
		GProviderSettingsRepository repository = mock(GProviderSettingsRepository.class);
		when(repository.findById(any())).thenReturn(Optional.empty());

		GProviderCurrency regolo = service(repository).getProviderCurrency("regolo.ai");
		assertEquals("EUR", regolo.getCurrencyCode());
		assertFalse(regolo.isOverridden());
		assertEquals("USD", service(repository).getProviderCurrency("openai").getCurrencyCode());
	}

	@Test
	void theAdminCurrencyWinsAndNullGoesBackToTheDeclaredOne() {
		GProviderSettingsRepository repository = mock(GProviderSettingsRepository.class);
		GProviderSettings stored = new GProviderSettings();
		stored.setProviderId("regolo.ai");
		stored.setCurrencyCode("CHF");
		when(repository.findById("regolo.ai")).thenReturn(Optional.of(stored));

		GProviderCurrency regolo = service(repository).getProviderCurrency("regolo.ai");
		assertEquals("CHF", regolo.getCurrencyCode());
		assertEquals("EUR", regolo.getDeclaredCurrencyCode());
		assertTrue(regolo.isOverridden());

		service(repository).updateProviderCurrency("regolo.ai", null);
		ArgumentCaptor<GProviderSettings> saved = ArgumentCaptor.forClass(GProviderSettings.class);
		verify(repository).save(saved.capture());
		assertEquals(null, saved.getValue().getCurrencyCode());
	}

	@Test
	void anUnknownCurrencyIsRejected() {
		GProviderSettingsRepository repository = mock(GProviderSettingsRepository.class);
		when(repository.findById(any())).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> service(repository).updateProviderCurrency("openai", "XYZ"));
		assertThrows(IllegalArgumentException.class, () -> service(repository).getProviderCurrency(" "));
		verify(repository, never()).save(any());
		// Codes are normalized before the check.
		service(repository).updateProviderCurrency("openai", " gbp ");
		ArgumentCaptor<GProviderSettings> saved = ArgumentCaptor.forClass(GProviderSettings.class);
		verify(repository).save(saved.capture());
		assertEquals("GBP", saved.getValue().getCurrencyCode());
	}

	@Test
	void theBundledCurrenciesAreTheActiveIso4217Ones() {
		GCurrenciesServiceImpl currencies = new GCurrenciesServiceImpl();
		assertTrue(currencies.getCurrencies().size() > 150);
		assertTrue(currencies.isKnown("EUR") && currencies.isKnown("usd") && currencies.isKnown("JPY"));
		// Funds, precious metals and the testing code are not currencies prices are expressed in.
		assertFalse(currencies.isKnown("XAU") || currencies.isKnown("XTS") || currencies.isKnown("USN"));
		assertEquals(0, currencies.getCurrencies().stream().filter(c -> c.getCode().equals("JPY")).findFirst()
				.orElseThrow().getMinorUnits());
	}
}
