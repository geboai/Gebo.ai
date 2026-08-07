package ai.gebo.webdavcms.handler.repositories;

import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;

public interface WebdavProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<GWebdavProjectEndpoint> {

	@Override
	default Class<GWebdavProjectEndpoint> getManagedType() {
		return GWebdavProjectEndpoint.class;
	}
}