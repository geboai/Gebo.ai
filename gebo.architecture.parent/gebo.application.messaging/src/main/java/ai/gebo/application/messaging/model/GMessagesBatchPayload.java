/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ai.gebo.application.messaging.IGMessagePayloadType;

/**
 * AI generated comments
 * Represents a batch of {@link GMessageEnvelope} messages, with functionality to
 * handle a collection of messages and metadata about them.
 * 
 * @param <T> the type of the message payload which must extend IGMessagePayloadType
 */
public class GMessagesBatchPayload<T extends IGMessagePayloadType> extends ArrayList<GMessageEnvelope<T>>
		implements IGMessagePayloadType {
	// Unique identifier for the payload
	private String payloadId = UUID.randomUUID().toString();
	// Timestamp marking the creation time of the payload
	private Date timestamp = new Date();
	// String representation of the type of elements in the payload
	private String fixedElementsPayloadType = null;
	// List of message IDs contained in this batch
	private List<String> cumulatedMessagesIds = new ArrayList<String>();

	/**
	 * Default constructor initializing an empty payload batch.
	 */
	public GMessagesBatchPayload() {

	}

	/**
	 * Constructor that initializes the batch payload with a specific message type.
	 *
	 * @param declaredType the class type of the payload elements
	 */
	public GMessagesBatchPayload(Class<T> declaredType) {
		fixedElementsPayloadType = declaredType.getName();
	}

	/**
	 * Constructor that initializes the batch payload with a specific initial capacity and message type.
	 *
	 * @param initialCapacity the initial capacity of the batch
	 * @param declaredType    the class type of the payload elements
	 */
	public GMessagesBatchPayload(int initialCapacity, Class<T> declaredType) {
		super(initialCapacity);
		fixedElementsPayloadType = declaredType.getName();
	}

	/**
	 * Constructor that initializes the batch payload with a collection of envelopes and a specific message type.
	 *
	 * @param c             the collection of message envelopes to include in the batch
	 * @param declaredType  the class type of the payload elements
	 */
	public GMessagesBatchPayload(Collection<GMessageEnvelope<T>> c, Class<T> declaredType) {
		super(c);
		fixedElementsPayloadType = declaredType.getName();
	}

	/**
	 * Returns the unique identifier of the payload.
	 *
	 * @return the payload's unique identifier
	 */
	public String getPayloadId() {
		return payloadId;
	}

	/**
	 * Sets the unique identifier of the payload.
	 *
	 * @param payloadId the new unique identifier to set
	 */
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}

	/**
	 * Returns the timestamp marking the creation time of the payload.
	 *
	 * @return the creation timestamp of the payload
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp marking the creation time of the payload.
	 *
	 * @param timestamp the new timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the string representation of the type of elements in the payload.
	 *
	 * @return the type of the elements in the payload
	 */
	public String getFixedElementsPayloadType() {
		return fixedElementsPayloadType;
	}

	/**
	 * Sets the string representation of the type of elements in the payload.
	 *
	 * @param fixedElementsPayloadType the new type to set for the elements in the payload
	 */
	public void setFixedElementsPayloadType(String fixedElementsPayloadType) {
		this.fixedElementsPayloadType = fixedElementsPayloadType;
	}

	/**
	 * Returns the list of message IDs contained in this batch.
	 *
	 * @return the list of cumulated message IDs
	 */
	public List<String> getCumulatedMessagesIds() {
		return cumulatedMessagesIds;
	}

	/**
	 * Sets the list of message IDs contained in this batch.
	 *
	 * @param cumulatedMessagesIds the new list of message IDs to set
	 */
	public void setCumulatedMessagesIds(List<String> cumulatedMessagesIds) {
		this.cumulatedMessagesIds = cumulatedMessagesIds;
	}

	/**
	 * Converts a list of {@link GMessageEnvelope} objects into a list of
	 * {@link GMessageEnvelope} batches grouped by payload type.
	 * 
	 * @param envelopes the list of message envelopes to be grouped
	 * @return a list of message envelope batches grouped by payload type
	 */
	public static List<GMessageEnvelope<GMessagesBatchPayload>> of(List<GMessageEnvelope> envelopes) {
		Map<Class, GMessageEnvelope<GMessagesBatchPayload>> byType = new HashMap<Class, GMessageEnvelope<GMessagesBatchPayload>>();
		envelopes.stream().forEach(x -> {
			if (x.getPayload() != null) {
				// If the payload type is not already in the map, create a new cumulative message envelope
				if (!byType.containsKey(x.getPayload().getClass())) {
					GMessageEnvelope<GMessagesBatchPayload> cumule = new GMessageEnvelope<GMessagesBatchPayload>();
					GMessagesBatchPayload gcumulated = new GMessagesBatchPayload();
					cumule.setPayload(gcumulated);
					gcumulated.setFixedElementsPayloadType(x.getPayloadType());
					byType.put(x.getPayload().getClass(), cumule);
					cumule.setSourceComponent(x.getSourceComponent());
					cumule.setSourceModule(x.getSourceModule());
					cumule.setTargetModule(x.getTargetModule());
					cumule.setTargetComponent(x.getTargetComponent());
					cumule.setSourceType(x.getSourceType());
					cumule.setTargetType(x.getTargetType());
				}
				// Add the current message to the cumulative envelope by type
				byType.get(x.getPayload().getClass()).getPayload().add(x);
				byType.get(x.getPayload().getClass()).getPayload().getCumulatedMessagesIds().add(x.getId());
			}
		});
		// Convert map values to a list to return
		List<GMessageEnvelope<GMessagesBatchPayload>> out = new ArrayList<GMessageEnvelope<GMessagesBatchPayload>>(
				byType.values());
		return out;
	}

}