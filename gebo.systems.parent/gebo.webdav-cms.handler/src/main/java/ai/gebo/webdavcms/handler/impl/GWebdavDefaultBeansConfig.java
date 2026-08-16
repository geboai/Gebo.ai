package ai.gebo.webdavcms.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.webdavcms.handler.IGWebdavContentManagementSystemHandler;
import ai.gebo.webdavcms.handler.IGWebdavVirtualFilesystemConsumingService;
import ai.gebo.webdavcms.handler.repositories.WebdavContentManagementSystemRepository;
import ai.gebo.webdavcms.handler.repositories.WebdavProjectEndpointRepository;

/**
 * Registers the default WebDAV content handlers as beans that a downstream
 * module can override by supplying its own bean of the corresponding
 * interface.
 */
@Configuration
public class GWebdavDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGWebdavVirtualFilesystemConsumingService.class)
	public IGWebdavVirtualFilesystemConsumingService webdavVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, WebdavConnectionFactory connectionFactory) {
		return new GWebdavRemoteVirtualFilesystemConsumingServiceImpl(documentFactory, connectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGWebdavContentManagementSystemHandler.class)
	public IGWebdavContentManagementSystemHandler webdavContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, WebdavContentManagementSystemRepository systemsRepo,
			WebdavProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGeboSecretsAccessService secretsService, IGWebdavVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new WebdavContentManagementHandlerImpl(buildSystemHandlerRepository, contentHandler, systemsRepo,
				endpointRepo, localFolderDiscoveryService, persistentObjectManager, messageBroker, secretsService,
				consumingService, ingestionHandler);
	}

}
