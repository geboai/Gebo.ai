package ai.gebo.webdavcms.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.impl.AbstractJobLaunchManager;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;

@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class WebdavJobLaunchManager extends AbstractJobLaunchManager {

	public WebdavJobLaunchManager(IGGeboIngestionJobQueueService jobLauncherController,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker broker,
			JobStatusRepository jobStatusRepositoy) {
		super(jobLauncherController, persistentObjectManager, broker, jobStatusRepositoy,
				GStandardModulesConstraints.WEBDAB_CMS_MODULE);
	}
}