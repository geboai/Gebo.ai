/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.repository;

import java.util.Date;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;

/**
 * Gebo.ai comment agent
 * Repository interface for managing GUserChatContext entities with MongoDB. 
 * Extends the generic IGBaseMongoDBRepository providing additional methods 
 * for specific query operations.
 */
public interface GUserChatContextRepository extends IGBaseMongoDBRepository<GUserChatContext> {

	/**
	 * Returns the managed type of the repository, which is GUserChatContext.
	 * This method overrides the default implementation from the parent interface.
	 * 
	 * @return the class type GUserChatContext
	 */
	@Override
	default Class<GUserChatContext> getManagedType() {
		return GUserChatContext.class;
	}

	/**
	 * Interface for representing user chat information. 
	 * Provides getter methods to access chat-related data.
	 */
	public static interface GUserChatInfo {

		/**
		 * Gets the code of the chat.
		 *
		 * @return a String representing the chat code.
		 */
		String getCode();

		/**
		 * Gets the username associated with the chat.
		 *
		 * @return a String representing the username.
		 */
		String getUsername();

		/**
		 * Gets the description of the chat.
		 *
		 * @return a String providing the chat description.
		 */
		String getDescription();

		/**
		 * Gets the creation date and time of the chat.
		 *
		 * @return a Date object representing when the chat was created.
		 */
		Date getChatCreationDateTime();

		/**
		 * Gets the profile code of the chat.
		 *
		 * @return a String representing the profile code.
		 */
		public String getChatProfileCode();

		/**
		 * Checks if the chat has a RAG (red-amber-green) status.
		 *
		 * @return a Boolean indicating if the chat has RAG status.
		 */
		public Boolean getRagChat();

		/**
		 * Gets the chat model code.
		 *
		 * @return a String representing the model code of the chat.
		 */
		public String getChatModelCode();
	}

	/**
	 * Finds all chat information based on the given example and pageable parameters.
	 *
	 * @param qbe an Example of GUserChatContext to use for query by example.
	 * @param page a Pageable object to manage pagination information.
	 * @return a Page of GUserChatInfo data.
	 */
	public Page<GUserChatInfo> findAllBy(Example<GUserChatContext> qbe, Pageable page);

	/**
	 * Finds chat information by the given username and pageable parameters.
	 *
	 * @param username a String representing the username to search for.
	 * @param page a Pageable object to manage pagination.
	 * @return a Page of GUserChatInfo data for the specified username.
	 */
	public Page<GUserChatInfo> findByUsername(String username, Pageable page);

}