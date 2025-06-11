package ai.gebo.integration.content.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.integration.content.handler.GIntegrationContentSystem;
import ai.gebo.integration.content.handler.GIntegrationProjectEndpoint;
import ai.gebo.integration.content.handler.repositories.IntegrationProjectEndpointRepository;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

@Service
public class GIntegrationSystemContentHandlerImpl
		extends GAbstractContentManagementSystemHandler<GIntegrationContentSystem, GIntegrationProjectEndpoint> {
	public final static GContentManagementSystemType type = new GContentManagementSystemType();
	static final GIntegrationContentSystem system = new GIntegrationContentSystem();
	static {
		type.setCode(GStandardModulesConstraints.INTEGRATION_MODULE);
		type.setDescription("3rd party integration module");
		system.setContentManagementSystemType(type.getCode());
		system.setCode(GStandardModulesConstraints.INTEGRATION_MODULE);
		system.setDescription("3rd party integration system");
	}

	public GIntegrationSystemContentHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, IntegrationProjectEndpointRepository endpointRepository,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, IGContentManagementSystemConfigurationDao.of(system),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointRepository), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, ingestionHandler);

	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {

		return type;
	}

	@Override
	public GIntegrationProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {

		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {

		return endpoint instanceof GIntegrationProjectEndpoint;
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.INTEGRATION_MODULE;
	}

	@Override
	public GIntegrationContentSystem getSystem(GIntegrationProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {

		return system;
	}

	@Override
	protected void consumeImplementation(GIntegrationContentSystem contentManagementConfig,
			List<GBuildSystem> buildSystems, GIntegrationProjectEndpoint endpoint, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		

	}

}
