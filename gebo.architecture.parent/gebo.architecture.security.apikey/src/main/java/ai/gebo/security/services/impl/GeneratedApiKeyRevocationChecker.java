/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.security.repository.GeneratedApiKeyRepository;
import ai.gebo.security.services.IGIssuedTokenRevocationChecker;
import lombok.AllArgsConstructor;

/**
 * A generated API key is accepted while its record exists: deleting the key
 * from the administrative or the personal keys page revokes it on the next
 * request, before its expiration. One indexed lookup per request, no cache, so
 * the revocation is immediate as the pages promise.
 */
@Service
@AllArgsConstructor
public class GeneratedApiKeyRevocationChecker implements IGIssuedTokenRevocationChecker {

	private final GeneratedApiKeyRepository repository;

	@Override
	public boolean isRevoked(String tokenId, Map<String, Object> claims) {
		if (!Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.API_KEY_CLAIM))) {
			// not an API key: somebody else's token id
			return false;
		}
		return !repository.existsByTokenId(tokenId);
	}
}
