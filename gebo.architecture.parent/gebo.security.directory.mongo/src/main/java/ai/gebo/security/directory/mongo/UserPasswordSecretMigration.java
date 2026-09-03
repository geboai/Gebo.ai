/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.directory.mongo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.security.model.User;
import ai.gebo.security.services.IGUserPasswordService;

/**
 * Moves the passwords of an <b>already-deployed</b> installation out of the user
 * documents and into the secret store, once, at startup.
 *
 * <h2>What it is for</h2>
 * <p>
 * {@link User} used to carry an encrypted {@code password} field. It no longer has
 * one, and the mapping layer simply ignores the leftover field - which means that
 * without this class an existing installation would come up with every local
 * account silently unable to log in, and with the old credentials still sitting in
 * the {@code users} collection, unreferenced and unnoticed. Both halves matter: the
 * point is not only to make logins work again, it is to <b>remove</b> the
 * credential from where it no longer belongs.
 * </p>
 *
 * <h2>Why it reads raw documents</h2>
 * <p>
 * It cannot go through {@code UserRepository}: the field it needs is precisely the
 * one the entity stopped declaring, so a mapped read would hand back a {@link User}
 * with nothing to migrate. It therefore reads {@code org.bson.Document}s straight
 * out of the collection {@code User} maps to - resolved through
 * {@link MongoTemplate#getCollectionName} rather than hard-coded, so a future
 * {@code @Document("...")} rename cannot leave this silently scanning nothing.
 * </p>
 *
 * <h2>Its guarantees</h2>
 * <ul>
 * <li><b>Idempotent.</b> It selects only documents that still <i>have</i> the field
 * and unsets it as the last step of each user, so a second start finds nothing and
 * does nothing. Re-running it is free.</li>
 * <li><b>Unset only after the secret is written.</b> A crash between the two leaves
 * the old field in place and the migration simply runs again; the reverse order
 * would lose the password outright.</li>
 * <li><b>A user it cannot decrypt is left alone.</b> Its field stays, it is logged
 * as an error, and the rest of the users still migrate. Unsetting it would destroy
 * the only copy of a credential that a corrected keystore might still recover;
 * leaving it costs one loud error per restart, which is the right way round.</li>
 * <li><b>A start is never failed by it.</b> Every failure is contained per user, and
 * a failure of the whole pass is logged rather than thrown - a service that refuses
 * to boot helps nobody log in.</li>
 * </ul>
 *
 * <p>
 * It runs on {@link ApplicationReadyEvent}, not {@code ContextRefreshedEvent}:
 * {@code GeboCryptingServiceImpl} loads its keystore <i>on</i>
 * {@code ContextRefreshedEvent}, so a listener of that same event could be called
 * before the crypting service can decrypt anything - the ordering between two
 * listeners of one event being exactly the kind of thing that works on one service
 * and not on the next.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class UserPasswordSecretMigration implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPasswordSecretMigration.class);

	/** The field {@code User} used to declare, and that this migration drains. */
	static final String LEGACY_PASSWORD_FIELD = "password";

	private static final String ID_FIELD = "_id";

	private final MongoTemplate mongoTemplate;
	private final IGeboCryptingService cryptService;
	private final IGUserPasswordService userPasswordService;

	public UserPasswordSecretMigration(MongoTemplate mongoTemplate, IGeboCryptingService cryptService,
			IGUserPasswordService userPasswordService) {
		this.mongoTemplate = mongoTemplate;
		this.cryptService = cryptService;
		this.userPasswordService = userPasswordService;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			migrate();
		} catch (RuntimeException e) {
			// Never take the service down over a data migration.
			LOGGER.error("The legacy user-password migration failed. Local password logins may not work until "
					+ "this is resolved; the passwords themselves are still in the user documents.", e);
		}
	}

	/**
	 * Runs one pass. Safe to call again at any time.
	 *
	 * @return how many users were moved to the secret store on this pass
	 */
	public int migrate() {
		String collection = mongoTemplate.getCollectionName(User.class);
		Query query = new Query(Criteria.where(LEGACY_PASSWORD_FIELD).exists(true));
		List<org.bson.Document> legacy = mongoTemplate.find(query, org.bson.Document.class, collection);
		if (legacy.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No user document in '{}' still carries a '{}' field - nothing to migrate", collection,
						LEGACY_PASSWORD_FIELD);
			}
			return 0;
		}

		LOGGER.info("Migrating the passwords of {} user(s) from the '{}' collection into the secret store", legacy.size(),
				collection);
		int migrated = 0;
		int cleared = 0;
		int failed = 0;
		for (org.bson.Document document : legacy) {
			String username = document.get(ID_FIELD) == null ? null : String.valueOf(document.get(ID_FIELD));
			if (username == null || username.trim().isEmpty()) {
				LOGGER.error("A document in '{}' carries a legacy '{}' but no _id - skipped", collection,
						LEGACY_PASSWORD_FIELD);
				failed++;
				continue;
			}
			Object rawField = document.get(LEGACY_PASSWORD_FIELD);
			String crypted = rawField == null ? null : String.valueOf(rawField);
			if (crypted == null || crypted.trim().isEmpty()) {
				// Nothing to carry over - an OAuth2/LDAP identity, or a field left null. The
				// dead field still goes, which is half of what this migration is for.
				unsetLegacyPassword(collection, username);
				cleared++;
				continue;
			}
			try {
				// Decrypted here rather than moved across as-is: the secret store applies its
				// own encryption (and, where configured, an external vault). Handing it the old
				// ciphertext would store a value only GPasswordEncoder knows how to read,
				// wrapped inside the store's encryption - opaque to every operation the store
				// offers, key rotation included.
				String plain = cryptService.decrypt(crypted);
				userPasswordService.storePassword(username, plain);
				// Only now: see the class comment on ordering.
				unsetLegacyPassword(collection, username);
				migrated++;
			} catch (GeboCryptSecretException | RuntimeException e) {
				failed++;
				LOGGER.error("Cannot migrate the password of user '{}': its legacy value is left in place and this "
						+ "user cannot log in with a password until the cause is fixed (the migration retries on "
						+ "every start).", username, e);
			}
		}
		LOGGER.info("Legacy user-password migration finished: {} moved to the secret store, {} empty field(s) removed, "
				+ "{} left for a later attempt", migrated, cleared, failed);
		return migrated;
	}

	private void unsetLegacyPassword(String collection, String username) {
		mongoTemplate.updateFirst(new Query(Criteria.where(ID_FIELD).is(username)),
				new Update().unset(LEGACY_PASSWORD_FIELD), collection);
	}
}
