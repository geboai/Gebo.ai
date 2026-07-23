package ai.gebo.architecture.replicator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplicationMessageReceiver {
    private String messagingModuleId;
    private String messagingSystemId;
}