/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.cluster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
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
		implements IGRuntimeModelConfigurationDao<IFacetype, ModelConfig> {

	@Autowired(required = false)
	protected GLlmModelClusterSynchronizer clusterSynchronizer;

	protected GAbstractClusteredModelRuntimeConfigurationDao(List<IFacetype> staticConfigs,
			IGDynamicConfigurationSource<IFacetype> dynamic) {
		super(staticConfigs, dynamic);
	}

	/**
	 * @return the model family this DAO manages, used to route propagated events
	 *         back to the right DAO on remote instances.
	 */
	protected abstract GLlmModelClusterCategory getClusterCategory();

	@Override
	public void addRuntimeByConfigClustered(ModelConfig config) throws LLMConfigException {
		addRuntimeByConfig(config);
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
		if (clusterSynchronizer != null) {
			clusterSynchronizer.broadcastUpdate(getClusterCategory(), config);
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
