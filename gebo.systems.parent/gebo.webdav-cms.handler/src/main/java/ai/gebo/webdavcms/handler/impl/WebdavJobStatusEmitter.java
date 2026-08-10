package ai.gebo.webdavcms.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.jobs.services.impl.AbstractJobStatusEmitter;

@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class WebdavJobStatusEmitter extends AbstractJobStatusEmitter {

	public WebdavJobStatusEmitter(IGRuntimeBinder runtimeBinder, IMessageEnvelopeFactory envelopeFactory) {
		super(runtimeBinder, envelopeFactory, GStandardModulesConstraints.WEBDAB_CMS_MODULE);
	}
}