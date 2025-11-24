/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.utils.DataPage;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfoData;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GLookupEntry;
import ai.gebo.security.services.IGSecurityService;

/**
 * AI generated comments
 * 
 * REST controller for managing user chat operations like retrieving, updating,
 * and deleting chat contexts. Provides endpoints for listing, searching, and
 * managing user chat histories.
 */
@RestController
@RequestMapping(path = "api/users/GeboUserChatsController")
public class GeboUserChatsController {
	@Autowired
	GUserChatContextRepository repository;
	@Autowired
	IGSecurityService securityService;

	/**
	 * Default constructor for GeboUserChatsController.
	 */
	public GeboUserChatsController() {

	}

	/**
	 * Parameter class for filtering chat information using Query By Example
	 * pattern. Contains a filter object and pagination information.
	 */
	public static class ChatInfosByQbeParam {
		public GUserChatContext filter = new GUserChatContext();
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
		Optional<GUserChatContext> optional = repository.findById(id);
		if (optional.isPresent()) {
			GUserChatContext data = optional.get();
			securityService.checkBeingCreator(data);
			return new GUserChatInfoData(data);
		}
		return null;
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
	 * Deletes multiple user chats by their IDs.
	 * 
	 * @param ids List of chat IDs to delete
	 */
	@PostMapping(value = "deleteUserChats", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserChats(@RequestBody List<String> ids) {
		repository.deleteAllById(ids);
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
		public static UserChatHistory from(GUserChatContext context) throws CloneNotSupportedException {
			UserChatHistory history = new UserChatHistory();
			history.setCode(context.getCode());
			history.setDescription(context.getDescription());
			history.setInteractions(getClientViewOf(context.getInteractions()));
			return history;
		}

		private static List<ChatInteractions> getClientViewOf(List<ChatInteractions> interactions)
				throws CloneNotSupportedException {

			List<ChatInteractions> cinteractions = new ArrayList<>();
			if (interactions != null) {
				for (ChatInteractions chatInteraction : interactions) {
					ChatInteractions clientVision = chatInteraction.clientClone();
					cinteractions.add(clientVision);
				}
			}
			return cinteractions;
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
		Optional<GUserChatContext> optional = repository.findById(code);
		if (optional.isPresent()) {
			GUserChatContext uc = optional.get();
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
		Optional<GUserChatContext> data = repository.findById(entry.getCode());
		if (data.isPresent()) {
			GUserChatContext uc = data.get();
			uc.setDescription(entry.getDescription());
			repository.save(uc);
			return GLookupEntry.of(uc);
		} else
			throw new IllegalStateException("Chat not found with this code");
	}
}