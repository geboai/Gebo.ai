package ai.gebo.integration.content.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.integration.content.handler.IGIntegrationSystemContentHandler;
import ai.gebo.integration.content.handler.repositories.IntegrationProjectEndpointRepository;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Registers the default integration content handler as a bean that a
 * downstream module can override by supplying its own bean of the
 * corresponding interface.
 */
@Configuration
public class GIntegrationDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGIntegrationSystemContentHandler.class)
	public IGIntegrationSystemContentHandler integrationSystemContentHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, IntegrationProjectEndpointRepository endpointRepository,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new GIntegrationSystemContentHandlerImpl(buildSystemHandlerRepository, contentHandler,
				endpointRepository, localFolderDiscoveryService, persistentObjectManager, messageBroker,
				ingestionHandler);
	}

}
