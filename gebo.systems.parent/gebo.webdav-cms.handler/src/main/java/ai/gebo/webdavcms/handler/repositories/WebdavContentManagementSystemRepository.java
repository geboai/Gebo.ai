package ai.gebo.webdavcms.handler.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;

public interface WebdavContentManagementSystemRepository extends IGBaseMongoDBRepository<GWebdavContentManagementSystem> {

	@Override
	default Class<GWebdavContentManagementSystem> getManagedType() {
		return GWebdavContentManagementSystem.class;
	}
}