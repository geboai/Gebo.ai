package ai.gebo.security.services;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.model.GeneratedApiKey;
import ai.gebo.security.model.GeneratedApiKeyInfo;

public interface IGGeneratedApiKeyService {
	public GeneratedApiKey generateApiKey(String description, Date expiration) throws GeboPersistenceException;

	public GeneratedApiKey generateApiKey(String description, String username, Date expiration)
			throws GeboPersistenceException;

	public void deleteApiKey(String code) throws GeboPersistenceException;

	public Page<GeneratedApiKeyInfo> getApiKeyPagedList(Pageable pageable) throws GeboPersistenceException;
}
