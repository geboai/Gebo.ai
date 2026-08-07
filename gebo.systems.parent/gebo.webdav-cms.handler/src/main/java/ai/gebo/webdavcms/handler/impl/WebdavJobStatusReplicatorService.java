package ai.gebo.webdavcms.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.replicator.model.IGReplicationsMap;
import ai.gebo.jobs.services.impl.AbstractJobStatusReplicatorService;

@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class WebdavJobStatusReplicatorService extends AbstractJobStatusReplicatorService {

	public WebdavJobStatusReplicatorService(GeboApplicationArchitecture architecture,
			IGReplicationsMap replicationsMap, IMessageEnvelopeFactory envelopeFactory,
			IGRuntimeBinder runtimeBinder) {
		super(GStandardModulesConstraints.WEBDAB_CMS_MODULE, architecture, replicationsMap,
				envelopeFactory, runtimeBinder);
	}
}