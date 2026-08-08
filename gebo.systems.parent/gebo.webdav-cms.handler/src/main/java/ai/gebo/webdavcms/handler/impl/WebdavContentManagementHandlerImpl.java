package ai.gebo.webdavcms.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;
import ai.gebo.webdavcms.handler.GWebdavResourceReference;
import ai.gebo.webdavcms.handler.IGWebdavContentManagementSystemHandler;
import ai.gebo.webdavcms.handler.IGWebdavVirtualFilesystemConsumingService;
import ai.gebo.webdavcms.handler.repositories.WebdavContentManagementSystemRepository;
import ai.gebo.webdavcms.handler.repositories.WebdavProjectEndpointRepository;

@Service
public class WebdavContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GWebdavContentManagementSystem, GWebdavProjectEndpoint, GWebdavResourceReference, IGWebdavVirtualFilesystemConsumingService>
		implements IGWebdavContentManagementSystemHandler {

	public static final String WEBDAB_CMS = "WEBDAB-CMS";

	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();

	static {
		systemType.setCode(WEBDAB_CMS);
		systemType.setDescription("WebDAV Content Management System");
	}

	protected IGeboSecretsAccessService secretsService = null;

	public WebdavContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, WebdavContentManagementSystemRepository systemsRepo,
			WebdavProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGeboSecretsAccessService secretsService, IGWebdavVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler,
				IGContentManagementSystemConfigurationDao.of(systemsRepo),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointRepo), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
		this.secretsService = secretsService;
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public GWebdavProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GWebdavProjectEndpoint;
	}

	@Override
	public GWebdavContentManagementSystem getSystem(GWebdavProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getWebdavSystemCode());
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.WEBDAB_CMS_MODULE;
	}
}