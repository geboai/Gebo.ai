/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboSshKeySecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGeboSecretsExternalStorageService;
import ai.gebo.secrets.services.IGeboSecretsStorageManagementService;

@Import(ExternalizedSecretsIntegrationTest.FakeExternalStorageConfig.class)
public class ExternalizedSecretsIntegrationTest extends AbstractBaseIntegrationTest {

	private static final String CONTEXT_CODE = "ext-secrets-test";

	@Autowired
	private IGeboSecretsStorageManagementService storageManagementService;

	@Autowired
	private FakeExternalSecretsStorageService fakeExternalStorage;

	@TestConfiguration
	public static class FakeExternalStorageConfig {

		@Bean
		public FakeExternalSecretsStorageService fakeExternalSecretsStorageService() {
			return new FakeExternalSecretsStorageService();
		}
	}

	public static class FakeExternalSecretsStorageService implements IGeboSecretsExternalStorageService {

		private final String id = "fake-external-storage";
		private final String description = "Fake external secrets storage for integration tests";
		private boolean configured = false;
		private boolean activeStorage = false;

		private final Map<String, AbstractGeboSecretContent> secrets = new ConcurrentHashMap<>();
		private final Map<String, SecretInfo> secretInfos = new ConcurrentHashMap<>();

		void setConfigured(boolean configured) {
			this.configured = configured;
		}

