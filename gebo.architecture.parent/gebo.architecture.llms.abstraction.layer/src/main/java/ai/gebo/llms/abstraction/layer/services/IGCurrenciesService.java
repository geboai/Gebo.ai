package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GCurrency;

/**
 * The international currencies prices and spending limits can be expressed in: the
 * ISO 4217 list bundled with Gebo.ai.
 */
public interface IGCurrenciesService {

	/** The currencies, sorted by code. */
	public List<GCurrency> getCurrencies();

	/** Whether the code is one of {@link #getCurrencies()}. */
	public boolean isKnown(String currencyCode);
}
