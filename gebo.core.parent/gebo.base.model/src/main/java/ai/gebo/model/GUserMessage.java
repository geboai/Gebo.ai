/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.document.utils.FastHashUtil;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * Represents a user message entity that can be stored in MongoDB. This class
 * provides functionality for creating and managing user messages with different
 * severity levels, and includes factory methods for common message types.
 */
@Document
public class GUserMessage implements Serializable {

	/**
	 * Enum for defining the severity levels of the user messages.
	 */
	public static enum MsgServerity {
		info, warn, error, success
	};

	@NotNull
	private MsgServerity severity = null;

	@NotNull
	private String summary = null;

	@NotNull
	private String detail = null;

	@NotNull
	@Id
	private String id = null;

	private String key = null;

	@NotNull
	@HashIndexed
	@Order(value = 1)
	private String jobId = null; // Identifier for correlating the message with a specific job

	@NotNull
	@Order(value = 2)
	private Long timestamp = null; // Timestamp indicating when the message was created

	/**
	 * Default constructor that initializes the id with a random UUID.
	 */
	public GUserMessage() {
		this.recalculateId();
	}

	private void recalculateId() {
		this.id = FastHashUtil.hash(ifNullPlusElseUppercase(jobId), ifNullPlusElseUppercase(key),
				ifNullPlusElseUppercase(summary), ifNullPlusElseUppercase(detail),
				ifNullPlusElseUppercase(severity != null ? severity.name() : null));
	}

	private String ifNullPlusElseUppercase(String value) {
		return value != null ? value.toUpperCase() : "+";
	}

	/**
	 * Constructs a message with specified parameters.
	 *
	 * @param id       Unique identifier for the message.
	 * @param key      Key associated with the message.
	 * @param summary  Brief summary of the message content.
	 * @param detail   Detailed information about the message.
	 * @param severity Severity level of the message.
	 */
	public GUserMessage(String key, String summary, String detail, MsgServerity severity) {
		this.key = key;
		this.summary = summary;
		this.detail = detail;
		this.severity = severity;
		this.timestamp = System.currentTimeMillis();
		this.recalculateId();
	}

	/**
	 * Factory method to create a success message.
	 *
	 * @param summary Brief summary of the message.
	 * @param detail  Detailed information about the message.
	 * @return A success severity user message.
	 */
	public static GUserMessage successMessage(String summary, String detail) {
		return new GUserMessage("GUserMessage", summary, detail, MsgServerity.success);
	}

	/**
	 * Factory method to create an error message.
	 *
	 * @param summary Brief summary of the error.
	 * @param detail  Detailed information about the error.
	 * @return An error severity user message.
	 */
	public static GUserMessage errorMessage(String summary, String detail) {
		return new GUserMessage("GUserMessage", summary, detail, MsgServerity.error);
	}

	/**
	 * Factory method to create an error message from a throwable.
	 *
	 * @param summary Brief summary of the error.
	 * @param th      Throwable containing stack trace information.
	 * @return An error severity user message with stack trace details.
	 */
	public static GUserMessage errorMessage(String summary, Throwable th) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos);
		th.printStackTrace(pw);
		pw.flush();
		try {
			bos.flush();
		} catch (IOException e) {
			// Handle potential IOException silently
		}
		return new GUserMessage("GUserMessage", summary, "Server error: " + th.getMessage(), MsgServerity.error);
	}

	/**
	 * Factory method to create an info message.
	 *
	 * @param summary Brief summary of the message.
	 * @param detail  Detailed information about the message.
	 * @return An info severity user message.
	 */
	public static GUserMessage infoMessage(String summary, String detail) {
		return new GUserMessage("GUserMessage", summary, detail, MsgServerity.info);
	}

	/**
	 * Factory method to create a warning message.
	 *
	 * @param summary Brief summary of the warning.
	 * @param detail  Detailed information about the warning.
	 * @return A warning severity user message.
	 */
	public static GUserMessage warnMessage(String summary, String detail) {
		return new GUserMessage("GUserMessage", summary, detail, MsgServerity.warn);
	}

	/**
	 * Gets the severity of the message.
	 *
	 * @return The severity of the message.
	 */
	public MsgServerity getSeverity() {
		return severity;
	}

	/**
	 * Sets the severity of the message.
	 *
	 * @param severity The severity level to set.
	 */
	public void setSeverity(MsgServerity severity) {
		this.severity = severity;
	}

	/**
	 * Gets the summary of the message.
	 *
	 * @return The summary of the message.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary of the message.
	 *
	 * @param summary The summary text to set.
	 */
	public void setSummary(String summary) {
		this.summary = summary;
		this.recalculateId();
	}

	/**
	 * Gets the detail of the message.
	 *
	 * @return The detailed information of the message.
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Sets the detailed message.
	 *
	 * @param detail The detail text to set.
	 */
	public void setDetail(String detail) {
		this.detail = detail;
		this.recalculateId();
	}

	/**
	 * Gets the unique identifier of the message.
	 *
	 * @return The unique identifier of the message.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the message.
	 *
	 * @param id The unique identifier to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the key of the message.
	 *
	 * @return The key of the message.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key of the message.
	 *
	 * @param key The key to set for the message.
	 */
	public void setKey(String key) {
		this.key = key;
		this.recalculateId();
	}

	/**
	 * Gets the jobId associated with the message.
	 *
	 * @return The jobId.
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Sets the jobId for the message.
	 *
	 * @param jobId The jobId to associate with the message.
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
		this.recalculateId();
	}

	/**
	 * Gets the timestamp of when the message was created.
	 *
	 * @return The creation timestamp.
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp of the message.
	 *
	 * @param timestamp The timestamp to set.
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}