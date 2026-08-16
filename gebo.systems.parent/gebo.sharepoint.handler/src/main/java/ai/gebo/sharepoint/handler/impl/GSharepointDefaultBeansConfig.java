package ai.gebo.sharepoint.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemConsumingService;
import ai.gebo.sharepoint.handler.IGSharepointContentManagementSystemHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Registers the default SharePoint content handlers as beans that a
 * downstream module can override by supplying its own bean of the
 * corresponding interface.
 */
@Configuration
public class GSharepointDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGMicrosoftGraphVirtualFilesystemConsumingService.class)
	public IGMicrosoftGraphVirtualFilesystemConsumingService microsoftGraphVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory) {
		return new GMicrosoftGraphVirtualFilesystemConsumingServiceImpl(documentFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGSharepointContentManagementSystemHandler.class)
	public IGSharepointContentManagementSystemHandler sharepointContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, SharepointSystemsConfiguratoinDao configurationsDao,
			SharepointProjectEndpointConfiguratoinDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager,
			GMicrosoftGraphVirtualFilesystemBrowsingServiceImpl msGraphVirtualService, IGMessageBroker messageBroker,
			IGMicrosoftGraphVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new GSharepointContentManagementSystemHandlerImpl(buildSystemHandlerRepository, contentHandler,
				configurationsDao, endpointsDao, localFolderDiscoveryService, persistentObjectManager,
				msGraphVirtualService, messageBroker, consumingService, ingestionHandler);
	}

}
