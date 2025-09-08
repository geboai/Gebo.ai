/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.application.messaging.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.SystemComponentType;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments Represents a generic message envelope for the messaging
 * system.
 * 
 * @param <PayloadType> The type of the payload contained in the message
 *                      envelope.
 */
@Document
public class GMessageEnvelope<PayloadType extends IGMessagePayloadType> implements Serializable {

	// The timestamp indicating when the message was created or sent.
	private Date timestamp = null;
	// The ID of the user associated with the message, if any.
	private String userId = null;
	// The originating component of the message.
	@HashIndexed
	private @NotNull String sourceComponent = null;
	// The type of the originating system component.
	private @NotNull SystemComponentType sourceType = null;
	// The target component to which the message is destined.
	@HashIndexed
	private @NotNull String targetComponent = null;
	// The type of the target system component.
	private @NotNull SystemComponentType targetType = null;
	// The actual payload data of the message.
	private @NotNull PayloadType payload = null;
	// The type of the payload.
	private @NotNull String payloadType = null;
	// The module within the source component.
	@HashIndexed
	private @NotNull String sourceModule = null;
	// The module within the target component.
	@HashIndexed
	private @NotNull String targetModule = null;
	// The unique identifier for the message.
	@Id
	private @NotNull String id = null;
	// The number of times the message has been retried.
	private @NotNull Integer retry = null;
	// Indicates if the message has been delivered.
	private Boolean delivered = null;
	// Indicates if the message has been processed.
	private Boolean processed = null;
	// Eventual workflowId and workflowStepId for workflow step message
	private GWorkflowType workflowType = null;
	private String workflowId = null;
	private String workflowStepId = null;
	// components to forward processed results or current message
	private List<GMessagingComponentRef> onProcessForwardDestionations = null;

	/**
	 * Default constructor for GMessageEnvelope.
	 */
	public GMessageEnvelope() {

	}

	/**
	 * Creates a copy of the given GMessageEnvelope.
	 * 
	 * @param env The GMessageEnvelope to copy.
	 * @param <T> The type of the payload.
	 * @return A new instance of GMessageEnvelope with the same properties as the
	 *         provided one.
	 */
	public static <T extends IGMessagePayloadType> GMessageEnvelope<T> copyOf(GMessageEnvelope<T> env) {
		GMessageEnvelope<T> dest = new GMessageEnvelope<T>();
		dest.sourceComponent = env.getSourceComponent();
		dest.targetComponent = env.getTargetComponent();
		dest.targetModule = env.getTargetModule();
		dest.sourceModule = env.getSourceModule();
		dest.payloadType = env.getPayloadType();
		dest.payload = env.getPayload();
		dest.userId = env.getUserId();
		dest.timestamp = env.getTimestamp();
		dest.delivered = env.getDelivered();
		dest.processed = env.getProcessed();
		dest.id = env.getId();
		return dest;
	}

