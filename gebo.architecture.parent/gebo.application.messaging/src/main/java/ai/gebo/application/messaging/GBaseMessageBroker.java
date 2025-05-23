/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;

/**
 * AI generated comments
 * <p>
 * The {@code GBaseMessageBroker} class acts as the base implementation of a message broker.
 * It manages the routing and acceptance of messages between various messaging systems.
 * Implements the {@code IGMessageBroker} interface.
 */
public class GBaseMessageBroker implements IGMessageBroker {

    // Maps to store message receivers and emitters organized by module and system IDs.
    protected Map<String, Map<String, IGMessageReceiver>> receivers = new HashMap<String, Map<String, IGMessageReceiver>>();
    protected Map<String, Map<String, IGMessageEmitter>> emitters = new HashMap<String, Map<String, IGMessageEmitter>>();

    // Logger for this class
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Default constructor for GBaseMessageBroker.
     */
    public GBaseMessageBroker() {

    }

    /**
     * Returns a list of payload types that the message broker can accept.
     * In this implementation, it returns an empty list.
     *
     * @return List of accepted payload types.
     */
    @Override
    public List<String> getAcceptedPayloadTypes() {
        return List.of();
    }

    /**
     * Indicates whether the broker can accept every payload type.
     *
     * @return {@code true} if every payload type is accepted, otherwise {@code false}.
     */
    @Override
    public boolean isAcceptEveryPayloadType() {
        return true;
    }

    /**
     * Returns the identifier for the messaging system.
     *
     * @return The messaging system ID.
     */
    @Override
    public String getMessagingSystemId() {
        return getClass().getName();
    }

    /**
     * Returns the type of system component which is a broker in this case.
     *
     * @return Component type as {@code SystemComponentType.BROKER}.
     */
    @Override
    public SystemComponentType getComponentType() {
        return SystemComponentType.BROKER;
    }

    /**
     * Adds a system component like a receiver or emitter to the broker.
     * Ensures the component is not already registered.
     *
     * @param system The system component to add.
     */
    @Override
    public synchronized void addSystemComponent(IGMessagingSystem system) {
        if (system instanceof IGMessageReceiver) {
            IGMessageReceiver receiver = (IGMessageReceiver) system;
            if (!receivers.containsKey(system.getMessagingModuleId()))
                receivers.put(receiver.getMessagingModuleId(), new HashMap<String, IGMessageReceiver>());
            if (receivers.get(receiver.getMessagingModuleId()).containsKey(receiver.getMessagingSystemId())
                && receivers.get(receiver.getMessagingModuleId()).get(receiver.getMessagingSystemId()) != receiver)
                throw new IllegalStateException("The system " + receiver.getMessagingModuleId() + "."
                    + system.getMessagingSystemId() + " is already registered in this broker");
            receivers.get(system.getMessagingModuleId()).put(system.getMessagingSystemId(), receiver);
        }
        if (system instanceof IGMessageEmitter) {
            IGMessageEmitter emitter = (IGMessageEmitter) system;
            if (!emitters.containsKey(system.getMessagingModuleId()))
                emitters.put(emitter.getMessagingModuleId(), new HashMap<String, IGMessageEmitter>());
            if (emitters.get(emitter.getMessagingModuleId()).containsKey(emitter.getMessagingSystemId())
                && emitters.get(emitter.getMessagingModuleId()).get(emitter.getMessagingSystemId()) != emitter)
                throw new IllegalStateException("The system " + emitter.getMessagingModuleId() + "."
                    + system.getMessagingSystemId() + " is already registered in this broker");
            emitters.get(system.getMessagingModuleId()).put(system.getMessagingSystemId(), emitter);
        }
    }

    /**
     * Removes a system component from the broker.
     * Checks if the component is present before attempting removal.
     *
     * @param system The system component to remove.
     */
    @Override
    public synchronized void removeSystemComponent(IGMessagingSystem system) {
        if (system instanceof IGMessageReceiver) {
            IGMessageReceiver receiver = (IGMessageReceiver) system;
            if (!receivers.containsKey(system.getMessagingModuleId()))
                throw new IllegalStateException(
                    "The module " + system.getMessagingModuleId() + " does not exist in this broker");
            if (!receivers.get(system.getMessagingModuleId()).containsKey(system.getMessagingSystemId()))
                throw new IllegalStateException(
                    "The system " + system.getMessagingSystemId() + " does not exist in this broker");
            receivers.get(system.getMessagingModuleId()).remove(system.getMessagingSystemId());
        }
        if (system instanceof IGMessageReceiver) {
            IGMessageEmitter emitter = (IGMessageEmitter) system;
            if (!emitters.containsKey(system.getMessagingModuleId()))
                throw new IllegalStateException(
                    "The module " + system.getMessagingModuleId() + " does not exist in this broker");
            if (!emitters.get(system.getMessagingModuleId()).containsKey(system.getMessagingSystemId()))
                throw new IllegalStateException(
                    "The system " + system.getMessagingSystemId() + " does not exist in this broker");
            emitters.get(system.getMessagingModuleId()).remove(system.getMessagingSystemId());
        }
    }

