package ai.gebo.architecture.replicator.service;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.model.base.GBaseObject;

@Configuration
public class ReplicatorServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IEntityReplicationService.class)
    public IEntityReplicationService dummyEntityReplicationService() {
        return new IEntityReplicationService() {
            @Override
            public <EntityType extends GBaseObject> void replicate(EntityType entity) {
            }

            @Override
            public <EntityType extends GBaseObject> void replicate(EntityType entity, boolean deleted) {
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(IGMessageReceiver.class)
    public IGMessageReceiver dummyMessageReceiver() {
        return new IGMessageReceiver() {
            @Override
            public String getMessagingModuleId() {
                return "dummy";
            }

            @Override
            public String getMessagingSystemId() {
                return "dummy";
            }

            @Override
            public SystemComponentType getComponentType() {
                return SystemComponentType.APPLICATION_COMPONENT;
            }

            @Override
            public List<String> getAcceptedPayloadTypes() {
                return List.of();
            }

            @Override
            public boolean isAcceptEveryPayloadType() {
                return false;
            }

            @Override
            public void accept(GMessageEnvelope envelope) {
            }
        };
    }
}