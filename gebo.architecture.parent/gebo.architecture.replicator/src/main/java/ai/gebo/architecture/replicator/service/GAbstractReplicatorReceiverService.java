package ai.gebo.architecture.replicator.service;

import java.util.List;

import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.environment.GeboApplicationArchitecture.ArchitectureType;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.messages.GAbstractEntityReplicationPayload;
import ai.gebo.model.base.GBaseObject;

public abstract class GAbstractReplicatorReceiverService implements IGMessageReceiver {

    private final String messagingModuleId;
    private final String messagingSystemId;
    private final GeboApplicationArchitecture architecture;
    private final IGPersistentObjectManager persistentObjectManager;

    protected GAbstractReplicatorReceiverService(String messagingModuleId, String messagingSystemId,
                                                  GeboApplicationArchitecture architecture,
                                                  IGPersistentObjectManager persistentObjectManager) {
        this.messagingModuleId = messagingModuleId;
        this.messagingSystemId = messagingSystemId;
        this.architecture = architecture;
        this.persistentObjectManager = persistentObjectManager;
    }

    @Override
    public String getMessagingModuleId() {
        return messagingModuleId;
    }

    @Override
    public String getMessagingSystemId() {
        return messagingSystemId;
    }

    @Override
    public SystemComponentType getComponentType() {
        return SystemComponentType.APPLICATION_COMPONENT;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void accept(GMessageEnvelope envelope) {
        if (architecture.getArchitecture() != ArchitectureType.MICROSERVICES) {
            return;
        }

        Object payload = envelope.getPayload();
        if (!(payload instanceof GAbstractEntityReplicationPayload)) {
            return;
        }

        GAbstractEntityReplicationPayload<GBaseObject> replicationPayload =
                (GAbstractEntityReplicationPayload<GBaseObject>) payload;

        GBaseObject entity = replicationPayload.getEntity();
        if (entity == null) {
            return;
        }

        boolean deleted = replicationPayload.isDeleted();

        try {
            if (deleted) {
                GBaseObject existing = persistentObjectManager.findById(entity.getClass(), entity.getCode());
                if (existing != null) {
                    persistentObjectManager.delete(entity, false);
                }
            } else {
                // A replicated entity always arrives with its code already set (assigned by
                // the originating microservice, which the receiver must preserve so
                // cross-service lookups keep working) - update() is the correct call
                // regardless of whether this is the first time this code is seen here:
                // it requires a pre-set code and performs a Mongo save() (upsert). insert()
                // is for the opposite case - a fresh entity that still needs a generated
                // code - and rejects one that already has it.
                persistentObjectManager.update(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process replication message for entity: " + entity.getCode(), e);
        }
    }

    @Override
    public List<String> getAcceptedPayloadTypes() {
        return List.of();
    }

    @Override
    public boolean isAcceptEveryPayloadType() {
        return true;
    }

    @Override
    public boolean handlesMessagePayload(IGMessagePayloadType payloadType) {
        return payloadType instanceof GAbstractEntityReplicationPayload;
    }
}