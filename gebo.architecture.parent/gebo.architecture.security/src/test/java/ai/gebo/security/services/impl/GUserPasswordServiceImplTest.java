/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGUserPasswordService;

/**
 * What {@link GUserPasswordServiceImpl} has to get right, against an in-memory
 * stand-in for the secret store.
 *
 * <p>
 * The store is faked rather than mocked because the behaviour under test is
 * <i>about</i> the store's state - that a password change reuses one secret instead
 * of accumulating them, that a lookup finds a secret written by a previous call -
 * and a mock verifying calls would assert the implementation rather than the
 * outcome.
 * </p>
 *
 * Gebo.ai comment agent
 */
class GUserPasswordServiceImplTest {

	private static final String USERNAME = "someone@gebo.ai";

	private FakeSecretsStore store;
	private GUserPasswordServiceImpl service;

	@BeforeEach
	void setUp() {
		store = new FakeSecretsStore();
		service = new GUserPasswordServiceImpl(store);
	}

	/** The context code is the contract with the migration and with anything reading the store by hand. */
	@Test
	void filesThePasswordUnderTheUserContextCode() throws Exception {
		service.storePassword(USERNAME, "s3cret");

		assertThat(IGUserPasswordService.contextCodeOf(USERNAME)).isEqualTo("user:" + USERNAME);
		assertThat(store.contents).hasSize(1);
		String code = store.contents.keySet().iterator().next();
		assertThat(store.contextCodes.get(code)).isEqualTo("user:" + USERNAME);
		assertThat(store.contents.get(code).type()).isEqualTo(GeboSecretType.USERNAME_PASSWORD);
	}

	/**
	 * The store encrypts the content itself, so what goes in is the plaintext - a
	 * pre-encoded value here would be encrypted twice and could never be compared
	 * against what a login presents.
	 */
	@Test
	void storesThePlaintextAndReadsItBack() throws Exception {
		service.storePassword(USERNAME, "s3cret");

		GeboUsernamePasswordContent content = (GeboUsernamePasswordContent) store.contents.values().iterator().next();
		assertThat(content.getPassword()).isEqualTo("s3cret");
		assertThat(content.getUsername()).isEqualTo(USERNAME);
		assertThat(service.findRawPassword(USERNAME)).isEqualTo("s3cret");
	}

	@Test
	void matchesOnlyTheStoredPassword() throws Exception {
		service.storePassword(USERNAME, "s3cret");

		assertThat(service.matches(USERNAME, "s3cret")).isTrue();
		assertThat(service.matches(USERNAME, "S3CRET")).isFalse();
		assertThat(service.matches(USERNAME, "")).isFalse();
		assertThat(service.matches(USERNAME, null)).isFalse();
	}

	/** A user with no password - a federated identity - is a failed match, not an error. */
	@Test
	void aUserWithoutAPasswordNeverMatches() {
		assertThat(service.hasPassword(USERNAME)).isFalse();
		assertThat(service.matches(USERNAME, "anything")).isFalse();
		assertThat(service.matches(USERNAME, null)).isFalse();
	}

	@Test
	void aUserWithoutAPasswordReadsBackAsNull() throws Exception {
		assertThat(service.findRawPassword(USERNAME)).isNull();
	}

	/**
	 * The one that matters most: a password change must <b>replace</b> the secret, not
	 * add one. A second secret would leave the old password live in the store and make
	 * which one answers a lookup a coin toss.
	 */
	@Test
	void aPasswordChangeReplacesTheSecretInsteadOfAddingOne() throws Exception {
		service.storePassword(USERNAME, "first");
		String code = store.contents.keySet().iterator().next();

		service.storePassword(USERNAME, "second");

		assertThat(store.contents).hasSize(1);
		assertThat(store.contents).containsKey(code);
		assertThat(service.findRawPassword(USERNAME)).isEqualTo("second");
		assertThat(service.matches(USERNAME, "first")).isFalse();
	}

	@Test
	void twoUsersDoNotShareASecret() throws Exception {
		service.storePassword(USERNAME, "mine");
		service.storePassword("other@gebo.ai", "theirs");

		assertThat(store.contents).hasSize(2);
		assertThat(service.findRawPassword(USERNAME)).isEqualTo("mine");
		assertThat(service.findRawPassword("other@gebo.ai")).isEqualTo("theirs");
	}

	/** Mongo ids are case-sensitive, so two accounts differing only by case must not collide. */
	@Test
	void usernamesAreUsedVerbatim() throws Exception {
		service.storePassword("Someone@Gebo.ai", "upper");
		service.storePassword("someone@gebo.ai", "lower");

		assertThat(store.contents).hasSize(2);
		assertThat(service.findRawPassword("Someone@Gebo.ai")).isEqualTo("upper");
		assertThat(service.findRawPassword("someone@gebo.ai")).isEqualTo("lower");
	}

	@Test
	void deletingRemovesTheSecret() throws Exception {
		service.storePassword(USERNAME, "s3cret");

		service.deletePassword(USERNAME);

		assertThat(store.contents).isEmpty();
		assertThat(service.hasPassword(USERNAME)).isFalse();
	}

