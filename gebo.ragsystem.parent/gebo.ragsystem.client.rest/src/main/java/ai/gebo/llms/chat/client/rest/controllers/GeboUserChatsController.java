/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfoData;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GLookupEntry;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * REST controller for managing user chat operations like retrieving, updating,
 * and deleting chat contexts. Provides endpoints for listing, searching, and
 * managing user chat histories.
 */
@RestController
@RequestMapping(path = "api/users/GeboUserChatsController")
@AllArgsConstructor
public class GeboUserChatsController {
	final GUserChatSessionRepository repository;
	final IGSecurityService securityService;
	final IGChatStorageAreaService chatStorageAreaService;
	final IGChatSessionLifeCycleService sessionLifeCycleService;

	/**
	 * Parameter class for filtering chat information using Query By Example
	 * pattern. Contains a filter object and pagination information.
	 */
	public static class ChatInfosByQbeParam {
		public GUserChatSession filter = new GUserChatSession();
		public DataPage page = new DataPage();
	};

	/**
	 * Retrieves chat information based on filter criteria using Query By Example
	 * pattern. Only returns chats belonging to the currently authenticated user.
	 * 
	 * @param param Contains filter criteria and pagination information
	 * @return A page of chat information matching the criteria
	 * @throws GeboChatException If there's an error retrieving chat information
	 */
	@PostMapping(value = "getChatInfosByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GUserChatInfo> getChatInfosByQbe(@RequestBody ChatInfosByQbeParam param) throws GeboChatException {
		param.filter.setUsername(securityService.getCurrentUser().getUsername());

		return repository.findAllBy(Example.of(param.filter), param.page.toPageable());
	}

	@GetMapping(value = "getChatInfosByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GUserChatInfo getChatInfosByCode(@RequestParam("id") String id) {
		Optional<GUserChatSession> optional = repository.findById(id);
		if (optional.isPresent()) {
			GUserChatSession data = optional.get();
			securityService.checkBeingCreator(data);
			return new GUserChatInfoData(data);
		}
		return null;
	}

	@GetMapping(value = "getMyChats", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GUserChatInfo> getMyChats() {
		return repository.findByUsername(securityService.getCurrentUser().getUsername());
	}

	/**
	 * Retrieves a paginated list of chat information for the current user.
	 * 
	 * @param page     The page number to retrieve
	 * @param pageSize The number of items per page
	 * @return A page of chat information for the current user
	 * @throws GeboChatException If there's an error retrieving chat information
	 */
	@GetMapping(value = "getMyChatsPaged", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GUserChatInfo> getMyChatsPaged(@RequestParam("page") Integer page,
			@RequestParam("pageSize") Integer pageSize) throws GeboChatException {
		DataPage _page = new DataPage();
		_page.setPage(page);
		_page.setPageSize(pageSize);

		return repository.findByUsername(securityService.getCurrentUser().getUsername(), _page.toPageable());
	}

	/**
	 * Data transfer object for chat history information. Contains only the
	 * necessary information to be sent to the client.
	 */
	public static class UserChatHistory extends GBaseObject {
		private List<ChatInteractions> interactions = null;

		public List<ChatInteractions> getInteractions() {
			return interactions;
		}

		public void setInteractions(List<ChatInteractions> interactions) {
			this.interactions = interactions;
		}

		/**
		 * Creates a UserChatHistory object from a GUserChatContext object.
		 * 
		 * @param context The chat context to convert
		 * @return A new UserChatHistory with data from the provided context
		 * @throws CloneNotSupportedException
		 */
		public static UserChatHistory from(GUserChatSession context) throws CloneNotSupportedException {
			UserChatHistory history = new UserChatHistory();
			history.setCode(context.getCode());
			history.setDescription(context.getDescription());
			history.setInteractions(context.getInteractions());
			return history;
		}

	}

	/**
	 * Retrieves chat history for a specific chat by its code. Ensures the
	 * requesting user has permission to access the chat.
	 * 
	 * @param code The unique identifier of the chat
	 * @return The chat history if found and accessible, null otherwise
	 * @throws CloneNotSupportedException
	 * @throws SecurityException          If the user tries to access a chat that
	 *                                    doesn't belong to them
	 */
	@GetMapping(value = "getChatHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserChatHistory getChatHistory(@RequestParam("code") String code) throws CloneNotSupportedException {
		Optional<GUserChatSession> optional = repository.findById(code);
		if (optional.isPresent()) {
			GUserChatSession uc = optional.get();
			String currUN = securityService.getCurrentUser().getUsername();
			if (uc.getUsername().equals(currUN))
				return UserChatHistory.from(uc);
			else
				throw new SecurityException("Trying to access the wrong chat history");
		} else
			return null;
	}

	/**
	 * Updates the description of a chat context.
	 * 
	 * @param entry Contains the chat code and new description
	 * @return The updated chat information as a lookup entry
	 * @throws IllegalStateException If the chat with the specified code doesn't
	 *                               exist
	 */
	@PostMapping(value = "changeChatDescription", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GLookupEntry changeChatDescription(@RequestBody GLookupEntry entry) {
		Optional<GUserChatSession> data = repository.findById(entry.getCode());
		if (data.isPresent()) {
			GUserChatSession uc = data.get();
			uc.setDescription(entry.getDescription());
			repository.save(uc);
			return GLookupEntry.of(uc);
		} else
			throw new IllegalStateException("Chat not found with this code");
	}

	@GetMapping(value = "createCleanChatByModelCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GUserChatInfo createCleanChatByModelCode(
			@RequestParam(value = "modelCode", required = true) String modelCode) throws GeboPersistenceException {
		return this.sessionLifeCycleService.createCleanChatByModelCode(modelCode);
	}

	@GetMapping(value = "createCleanChatByChatProfileCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GUserChatInfo createCleanChatByChatProfileCode(
			@RequestParam(value = "chatProfileCode", required = true) String chatProfileCode)
			throws GeboPersistenceException {
		return this.sessionLifeCycleService.createCleanChatByChatProfileCode(chatProfileCode);
	}

	@DeleteMapping("deleteChat")
	public void deleteChat(@RequestParam("userChatContextCode") String userChatContextCode)
			throws GeboChatSessionLifecycleException {
		this.sessionLifeCycleService.removeChatSession(userChatContextCode);
	}

}