	/**
	 * Constructs a new message from the given system and payload.
	 * 
	 * @param system  The system emitting the message.
	 * @param payload The payload to be contained in the message.
	 * @param <T>     The type of the payload.
	 * @return A new instance of GMessageEnvelope.
	 */
	public static <T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system,
			T payload) {
		GMessageEnvelope<T> dest = new GMessageEnvelope<T>();
		dest.sourceComponent = system.getMessagingSystemId();
		dest.sourceModule = system.getMessagingModuleId();
		dest.payloadType = payload.getClass().getName();
		dest.timestamp = new Date();
		dest.payload = payload;
		dest.delivered = false;
		dest.processed = false;
		dest.id = UUID.randomUUID().toString();
		return dest;
	}

	/**
	 * Constructs a new message from the given system, payload, and user.
	 * 
	 * @param system  The system emitting the message.
	 * @param payload The payload to be contained in the message.
	 * @param user    The ID of the user associated with the message.
	 * @param <T>     The type of the payload.
	 * @return A new instance of GMessageEnvelope.
	 */
	public static <T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system,
			T payload, String user) {
		GMessageEnvelope<T> dest = new GMessageEnvelope<T>();
		dest.setUserId(user);
		dest.sourceComponent = system.getMessagingSystemId();
		dest.sourceModule = system.getMessagingModuleId();
		dest.payloadType = payload.getClass().getName();
		dest.timestamp = new Date();
		dest.payload = payload;
		dest.id = UUID.randomUUID().toString();
		return dest;
	}

	/**
	 * Gets the source component of the message.
	 * 
	 * @return The source component as a String.
	 */
	public String getSourceComponent() {
		return sourceComponent;
	}

	/**
	 * Sets the source component of the message.
	 * 
	 * @param messageSource The source component to set.
	 */
	public void setSourceComponent(String messageSource) {
		this.sourceComponent = messageSource;
	}

	/**
	 * Gets the source type of the message.
	 * 
	 * @return The source SystemComponentType.
	 */
	public SystemComponentType getSourceType() {
		return sourceType;
	}

	/**
	 * Sets the source type of the message.
	 * 
	 * @param sourceType The SystemComponentType to set.
	 */
	public void setSourceType(SystemComponentType sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * Gets the target component of the message.
	 * 
	 * @return The target component as a String.
	 */
	public String getTargetComponent() {
		return targetComponent;
	}

	/**
	 * Sets the target component of the message.
	 * 
	 * @param messageTarget The target component to set.
	 */
	public void setTargetComponent(String messageTarget) {
		this.targetComponent = messageTarget;
	}

	/**
	 * Gets the target type of the message.
	 * 
	 * @return The target SystemComponentType.
	 */
	public SystemComponentType getTargetType() {
		return targetType;
	}

	/**
	 * Sets the target type of the message.
	 * 
	 * @param targetType The SystemComponentType to set.
	 */
	public void setTargetType(SystemComponentType targetType) {
		this.targetType = targetType;
	}

	/**
	 * Gets the payload of the message.
	 * 
	 * @return The payload.
	 */
	public PayloadType getPayload() {
		return payload;
	}

	/**
	 * Sets the payload of the message. Automatically updates the payloadType field.
	 * 
	 * @param payload The payload to set.
	 */
	public void setPayload(PayloadType payload) {
		this.payload = payload;
		if (payload == null) {
			this.payloadType = null;
		} else {
			this.payloadType = payload.getClass().getName();
		}
	}

	/**
	 * Gets the payload type of the message.
	 * 
	 * @return The payload type as a String.
	 */
	public String getPayloadType() {
		return payloadType;
	}

	/**
	 * Sets the payload type of the message.
	 * 
	 * @param payloadType The payload type to set.
	 */
	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	/**
	 * Returns a string representation of the GMessageEnvelope.
	 */
	@Override
	public String toString() {
		return "{id=" + id + ", payloadType=" + payloadType + ",messageTargetModule=" + targetModule
				+ " ,messageTarget=" + targetComponent + ",messageSourceModule=" + sourceModule + ", messageSource="
				+ sourceComponent + "}";
	}

	/**
	 * Gets the module within the source component.
	 * 
	 * @return The source module as a String.
	 */
	public String getSourceModule() {
		return sourceModule;
	}

	/**
	 * Sets the module within the source component.
	 * 
	 * @param sourceModule The source module to set.
	 */
	public void setSourceModule(String sourceModule) {
		this.sourceModule = sourceModule;
	}

	/**
	 * Gets the module within the target component.
	 * 
	 * @return The target module as a String.
	 */
	public String getTargetModule() {
		return targetModule;
	}

	/**
	 * Sets the module within the target component.
	 * 
	 * @param targetModule The target module to set.
	 */
	public void setTargetModule(String targetModule) {
		this.targetModule = targetModule;
	}

	/**
	 * Gets the unique identifier of the message.
	 * 
	 * @return The unique ID as a String.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the message.
	 * 
	 * @param id The unique ID to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the retry count for the message.
	 * 
	 * @return The retry count as an Integer.
	 */
	public Integer getRetry() {
		return retry;
	}

	/**
	 * Sets the retry count for the message.
	 * 
	 * @param retry The retry count to set.
	 */
	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	/**
	 * Gets the timestamp of when the message was created or sent.
	 * 
	 * @return The timestamp as a Date object.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp of when the message was created or sent.
	 * 
	 * @param timestamp The timestamp to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the ID of the user associated with the message.
	 * 
	 * @return The user ID as a String.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the ID of the user associated with the message.
	 * 
	 * @param userId The user ID to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the delivery status of the message.
	 * 
	 * @return True if delivered, otherwise false.
	 */
	public Boolean getDelivered() {
		return delivered;
	}

	/**
	 * Sets the delivery status of the message.
	 * 
	 * @param delivered The delivery status to set.
	 */
	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	/**
	 * Gets the processed status of the message.
	 * 
	 * @return True if processed, otherwise false.
	 */
	public Boolean getProcessed() {
		return processed;
	}

	/**
	 * Sets the processed status of the message.
	 * 
	 * @param processed The processed status to set.
	 */
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowStepId() {
		return workflowStepId;
	}

	public void setWorkflowStepId(String workflowStepId) {
		this.workflowStepId = workflowStepId;
	}

	public List<GMessagingComponentRef> getOnProcessForwardDestionations() {
		return onProcessForwardDestionations;
	}

	public void setOnProcessForwardDestionations(List<GMessagingComponentRef> onProcessForwardDestionations) {
		this.onProcessForwardDestionations = onProcessForwardDestionations;
	}

	public void addOnProcessForwardDestination(String moduleId, String componentId, String workflowId, String stepId) {
		if (this.onProcessForwardDestionations == null) {
			this.onProcessForwardDestionations = new ArrayList<GMessagingComponentRef>();
		}
		this.onProcessForwardDestionations.add(new GMessagingComponentRef(moduleId, componentId, workflowId, stepId));

	}

	public GWorkflowType getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(GWorkflowType workflowType) {
		this.workflowType = workflowType;
	}
}