	/** Deleting a user that never had a password must not blow up the deletion of the user. */
	@Test
	void deletingAPasswordThatIsNotThereIsANoOp() {
		assertThat(store.contents).isEmpty();
		assertThatCode(() -> service.deletePassword(USERNAME)).doesNotThrowAnyException();
	}

	/**
	 * The {@code user:<username>} context is a naming convention shared with the ACL
	 * layer, so a secret of another type filed against the same user must be ignored,
	 * not mistaken for a password.
	 */
	@Test
	void ignoresSecretsOfAnotherTypeInTheSameContext() throws Exception {
		GeboTokenContent token = new GeboTokenContent();
		store.storeSecret(token, "an API token of this user", IGUserPasswordService.contextCodeOf(USERNAME));

		assertThat(service.hasPassword(USERNAME)).isFalse();
		assertThat(service.findRawPassword(USERNAME)).isNull();

		service.storePassword(USERNAME, "s3cret");

		// The token is untouched, and the password did not overwrite it.
		assertThat(store.contents).hasSize(2);
		assertThat(service.findRawPassword(USERNAME)).isEqualTo("s3cret");
	}

	/** A store that cannot answer fails the check; it does not fail the request. */
	@Test
	void aBrokenStoreIsAMismatchNotAnException() {
		store.failing = true;

		assertThat(service.matches(USERNAME, "s3cret")).isFalse();
		assertThat(service.hasPassword(USERNAME)).isFalse();
	}

	/** But a caller that is trying to WRITE must hear about it. */
	@Test
	void aBrokenStoreStillFailsAWrite() {
		store.failing = true;

		assertThatThrownBy(() -> service.storePassword(USERNAME, "s3cret"))
				.isInstanceOf(GeboCryptSecretException.class);
	}

	@Test
	void refusesAnEmptyUsernameOrANullPassword() {
		assertThatThrownBy(() -> service.storePassword(null, "s3cret"))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> service.storePassword("  ", "s3cret"))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> service.storePassword(USERNAME, null))
				.isInstanceOf(IllegalArgumentException.class);
	}

	/**
	 * An in-memory {@link IGeboSecretsAccessService}. Only the operations this service
	 * uses are implemented; the rest throw, so a future call added without a test
	 * shows up immediately.
	 */
	private static class FakeSecretsStore implements IGeboSecretsAccessService {

		final Map<String, AbstractGeboSecretContent> contents = new LinkedHashMap<>();
		final Map<String, String> contextCodes = new LinkedHashMap<>();
		final Map<String, String> descriptions = new LinkedHashMap<>();
		boolean failing = false;

		private void checkUp() throws GeboCryptSecretException {
			if (failing)
				throw new GeboCryptSecretException("the store is down");
		}

		@Override
		public List<String> getAllSecretsId() {
			return new ArrayList<>(contents.keySet());
		}

		@Override
		public AbstractGeboSecretContent getSecretContentById(String id) throws GeboCryptSecretException {
			checkUp();
			AbstractGeboSecretContent content = contents.get(id);
			if (content == null)
				throw new GeboCryptSecretException("Unkown secret with code=>" + id);
			return content;
		}

		@Override
		public <T extends GeboCustomSecretContent> T getCustomSecretContentById(String id, Class<T> type) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <SecretType extends AbstractGeboSecretContent> String storeSecret(SecretType secret, String description,
				String contextCode) throws GeboCryptSecretException {
			String code = UUID.randomUUID().toString();
			storeSecret(secret, description, contextCode, code);
			return code;
		}

		@Override
		public <SecretType extends AbstractGeboSecretContent> void storeSecret(SecretType secret, String description,
				String contextCode, String secretId) throws GeboCryptSecretException {
			checkUp();
			contents.put(secretId, secret);
			contextCodes.put(secretId, contextCode);
			descriptions.put(secretId, description);
		}

		@Override
		public <SecretType extends AbstractGeboSecretContent> void updateSecret(SecretType secret, String description,
				String contextCode, String code) throws GeboCryptSecretException {
			checkUp();
			if (!contents.containsKey(code))
				throw new GeboCryptSecretException("Secret with code=>" + code + " not found");
			contents.put(code, secret);
			contextCodes.put(code, contextCode);
			descriptions.put(code, description);
		}

		@Override
		public void deleteSecret(String code) throws GeboCryptSecretException {
			checkUp();
			contents.remove(code);
			contextCodes.remove(code);
			descriptions.remove(code);
		}

		@Override
		public List<SecretInfo> getSecretInfoByContextCode(String contextCode) throws GeboCryptSecretException {
			checkUp();
			List<SecretInfo> out = new ArrayList<>();
			for (Map.Entry<String, String> entry : contextCodes.entrySet()) {
				if (entry.getValue().equals(contextCode))
					out.add(infoOf(entry.getKey()));
			}
			return out;
		}

		@Override
		public SecretInfo getSecretInfoById(String code) throws GeboCryptSecretException {
			checkUp();
			return contents.containsKey(code) ? infoOf(code) : null;
		}

		private SecretInfo infoOf(String code) {
			SecretInfo info = new SecretInfo();
			info.setCode(code);
			info.setContextCode(contextCodes.get(code));
			info.setDescription(descriptions.get(code));
			info.setSecretType(contents.get(code).type());
			return info;
		}
	}
}
