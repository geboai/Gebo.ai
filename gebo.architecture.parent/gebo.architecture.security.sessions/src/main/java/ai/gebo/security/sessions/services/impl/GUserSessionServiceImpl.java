/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.sessions.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import ai.gebo.security.sessions.model.GUserSession;
import ai.gebo.security.sessions.model.GUserSession.Kind;
import ai.gebo.security.sessions.repository.GUserSessionRepository;
import ai.gebo.security.sessions.services.IGUserSessionService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;

/**
 * The sessions store over one Mongo collection. On the request path it does one
 * indexed lookup for the revocation check and, at most once a minute per
 * session, one upsert to refresh the record; a revocation is seen by the very
 * next request, nothing is cached.
 */
@Service
public class GUserSessionServiceImpl implements IGUserSessionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GUserSessionServiceImpl.class);
	/** A session's record is refreshed at most this often. */
	private static final long TOUCH_PERIOD_MILLIS = 60_000L;
	/** Records are dropped this long after their token expired. */
	private static final long KEEP_EXPIRED_MILLIS = 24L * 3600_000L;
	/** Records of tokens without a known expiration are dropped when unseen for this long. */
	private static final long KEEP_UNSEEN_MILLIS = 7L * 24L * 3600_000L;

	private final GUserSessionRepository repository;
	private final MongoTemplate mongoTemplate;
	private final LocalJwtTokenProvider tokenProvider;
	/** When each session was last refreshed, by this instance. */
	private final ConcurrentHashMap<String, Long> lastTouch = new ConcurrentHashMap<>();

	public GUserSessionServiceImpl(GUserSessionRepository repository, MongoTemplate mongoTemplate,
			LocalJwtTokenProvider tokenProvider) {
		this.repository = repository;
		this.mongoTemplate = mongoTemplate;
		this.tokenProvider = tokenProvider;
	}

	@PostConstruct
	void ensureIndexes() {
		try {
			mongoTemplate.indexOps(GUserSession.class).createIndex(new Index().on("username", Sort.Direction.ASC));
			mongoTemplate.indexOps(GUserSession.class).createIndex(new Index().on("lastSeen", Sort.Direction.DESC));
			mongoTemplate.indexOps(GUserSession.class).createIndex(new Index().on("expiresAt", Sort.Direction.ASC));
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot create the sessions indexes (the store works without them): {}", e.getMessage());
		}
	}

	@Override
	public String sessionIdOf(String tokenId, Map<String, Object> claims) {
		if (claims == null) {
			return null;
		}
		Object sessionId = claims.get(LocalJwtTokenProvider.SESSION_ID_CLAIM);
		if (sessionId != null) {
			return sessionId.toString();
		}
		if (Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.API_KEY_CLAIM))
				|| Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.OAUTH2_TOKEN_CLAIM))) {
			return tokenId;
		}
		return null;
	}

	private static Kind kindOf(Map<String, Object> claims) {
		if (Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.API_KEY_CLAIM))) {
			return Kind.API_KEY;
		}
		if (Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.OAUTH2_TOKEN_CLAIM))) {
			return Kind.OAUTH2;
		}
		return Kind.SESSION;
	}

	/** The expiration of the claims: jjwt keeps seconds, so do the external claims. */
	private static Date expirationOf(Map<String, Object> claims) {
		Object exp = claims.get("exp");
		if (exp instanceof Date date) {
			return date;
		}
		if (exp instanceof Number number) {
			return new Date(number.longValue() * 1000L);
		}
		return null;
	}

	private static String text(Object value) {
		return value != null ? value.toString() : null;
	}

	@Override
	public void touch(String tokenId, Map<String, Object> claims, String clientIp, String userAgent) {
		try {
			String sessionId = sessionIdOf(tokenId, claims);
			if (sessionId == null) {
				return;
			}
			long now = System.currentTimeMillis();
			Long previous = lastTouch.get(sessionId);
			if (previous != null && now - previous < TOUCH_PERIOD_MILLIS) {
				return;
			}
			lastTouch.put(sessionId, now);
			Date nowDate = new Date(now);
			Update update = new Update().set("lastSeen", nowDate).set("lastTokenId", tokenId)
					.set("lastClientIp", clientIp).set("lastUserAgent", userAgent)
					.setOnInsert("username", text(claims.get("sub"))).setOnInsert("kind", kindOf(claims).name())
					.setOnInsert("authType", text(claims.get(LocalJwtTokenProvider.AUTH_TYPE_CLAIM)))
					.setOnInsert("authProvider", text(claims.get(LocalJwtTokenProvider.AUTH_PROVIDER_REGISTRATION_ID) != null
							? claims.get(LocalJwtTokenProvider.AUTH_PROVIDER_REGISTRATION_ID)
							: claims.get(LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM)))
					.setOnInsert("firstSeen", nowDate).setOnInsert("dateCreated", nowDate)
					.setOnInsert("revoked", false);
			Date expiresAt = expirationOf(claims);
			if (expiresAt != null) {
				update.set("expiresAt", expiresAt);
			}
			mongoTemplate.upsert(Query.query(Criteria.where("_id").is(sessionId)), update, GUserSession.class);
			if (lastTouch.size() > 10_000) {
				lastTouch.entrySet().removeIf(e -> now - e.getValue() > TOUCH_PERIOD_MILLIS);
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot record the session of token {} (ignored): {}", tokenId, e.getMessage());
		}
	}

	@Override
	public boolean isRevoked(String sessionId) {
		if (sessionId == null) {
			return false;
		}
		return mongoTemplate.exists(Query.query(Criteria.where("_id").is(sessionId).and("revoked").is(true)),
				GUserSession.class);
	}

	@Override
	public GUserSession revoke(String sessionId, String revokedBy) {
		GUserSession session = sessionId != null ? repository.findById(sessionId).orElse(null) : null;
		if (session == null) {
			return null;
		}
		if (!session.isRevoked()) {
			session.setRevoked(true);
			session.setRevokedBy(revokedBy);
			session.setRevokedAt(new Date());
			session = repository.save(session);
		}
		return session;
	}

	@Override
	public int revokeAllOf(String username, String revokedBy) {
		if (username == null || username.isBlank()) {
			return 0;
		}
		List<GUserSession> live = repository.findByUsernameAndRevokedFalse(username.toLowerCase());
		int revoked = 0;
		for (GUserSession session : live) {
			if (revoke(session.getCode(), revokedBy) != null) {
				revoked++;
			}
		}
		return revoked;
	}

	@Override
	public GUserSession revokeOfToken(String token, String revokedBy) {
		if (token == null || token.isBlank()) {
			return null;
		}
		Claims claims = tokenProvider.parseClaims(token);
		String sessionId;
		if (claims != null) {
			sessionId = sessionIdOf(claims.getId(), claims);
		} else {
			// not minted here: an external token, listed under its hash
			sessionId = LocalJwtTokenProvider.tokenHash(token);
		}
		return revoke(sessionId, revokedBy);
	}

	@Override
	public Page<GUserSession> list(SessionsQuery query, Pageable pageable) {
		Criteria criteria = new Criteria();
		if (query != null) {
			if (query.getUsername() != null && !query.getUsername().isBlank()) {
				criteria = criteria.and("username").regex(Pattern.quote(query.getUsername().trim()), "i");
			}
			if (query.getKind() != null) {
				criteria = criteria.and("kind").is(query.getKind().name());
			}
			if (query.getActiveWithinMinutes() != null && query.getActiveWithinMinutes() > 0) {
				criteria = criteria.and("lastSeen")
						.gte(new Date(System.currentTimeMillis() - query.getActiveWithinMinutes() * 60_000L));
			}
			if (!query.isIncludeRevoked()) {
				criteria = criteria.and("revoked").is(false);
			}
			if (!query.isIncludeExpired()) {
				criteria = criteria.orOperator(Criteria.where("expiresAt").gt(new Date()),
						Criteria.where("expiresAt").is(null));
			}
		}
		Query mongoQuery = Query.query(criteria);
		long total = mongoTemplate.count(mongoQuery, GUserSession.class);
		Pageable page = pageable.getSort().isSorted() ? pageable
				: org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
						Sort.by(Sort.Direction.DESC, "lastSeen"));
		List<GUserSession> content = mongoTemplate.find(mongoQuery.with(page), GUserSession.class);
		return new PageImpl<>(content, page, total);
	}

	@Override
	public long countActive(int withinMinutes) {
		Criteria criteria = Criteria.where("revoked").is(false).and("lastSeen")
				.gte(new Date(System.currentTimeMillis() - Math.max(1, withinMinutes) * 60_000L));
		return mongoTemplate.count(Query.query(criteria), GUserSession.class);
	}

	/** Drops the records nobody can act on any more. */
	@Scheduled(fixedDelay = 3600_000L, initialDelay = 300_000L)
	public void purgeExpired() {
		try {
			Date now = new Date();
			Criteria gone = new Criteria().orOperator(
					Criteria.where("expiresAt").lt(new Date(now.getTime() - KEEP_EXPIRED_MILLIS)),
					Criteria.where("expiresAt").is(null).and("lastSeen")
							.lt(new Date(now.getTime() - KEEP_UNSEEN_MILLIS)));
			long removed = mongoTemplate.remove(Query.query(gone), GUserSession.class).getDeletedCount();
			if (removed > 0) {
				LOGGER.info("Purged {} expired session record(s)", removed);
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot purge the expired sessions: {}", e.getMessage());
		}
	}
}
