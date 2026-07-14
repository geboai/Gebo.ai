package ai.gebo.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;

public interface UserRepositoryCustom {
	Page<UserRepository.UserInfos> findByQbe(EditableUser example, Pageable pageable);
}