    /**
     * Accepts a message envelope and routes it to the appropriate receiver if conditions are met.
     * Validates the receiver and emitter capabilities based on the envelope's payload type.
     *
     * @param envelope The message envelope to be processed.
     */
    @Override
    public void accept(GMessageEnvelope envelope) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Begin broker accept(" + envelope.toString() + ")");
            }
            if (envelope.getSourceModule() != null && envelope.getSourceComponent() != null
                && envelope.getTargetModule() != null && envelope.getTargetComponent() != null
                && emitters.containsKey(envelope.getSourceModule()) && envelope.getTargetComponent() != null
                && receivers.containsKey(envelope.getTargetModule())
                && emitters.get(envelope.getSourceModule()).containsKey(envelope.getSourceComponent())
                && receivers.get(envelope.getTargetModule()).containsKey(envelope.getTargetComponent())) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Routed to receiver " + envelope.getTargetComponent());
                }
                IGMessageReceiver receiver = receivers.get(envelope.getTargetModule())
                    .get(envelope.getTargetComponent());
                IGMessageEmitter emitter = emitters.get(envelope.getSourceModule()).get(envelope.getSourceComponent());
                if (emitter.getEmittedPayloadTypes() == null
                    || !emitter.getEmittedPayloadTypes().contains(envelope.getPayloadType())) {
                    throw new IllegalStateException("The emitter " + emitter.getMessagingSystemId()
                        + " cannot emit message of type " + envelope.getPayloadType()
                        + " that's not in its list of EmittedPayloadTypes envelope:" + envelope.toString());
                }
                if (!receiver.isAcceptEveryPayloadType() && (receiver.getAcceptedPayloadTypes() == null
                    || !receiver.getAcceptedPayloadTypes().contains(envelope.getPayloadType()))) {
                    throw new IllegalStateException("The receiver " + receiver.getMessagingSystemId()
                        + " cannot receive message of type " + envelope.getPayloadType()
                        + " that's not in its list of AcceptedPayloadTypes envelope:" + envelope.toString());
                }
                if (receiver.handlesMessagePayload(envelope.getPayload())) {
                    receiver.accept(envelope);
                } else {
                    LOGGER.error("Receiver: " + receiver.getCompleteId() + " does not manage message: "
                        + envelope.toString());
                }

            } else {
                LOGGER.error("Envelope condition for:" + envelope.toString() + " impossible to be routed");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("End broker accept(" + envelope.toString() + ")");
            }
        } catch (Throwable th) {
            LOGGER.error("Error during routing", th);
        }
    }

    /**
     * Returns the messaging module identifier.
     *
     * @return The messaging module ID.
     */
    @Override
    public String getMessagingModuleId() {
        return GStandardModulesConstraints.CORE_MODULE;
    }

    /**
     * Checks if a given system component is registered with the broker.
     *
     * @param system The system component to check.
     * @return {@code true} if the system is registered, otherwise {@code false}.
     */
    @Override
    public boolean isRegistered(IGMessagingSystem system) {
        boolean isReceiver = false;
        boolean isEmitter = false;
        boolean receiverOK = false;
        boolean emitterOK = false;
        if (system instanceof IGMessageReceiver) {
            isReceiver = true;
            receiverOK = receivers.containsKey(system.getMessagingModuleId())
                && receivers.get(system.getMessagingModuleId()).containsKey(system.getMessagingSystemId());
        }
        if (system instanceof IGMessageEmitter) {
            isEmitter = true;
            emitterOK = emitters.containsKey(system.getMessagingModuleId())
                && emitters.get(system.getMessagingModuleId()).containsKey(system.getMessagingSystemId());
        }
        return ((isReceiver && receiverOK) || !isReceiver) && ((isEmitter && emitterOK) || !isEmitter);
    }

    /**
     * Retrieves information about all the systems the broker is aware of.
     *
     * @return List of module meta-information.
     */
    @Override
    public List<GModuleMetaInfo> getSystemsInfo() {
        return ComponentsTreeUtil.componentsTree(receivers, emitters);
    }

    /**
     * Checks if a receiving component with a specific module and component ID is present.
     *
     * @param module      The module ID.
     * @param componentId The component ID.
     * @return {@code true} if the component is present, otherwise {@code false}.
     */
    @Override
    public boolean checkReceivingComponentPresent(String module, String componentId) {
        return receivers != null && module != null && componentId != null && receivers.containsKey(module)
            && receivers.get(module).containsKey(componentId);
    }
}