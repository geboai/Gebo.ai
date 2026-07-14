package ai.gebo.security.services.impl;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.config.GeneratedApiKeyConfig;
import ai.gebo.security.model.GeneratedApiKey;
import ai.gebo.security.model.GeneratedApiKeyInfo;
import ai.gebo.security.repository.GeneratedApiKeyRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGGeneratedApiKeyService;
import ai.gebo.security.services.IGSecurityService;
import io.jsonwebtoken.security.SecurityException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GGeneratedApiKeyServiceImpl implements IGGeneratedApiKeyService {
	private final LocalJwtTokenProvider jwtTokenProvider;
	private final IGSecurityService securityService;
	private final IGPersistentObjectManager persistentObjectManager;
	private final GeneratedApiKeyRepository repository;
	private final GeneratedApiKeyConfig apikeyConfig;

	@Override
	public GeneratedApiKey generateApiKey(String description, String username, Date expiration)
			throws GeboPersistenceException {
		if (!apikeyConfig.isAdminApiKeyGenEnabled())
			throw new SecurityException("Api key generation disabled in this system for admin");
		if (!securityService.isCurrentUserAdmin())
			throw new SecurityException("Only admin users can call this service");
		GeneratedApiKey gen = new GeneratedApiKey();
		gen.setImpersonatedUser(username);
		gen.setApiKey(jwtTokenProvider.createToken(username, expiration));
		gen.setExpiration(expiration);
		gen.setDescription(description);

		return this.persistentObjectManager.insert(gen);
	}

	@Override
	public void deleteApiKey(String code) {
		Optional<GeneratedApiKey> opt = repository.findById(code);
		if (opt.isPresent()) {
			if (securityService.isCurrentUserAdmin()) {
				repository.deleteById(code);
			} else if (securityService.getCurrentUser().getUsername().equals(opt.get().getUserCreated())) {
				repository.deleteById(code);
			} else
				throw new SecurityException("You cannot delete this apiKey");
		}
	}

	@Override
	public Page<GeneratedApiKeyInfo> getApiKeyPagedList(Pageable pageable) {
		if (securityService.isCurrentUserAdmin()) {
			return repository.findAllProjectedBy(pageable);
		} else {
			return repository.findProjectedByUserCreated(securityService.getCurrentUser().getUsername(), pageable);
		}

	}

	@Override
	public GeneratedApiKey generateApiKey(String description, Date expiration) throws GeboPersistenceException {
		if (!apikeyConfig.isUserApiKeyGenEnabled())
			throw new SecurityException("Api key generation disabled in this system for users");
		UserInfos user = securityService.getCurrentUser();
		GeneratedApiKey gen = new GeneratedApiKey();
		gen.setImpersonatedUser(user.getUsername());
		gen.setApiKey(jwtTokenProvider.createToken(user.getUsername(), expiration));
		gen.setExpiration(expiration);
		gen.setDescription(description);
		return this.persistentObjectManager.insert(gen);
	}

}
