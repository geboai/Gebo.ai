/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.GUserMessage.MsgServerity;

/**
 * AI generated comments
 * Represents the status of an operation that may include a result and a list of messages.
 * 
 * @param <Type> the type of the result object 
 */
public class OperationStatus<Type> {

    // Holds the result of an operation, can be null if operation failed
	private Type result = null;
	
	// List of messages related to the operation, such as errors or warnings
	private List<GUserMessage> messages = new ArrayList<GUserMessage>();

    /**
     * Default constructor that initializes an empty OperationStatus.
     */
	public OperationStatus() {

	}

    /**
     * Returns the result of the operation.
     * 
     * @return the result
     */
	public Type getResult() {
		return result;
	}

    /**
     * Sets the result of the operation.
     * 
     * @param result the result to set
     */
	public void setResult(Type result) {
		this.result = result;
	}

    /**
     * Returns the list of messages related to the operation.
     * 
     * @return the list of messages
     */
	public List<GUserMessage> getMessages() {
		return messages;
	}

    /**
     * Sets the list of messages related to the operation.
     * 
     * @param messages the list of messages to set
     */
	public void setMessages(List<GUserMessage> messages) {
		this.messages = messages;
	}

    /**
     * Checks if there are any error messages in the list of messages.
     * 
     * @return true if there are error messages, false otherwise
     */
	public boolean isHasErrorMessages() {
		if (this.messages == null)
			return false;
		return this.messages.stream().filter(x -> x.getSeverity() != null && x.getSeverity() == MsgServerity.error)
				.findFirst().isPresent();
	}

    /**
     * Checks if there are any warning messages in the list of messages.
     * 
     * @return true if there are warning messages, false otherwise
     */
	public boolean isHasWarnMessages() {
		if (this.messages == null)
			return false;
		return this.messages.stream().filter(x -> x.getSeverity() != null && x.getSeverity() == MsgServerity.warn)
				.findFirst().isPresent();
	}

    /**
     * Creates an OperationStatus with the given result and a default success message.
     * 
     * @param <T> the type of the result
     * @param t the result to set
     * @return a new OperationStatus with the result
     */
	public static <T> OperationStatus<T> of(T t) {
		OperationStatus<T> o = new OperationStatus<T>();
		o.setResult(t);
		o.messages.add(GUserMessage.successMessage("OK!", "Operation done successfully"));
		return o;
	}

    /**
     * Creates an OperationStatus for a given Throwable, including an error message.
     * 
     * @param <T> the type of the result
     * @param t the Throwable to handle
     * @return a new OperationStatus with an error message
     */
	public static <T> OperationStatus<T> of(Throwable t) {
		OperationStatus<T> o = new OperationStatus<T>();
		o.setResult(null);
		o.messages.add(GUserMessage.errorMessage("Exception during operation:" + t.getMessage(), t));
		return o;
	}

    /**
     * Creates an OperationStatus with a custom error message.
     * 
     * @param <T> the type of the result
     * @param summary the summary of the error
     * @param detail the detail of the error
     * @return a new OperationStatus with the error message
     */
	public static <T> OperationStatus<T> ofError(String summary, String detail) {
		OperationStatus<T> os = new OperationStatus<T>();
		os.messages.add(GUserMessage.errorMessage(summary, detail));
		return os;
	}

    /**
     * Creates an OperationStatus with a default error message and the provided detail.
     * 
     * @param <T> the type of the result
     * @param detail the detail of the error
     * @return a new OperationStatus with the error message
     */
	public static <T> OperationStatus<T> ofError(String detail) {

		return ofError("Operation failed", detail);
	}

    /**
     * Creates an OperationStatus with a result and a list of messages.
     * 
     * @param <T> the type of the result
     * @param out the result to set
     * @param messages the list of messages to include
     * @return a new OperationStatus with the result and messages
     */
	public static <T> OperationStatus<T> of(T out, List<GUserMessage> messages) {
		OperationStatus<T> data = of(out);
		data.messages.clear();
		data.messages.addAll(messages);
		return data;
	}

    /**
     * Creates an OperationStatus with a result and a single message.
     * 
     * @param <T> the type of the result
     * @param out the result to set
     * @param message the message to include
     * @return a new OperationStatus with the result and message
     */
	public static <T> OperationStatus<T> of(T out, GUserMessage message) {
		OperationStatus<T> data = of(out);
		data.messages.clear();
		data.messages.add(message);
		return data;
	}
}