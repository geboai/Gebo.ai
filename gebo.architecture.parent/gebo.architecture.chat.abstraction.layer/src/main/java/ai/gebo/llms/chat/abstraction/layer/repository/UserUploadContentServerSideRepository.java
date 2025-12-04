package ai.gebo.llms.chat.abstraction.layer.repository;

import java.util.stream.Stream;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;

public interface UserUploadContentServerSideRepository extends IGBaseMongoDBRepository<UserUploadContentServerSide> {
	@Override
	default Class<UserUploadContentServerSide> getManagedType() {
		return UserUploadContentServerSide.class;
	}

	public void deleteByUserContextCode(String userContextCode);
	
	public Stream<UserUploadContentServerSide> findByUserContextCode(String userContextCode);

}