		void setActiveStorage(boolean activeStorage) {
			this.activeStorage = activeStorage;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public boolean isConfigured() {
			return configured;
		}

		@Override
		public boolean isActiveStorage() {
			return activeStorage;
		}

		@Override
		public List<String> getAllSecretsId() {
			return new ArrayList<>(secrets.keySet());
		}

		@Override
		public AbstractGeboSecretContent getSecretContentById(String id) throws GeboCryptSecretException {
			AbstractGeboSecretContent content = secrets.get(id);
			if (content == null)
				throw new GeboCryptSecretException("Unknown secret with code=>" + id);
			return content;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T extends GeboCustomSecretContent> T getCustomSecretContentById(String id, Class<T> type)
				throws GeboCryptSecretException {
			AbstractGeboSecretContent content = secrets.get(id);
			if (content == null)
				throw new GeboCryptSecretException("Unknown secret with code=>" + id);
			if (!type.isInstance(content))
				throw new GeboCryptSecretException("Secret type mismatch");
			return (T) content;
		}

		@Override
		public <SecretType extends AbstractGeboSecretContent> String storeSecret(SecretType secret, String description,
				String contextCode) throws GeboCryptSecretException {
			String secretId = java.util.UUID.randomUUID().toString();
			storeSecret(secret, description, contextCode, secretId);
			return secretId;
		}

		@Override
		public <SecretType extends AbstractGeboSecretContent> void storeSecret(SecretType secret, String description,
				String contextCode, String secretId) throws GeboCryptSecretException {
			secrets.put(secretId, secret);
			SecretInfo info = new SecretInfo();
			info.setCode(secretId);
			info.setDescription(description);
			info.setSecretType(secret.type());
			info.setContextCode(contextCode);
			secretInfos.put(secretId, info);
		}

		@Override
		public <SecretType extends AbstractGeboSecretContent> void updateSecret(SecretType secret, String description,
				String contextCode, String code) throws GeboCryptSecretException {
			if (!secrets.containsKey(code))
				throw new GeboCryptSecretException("Secret with code=>" + code + " not found");
			storeSecret(secret, description, contextCode, code);
		}

		@Override
		public void deleteSecret(String code) throws GeboCryptSecretException {
			secrets.remove(code);
			secretInfos.remove(code);
		}

		@Override
		public List<SecretInfo> getSecretInfoByContextCode(String contextCode) throws GeboCryptSecretException {
			return secretInfos.values().stream().filter(info -> contextCode.equals(info.getContextCode())).toList();
		}

		@Override
		public SecretInfo getSecretInfoById(String code) throws GeboCryptSecretException {
			return secretInfos.get(code);
		}

		@Override
		public void switchToActiveStorage() {
			this.activeStorage = true;
		}

		int getSecretsCount() {
			return secrets.size();
		}
	}

	private String createTokenSecret(String token, String user, String description)
			throws GeboCryptSecretException {
		GeboTokenContent content = new GeboTokenContent();
		content.setToken(token);
		content.setUser(user);
		return secretsAccessService.storeSecret(content, description, CONTEXT_CODE);
	}

	private String createUsernamePasswordSecret(String username, String password, String description)
			throws GeboCryptSecretException {
		GeboUsernamePasswordContent content = new GeboUsernamePasswordContent();
		content.setUsername(username);
		content.setPassword(password);
		return secretsAccessService.storeSecret(content, description, CONTEXT_CODE);
	}

	private String createSshKeySecret(String email, String privateKey, String publicKey, String description)
			throws GeboCryptSecretException {
		GeboSshKeySecretContent content = new GeboSshKeySecretContent();
		content.setEmail(email);
		content.setKey(privateKey);
		content.setPub(publicKey);
		return secretsAccessService.storeSecret(content, description, CONTEXT_CODE);
	}

	private String createCustomSecret(String desc, String contentText, String contentType, String description)
			throws GeboCryptSecretException {
		GeboCustomSecretContent secret = new GeboCustomSecretContent();
		secret.setCustomContentDescription(desc);
		secret.setContent(contentText);
		secret.setContentType(contentType);
		return secretsAccessService.storeSecret(secret, description, CONTEXT_CODE);
	}

	@Test
	public void fullExternalizedSecretsRoundtrip() throws GeboCryptSecretException {
		String tokenId = createTokenSecret("test-token-abc123", "testuser", "Test token secret");
		String upId = createUsernamePasswordSecret("admin", "s3cr3t!", "Test username/password");
		String sshId = createSshKeySecret("test@gebo.ai", "-----BEGIN RSA PRIVATE KEY-----\nfakekey\n-----END RSA PRIVATE KEY-----",
				"ssh-rsa AAAAfake test@gebo.ai", "Test SSH key");
		String customId = createCustomSecret("API Key", "sk-proj-1234567890", "text/plain", "Test custom API key");

		List<String> allIds = secretsAccessService.getAllSecretsId();
		assertTrue(allIds.contains(tokenId), "Token secret should be listed");
		assertTrue(allIds.contains(upId), "Username/password secret should be listed");
		assertTrue(allIds.contains(sshId), "SSH key secret should be listed");
		assertTrue(allIds.contains(customId), "Custom secret should be listed");

		AbstractGeboSecretContent tokenContent = secretsAccessService.getSecretContentById(tokenId);
		assertTrue(tokenContent instanceof GeboTokenContent, "Should be token content");
		assertEquals("test-token-abc123", ((GeboTokenContent) tokenContent).getToken());

		AbstractGeboSecretContent upContent = secretsAccessService.getSecretContentById(upId);
		assertTrue(upContent instanceof GeboUsernamePasswordContent, "Should be username/password content");
		assertEquals("admin", ((GeboUsernamePasswordContent) upContent).getUsername());

		SecretInfo sshInfo = secretsAccessService.getSecretInfoById(sshId);
		assertEquals("Test SSH key", sshInfo.getDescription());

		assertFalse(storageManagementService.isMigrationToExternalPossible(),
				"Migration not possible: external storage not configured");
		assertFalse(storageManagementService.isMigrationToExternalDone(),
				"Migration not done: external storage not configured");

		fakeExternalStorage.setConfigured(true);
		assertEquals(0, fakeExternalStorage.getSecretsCount(),
				"External storage should be empty before migration");

		assertTrue(storageManagementService.isMigrationToExternalPossible(),
				"Migration should be possible: configured but not active");
		assertFalse(storageManagementService.isMigrationToExternalDone(),
				"Migration not yet done: configured but not active");

		storageManagementService.migrateToExternalStorage();

		assertFalse(storageManagementService.isMigrationToExternalPossible(),
				"Migration no longer possible: already migrated");
		assertTrue(storageManagementService.isMigrationToExternalDone(),
				"Migration done: external storage active after migration");

		assertEquals(4, fakeExternalStorage.getSecretsCount(),
				"External storage should have all 4 secrets");

		AbstractGeboSecretContent extToken = fakeExternalStorage.getSecretContentById(tokenId);
		assertTrue(extToken instanceof GeboTokenContent, "External: should be token content");
		assertEquals("test-token-abc123", ((GeboTokenContent) extToken).getToken());

		SecretInfo extInfo = fakeExternalStorage.getSecretInfoById(upId);
		assertEquals("Test username/password", extInfo.getDescription());
		assertEquals(CONTEXT_CODE, extInfo.getContextCode());

		AbstractGeboSecretContent proxiedToken = secretsAccessService.getSecretContentById(tokenId);
		assertTrue(proxiedToken instanceof GeboTokenContent, "Proxied: should be token content");
		assertEquals("test-token-abc123", ((GeboTokenContent) proxiedToken).getToken());

		AbstractGeboSecretContent proxiedSsh = secretsAccessService.getSecretContentById(sshId);
		assertTrue(proxiedSsh instanceof GeboSshKeySecretContent, "Proxied: should be SSH key content");
		assertEquals("test@gebo.ai", ((GeboSshKeySecretContent) proxiedSsh).getEmail());

		List<String> proxiedIds = secretsAccessService.getAllSecretsId();
		assertEquals(4, proxiedIds.size(), "Proxy should list all secrets from external");

		SecretInfo proxiedCustomInfo = secretsAccessService.getSecretInfoById(customId);
		assertEquals("Test custom API key", proxiedCustomInfo.getDescription());

		List<SecretInfo> byContext = secretsAccessService.getSecretInfoByContextCode(CONTEXT_CODE);
		assertEquals(4, byContext.size(), "All 4 secrets should match context code");

		String newTokenId = createTokenSecret("proxy-created-token", "proxyuser", "Created via proxy");
		assertEquals(5, fakeExternalStorage.getSecretsCount(),
				"External should have 5 secrets after proxy store");
		assertEquals(5, secretsAccessService.getAllSecretsId().size(),
				"Proxy should report 5 secrets");

		storageManagementService.migrateFromExternalStorage();

		fakeExternalStorage.setActiveStorage(false);
		fakeExternalStorage.setConfigured(true);

		assertTrue(storageManagementService.isMigrationToExternalPossible(),
				"Migration to external should be possible again after reverse");
		assertFalse(storageManagementService.isMigrationToExternalDone(),
				"Migration done should be false after reverse deactivation");

		List<String> afterReverseIds = secretsAccessService.getAllSecretsId();
		assertTrue(afterReverseIds.contains(tokenId), "Token should be back after reverse migration");
		assertTrue(afterReverseIds.contains(upId), "UP should be back after reverse migration");
		assertTrue(afterReverseIds.contains(sshId), "SSH should be back after reverse migration");
		assertTrue(afterReverseIds.contains(customId), "Custom should be back after reverse migration");
		assertTrue(afterReverseIds.contains(newTokenId), "Proxy-created token should be back after reverse migration");

		AbstractGeboSecretContent restoredToken = secretsAccessService.getSecretContentById(tokenId);
		assertTrue(restoredToken instanceof GeboTokenContent, "Restored should be token content");
		assertEquals("test-token-abc123", ((GeboTokenContent) restoredToken).getToken());

		AbstractGeboSecretContent restoredUp = secretsAccessService.getSecretContentById(upId);
		assertEquals("s3cr3t!", ((GeboUsernamePasswordContent) restoredUp).getPassword());

		SecretInfo restoredInfo = secretsAccessService.getSecretInfoById(sshId);
		assertNotNull(restoredInfo, "SSH info should be restorable after reverse");
		assertEquals("Test SSH key", restoredInfo.getDescription());

		LOGGER.info("Externalized secrets full roundtrip completed successfully");
	}
}