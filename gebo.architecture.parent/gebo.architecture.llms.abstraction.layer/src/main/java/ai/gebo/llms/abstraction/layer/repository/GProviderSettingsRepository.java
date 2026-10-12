package ai.gebo.llms.abstraction.layer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.abstraction.layer.model.GProviderSettings;

public interface GProviderSettingsRepository extends MongoRepository<GProviderSettings, String> {
}
