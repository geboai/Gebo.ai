/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.repository;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * AI generated comments Service class that implements the
 * ReactiveClientRegistrationRepository interface. Responsible for retrieving
 * OAuth2 client registrations dynamically in a reactive manner.
 */
@AllArgsConstructor
public class Oauth2DynamicReactiveRegistrationRepository implements ReactiveClientRegistrationRepository {

	// Repository for dynamic OAuth2 client registrations
	final Oauth2DynamicClientRegistrationRepository repo;

	/**
	 * Finds a ClientRegistration by its registration ID in a reactive way.
	 * 
	 * @param registrationId the registration ID of the client.
	 * @return a Mono emitting the ClientRegistration object corresponding to the
	 *         provided registration ID.
	 */
	@Override
	public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
		// Retrieve the client registration by registration ID and wrap it in a Mono
		return Mono.just(repo.findByRegistrationId(registrationId));
	}

}