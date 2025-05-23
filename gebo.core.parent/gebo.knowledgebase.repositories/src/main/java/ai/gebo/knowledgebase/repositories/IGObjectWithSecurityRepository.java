/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.model.IGObjectWithSecurity;

/**
 * AI generated comments
 * 
 * This is a marker interface for repositories managing entities that implement the IGObjectWithSecurity interface.
 * It extends Spring Data's repository infrastructure, allowing the implementation of methods for custom behavior.
 * The @NoRepositoryBean annotation indicates that this interface should not be instantiated as a concrete repository bean.
 *
 * @param <SecurityType> A type parameter extending IGObjectWithSecurity which ensures that the repository will handle objects 
 *        with security features by providing security-related methods.
 */
@NoRepositoryBean
public interface IGObjectWithSecurityRepository<SecurityType extends IGObjectWithSecurity> {
	
}