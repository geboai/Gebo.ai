/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.cluster;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealPricedModel;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Base class for {@link IGRuntimeModelConfigurationDao} implementations that
 * manage live (remote-connection) LLM model clients and must stay consistent
 * across a cluster of application instances.
 * <p>
 * It keeps all the existing single-node behaviour of
 * {@link GAbstractRuntimeConfigurationDao} and layers on top the cluster-aware
 * variants of the mutating operations
 * ({@link #addRuntimeByConfigClustered(GBaseModelConfig)},
 * {@link #reconfigureByConfigClustered(GBaseModelConfig)},
 * {@link #deleteByCodeClustered(String)}). Each of these applies the change
 * locally (through the plain operation the subclass already implements) and then
 * asks the {@link GLlmModelClusterSynchronizer} to propagate it to the other
 * instances.
 * <p>
 * The synchronizer is optional ({@code required = false}): when the LLM cluster
 * infrastructure is absent the clustered operations degrade gracefully to plain
 * local operations. When present but clustering is disabled, the broadcast is a
 * no-op. Startup initialization and the application of <em>remote</em> events go
 * through the plain (non-clustered) operations, so they never re-broadcast.
 *
 * @param <IFacetype>   the live model client type managed by this DAO
 * @param <ModelConfig> the configuration type of the managed models
 */
public abstract class GAbstractClusteredModelRuntimeConfigurationDao<IFacetype extends IGConfigurableModel, ModelConfig extends GBaseModelConfig>
		extends GAbstractRuntimeConfigurationDao<IFacetype>
		implements IGRuntimeModelConfigurationDao<IFacetype, ModelConfig>, ApplicationListener<ContextRefreshedEvent> {

	@Autowired(required = false)
	protected GLlmModelClusterSynchronizer clusterSynchronizer;

	@Autowired
	protected ApplicationContext applicationContext;

	/** Associates the configured API keys with provider deals; optional. */
	@Autowired(required = false)
	protected IGProviderDealService providerDealService;

	private static final Logger LOGGER_DEALS = LoggerFactory
			.getLogger(GAbstractClusteredModelRuntimeConfigurationDao.class);

	protected GAbstractClusteredModelRuntimeConfigurationDao(List<IFacetype> staticConfigs,
			IGDynamicConfigurationSource<IFacetype> dynamic) {
		super(staticConfigs, dynamic);
	}

	/**
	 * @return the model family this DAO manages, used to route propagated events
	 *         back to the right DAO on remote instances.
	 */
	protected abstract GLlmModelClusterCategory getClusterCategory();

	/**
	 * Loads every persisted model of this family and brings each up at runtime.
	 * Invoked once, when {@link #applicationContext} itself finishes refreshing.
	 */
	protected abstract void initializeRuntimeModels();

	/**
	 * {@link ContextRefreshedEvent} bubbles up from every ancestor-to-descendant
	 * context in the hierarchy to every context's listeners - including the
	 * short-lived per-service child contexts Spring Cloud LoadBalancer creates on
	 * the first call to a {@code @LoadBalanced} client. Since model
	 * initialization itself calls out to load-balanced cluster clients (e.g. to
	 * fetch a secret), reacting unconditionally here would recurse: initializing
	 * a model triggers the client's first-ever call, which creates its
	 * LoadBalancer child context, whose own refresh republishes this same event
	 * up to this listener - forever, until the stack overflows. Only the
	 * event for this DAO's own context may proceed.
	 */
	@Override
	public final void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext() != applicationContext) {
			return;
		}
		initializeRuntimeModels();
		ensureProviderDealsOfRunningModels();
	}

	/**
	 * Associates the API key of every model brought up at startup with a deal of its
	 * provider, importing the price the provider's API gave with it, exactly as saving
	 * the model does: the models configured before the provider deals existed, or
	 * while their store was unavailable, get covered without being saved again.
	 * <p>
	 * Idempotent, so running on every instance of a cluster is harmless. Runs in the
	 * background: a first association may read the key's spending limits from the
	 * provider's API, and startup must not wait on the network. Best effort, as the
	 * association itself.
	 */
	@SuppressWarnings("unchecked")
	protected void ensureProviderDealsOfRunningModels() {
		if (providerDealService == null) {
			return;
		}
		List<IFacetype> models = new ArrayList<>(getConfigurations());
		if (models.isEmpty()) {
			return;
		}
		Thread.ofVirtual().name("provider-deals-startup-" + getClusterCategory()).start(() -> {
			int associated = 0;
			for (IFacetype model : models) {
				try {
					if (model.getConfig() != null) {
						ensureProviderDeal((ModelConfig) model.getConfig());
						associated++;
					}
				} catch (Throwable e) {
					LOGGER_DEALS.error("Cannot associate the model code=" + model.getCode()
							+ " with a provider deal at startup", e);
				}
			}
			LOGGER_DEALS.info("Checked the provider deals of " + associated + " " + getClusterCategory()
					+ " model(s) running at startup");
		});
	}

	@Override
	public void addRuntimeByConfigClustered(ModelConfig config) throws LLMConfigException {
		addRuntimeByConfig(config);
		ensureProviderDeal(config);
		if (clusterSynchronizer != null) {
			clusterSynchronizer.broadcastAdd(getClusterCategory(), config);
		}
	}

	@Override
	public void reconfigureByConfigClustered(ModelConfig config) throws LLMConfigException {
		IFacetype handler = findByCode(config.getCode());
		if (handler != null) {
			handler.reconfigure(config);
		}
		ensureProviderDeal(config);
		if (clusterSynchronizer != null) {
			clusterSynchronizer.broadcastUpdate(getClusterCategory(), config);
		}
	}

	/**
	 * Makes sure the API key chosen for the model, or the NO_API_KEY pseudo key for a
	 * model without one, is covered by a deal of the model's real provider, and
	 * imports into that deal the price the provider's API gave with the model, if
	 * any. Runs on the configuring instance only: these clustered
	 * operations are the user's configuration, while startup and the replicas apply
	 * the plain operations, so a configuration change is associated exactly once.
	 * <p>
	 * Best effort: the association is bookkeeping, so any failure is logged and never
	 * fails the model configuration.
	 */
	protected void ensureProviderDeal(ModelConfig config) {
		if (providerDealService == null || config == null) {
			return;
		}
		try {
			// A configuration without API key is covered by the provider's deal
			// holding the NO_API_KEY pseudo key.
			String secretCode = GProviderDeal.coveredKey(config.getApiSecretCode());
			IFacetype model = findByCode(config.getCode());
			GModelType type = model != null ? model.getType() : null;
			String providerId = type != null ? type.getProviderId() : null;
			if (providerId == null || providerId.isBlank()) {
				LOGGER_DEALS.warn("Model code=" + config.getCode() + " of type=" + config.getModelTypeCode()
						+ " declares no provider, its API key is not associated with a provider deal");
				return;
			}
			providerDealService.ensureDeal(providerId, secretCode);
			// The price the provider's API gave with the chosen model, if any, becomes the
			// deal's price of the model unless the admin set one.
			if (model != null && model.getProviderApiPricingConditions() != null) {
				providerDealService.importModelPricing(providerId, secretCode, model.safeGetModelCode(),
						model.getProviderApiPricingConditions());
			}
		} catch (Throwable e) {
			LOGGER_DEALS.error("Cannot associate the API key of model code=" + config.getCode()
					+ " with a provider deal, the model is configured all the same", e);
		}
	}

	/**
	 * Attaches the provider deals to a model this DAO registers, so that its
	 * {@code getPricingConditions()} returns the price of the deal covering its API
	 * key; to be called on every path registering a model, before any wrapping. A
	 * model not implementing {@link IGProviderDealPricedModel} stays priced by its
	 * configuration.
	 */
	protected void attachProviderDealPricing(IGConfigurableModel model) {
		if (model instanceof IGProviderDealPricedModel priced) {
			priced.setProviderDealService(providerDealService);
			if (LOGGER_DEALS.isDebugEnabled()) {
				LOGGER_DEALS.debug("Model code=" + model.getCode() + " priced by the provider deals: "
						+ (providerDealService != null ? "yes" : "no, no deals service"));
			}
		} else if (model != null && LOGGER_DEALS.isDebugEnabled()) {
			LOGGER_DEALS.debug("Model code=" + model.getCode() + " of class=" + model.getClass().getName()
					+ " is priced by the provider API only");
		}
	}

	@Override
	public void deleteByCodeClustered(String code) throws LLMConfigException {
		deleteByCode(code);
		if (clusterSynchronizer != null) {
			clusterSynchronizer.broadcastDelete(getClusterCategory(), code);
		}
	}
}
