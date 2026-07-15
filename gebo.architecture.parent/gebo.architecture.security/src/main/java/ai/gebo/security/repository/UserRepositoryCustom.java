package ai.gebo.security.repository;

import ai.gebo.security.model.UserInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;

public interface UserRepositoryCustom {
	Page<UserInfos> findByQbe(EditableUser example, Pageable pageable);
}
