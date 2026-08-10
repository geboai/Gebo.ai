package ai.gebo.webdavcms.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.RemoteVirtualFileSystemContentConsumingSessionParam;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;
import ai.gebo.webdavcms.handler.IGWebdavContentManagementSystemHandler;

@Configuration
public class GWebdavContentsDispatcherConfig extends
		SingletonBuilder<GWebdavContentManagementSystem, GWebdavProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> {

	public GWebdavContentsDispatcherConfig(IGWebdavContentManagementSystemHandler handler, IGMessageBroker broker,
			IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo,
			IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo, documentsRepo,
				virtualFolderRepo, workflowRouter);
	}

	@Bean
	@Scope("singleton")
	@Qualifier("webdavContentsDispatcher")
	public GIOCModuleContentsDispatcher<GWebdavContentManagementSystem, GWebdavProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> getWebdavContentsDispatcher() {
		return super.getDispatcher();
	}
}