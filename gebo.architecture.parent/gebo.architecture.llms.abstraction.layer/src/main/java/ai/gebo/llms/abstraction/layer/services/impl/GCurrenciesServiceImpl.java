package ai.gebo.llms.abstraction.layer.services.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GCurrency;
import ai.gebo.llms.abstraction.layer.services.IGCurrenciesService;
import lombok.Data;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * {@link IGCurrenciesService} over the ISO 4217 list bundled in
 * {@value #RESOURCE}, read once.
 */
@Service
public class GCurrenciesServiceImpl implements IGCurrenciesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GCurrenciesServiceImpl.class);
	static final String RESOURCE = "currencies/iso4217-currencies.json";

	@Data
	static class CurrenciesFile {
		private String source = null;
		private String published = null;
		private List<GCurrency> currencies = List.of();
	}

	private volatile List<GCurrency> currencies = null;
	private volatile Set<String> codes = null;

	@Override
	public List<GCurrency> getCurrencies() {
		load();
		return currencies;
	}

	@Override
	public boolean isKnown(String currencyCode) {
		load();
		return currencyCode != null && codes.contains(currencyCode.trim().toUpperCase());
	}

	private void load() {
		if (currencies != null) {
			return;
		}
		synchronized (this) {
			if (currencies != null) {
				return;
			}
			ObjectMapper mapper = JsonMapper.builder().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
					.build();
			try (InputStream in = new ClassPathResource(RESOURCE).getInputStream()) {
				CurrenciesFile file = mapper.readValue(in, CurrenciesFile.class);
				List<GCurrency> list = List.copyOf(file.getCurrencies());
				codes = list.stream().map(GCurrency::getCode).collect(Collectors.toUnmodifiableSet());
				currencies = list;
				LOGGER.info("Loaded " + list.size() + " currencies from " + RESOURCE + " (ISO 4217 published "
						+ file.getPublished() + ")");
			} catch (Exception e) {
				throw new IllegalStateException("Cannot read the currencies list " + RESOURCE, e);
			}
		}
	}
}
