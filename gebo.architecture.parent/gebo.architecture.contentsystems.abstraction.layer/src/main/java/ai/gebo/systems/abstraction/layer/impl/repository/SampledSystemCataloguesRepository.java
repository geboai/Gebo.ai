package ai.gebo.systems.abstraction.layer.impl.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.systems.abstraction.layer.model.SampledSystemCatalogues;

/**
 * Mongo store of {@link SampledSystemCatalogues} — the cached catalogue snapshots
 * sampled from the content/virtual-filesystem searchable systems.
 */
public interface SampledSystemCataloguesRepository extends MongoRepository<SampledSystemCatalogues, String> {

	List<SampledSystemCatalogues> findByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String systemConfigurationCode);
}
