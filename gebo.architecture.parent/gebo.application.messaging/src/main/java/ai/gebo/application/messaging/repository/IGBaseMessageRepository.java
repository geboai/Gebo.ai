/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Gebo.ai comment agent
 * Base interface for message repositories using MongoDB.
 * Specifies generic methods to perform searches based on message attributes.
 *
 * @param <MessageType> the type of message envelope managed by the repository
 */
@NoRepositoryBean
public interface IGBaseMessageRepository<MessageType extends GMessageEnvelope>
		extends MongoRepository<MessageType, String> {

	/**
	 * Finds messages by their source module and source component.
	 *
	 * @param sourceModule the source module of the message
	 * @param messageSource the source component of the message
	 * @return a Stream of messages matching the source module and component
	 */
	public Stream<MessageType> findBySourceModuleAndSourceComponent(String sourceModule, String messageSource);

	/**
	 * Finds messages by their source module.
	 *
	 * @param sourceModule the source module of the message
	 * @return a Stream of messages matching the source module
	 */
	public Stream<MessageType> findBySourceModule(String sourceModule);

	/**
	 * Finds messages by their target module and target component.
	 *
	 * @param targetModule the target module of the message
	 * @param messageTarget the target component of the message
	 * @return a Stream of messages matching the target module and component
	 */
	public Stream<MessageType> findByTargetModuleAndTargetComponent(String targetModule, String messageTarget);

	/**
	 * Finds messages by their target module.
	 *
	 * @param targetModule the target module of the message
	 * @return a Stream of messages matching the target module
	 */
	public Stream<MessageType> findByTargetModule(String targetModule);

	/**
	 * Finds messages by their source module, source component, target module, and target component.
	 *
	 * @param sourceModule the source module of the message
	 * @param messageSource the source component of the message
	 * @param targetModule the target module of the message
	 * @param messageTarget the target component of the message
	 * @return a Stream of messages matching the given sources and targets
	 */
	public Stream<MessageType> findBySourceModuleAndSourceComponentAndTargetModuleAndTargetComponent(
			String sourceModule, String messageSource, String targetModule, String messageTarget);
}