package ai.gebo.architecture.replicator.service;

import java.util.List;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.environment.GeboApplicationArchitecture.ArchitectureType;
import ai.gebo.architecture.replicator.model.IGReplicationsMap;
import ai.gebo.architecture.replicator.model.ReplicationMessageReceiver;
import ai.gebo.core.messages.GAbstractEntityReplicationPayload;
import ai.gebo.model.base.GBaseObject;

public abstract class GAbstractReplicatorService implements IEntityReplicationService, IGMessageEmitter {

    private final String messagingModuleId;
    private final String messagingSystemId;
    private final GeboApplicationArchitecture architecture;
    private final IGReplicationsMap replicationsMap;

    protected GAbstractReplicatorService(String messagingModuleId, String messagingSystemId,
                                          GeboApplicationArchitecture architecture,
                                          IGReplicationsMap replicationsMap) {
        this.messagingModuleId = messagingModuleId;
        this.messagingSystemId = messagingSystemId;
        this.architecture = architecture;
        this.replicationsMap = replicationsMap;
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
    public <EntityType extends GBaseObject> void replicate(EntityType entity) {
        replicate(entity, false);
    }

    @Override
    public <EntityType extends GBaseObject> void replicate(EntityType entity, boolean deleted) {
        if (architecture.getArchitecture() != ArchitectureType.MICROSERVICES) {
            return;
        }

        String className = entity.getClass().getName();
        List<ReplicationMessageReceiver> receivers = replicationsMap.getRoutingMap().get(className);

        if (receivers == null || receivers.isEmpty()) {
            return;
        }

        GAbstractEntityReplicationPayload<EntityType> payload = createPayload(entity);
        payload.setDeleted(deleted);

        for (ReplicationMessageReceiver receiver : receivers) {
            GMessageEnvelope<?> envelope = GMessageEnvelope.newMessageFrom(this, payload);
            envelope.setTargetModule(receiver.getMessagingModuleId());
            envelope.setTargetComponent(receiver.getMessagingSystemId());
            emit(envelope);
        }
    }

    protected abstract <EntityType extends GBaseObject> GAbstractEntityReplicationPayload<EntityType> createPayload(EntityType entity);

    protected abstract void emit(GMessageEnvelope<?> envelope);

    @Override
    public List<String> getEmittedPayloadTypes() {
        return List.of();
    }
}