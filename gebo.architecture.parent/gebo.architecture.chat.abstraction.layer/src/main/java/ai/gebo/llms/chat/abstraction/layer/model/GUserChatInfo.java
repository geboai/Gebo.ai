package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;

/**
 * Interface for representing user chat information. 
 * Provides getter methods to access chat-related data.
 */
public interface GUserChatInfo {

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