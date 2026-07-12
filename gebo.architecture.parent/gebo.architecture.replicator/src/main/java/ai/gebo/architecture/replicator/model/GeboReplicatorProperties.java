package ai.gebo.architecture.replicator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "gebo.replicator")
public class GeboReplicatorProperties {
    private Map<String, List<RoutingEntry>> routing = new HashMap<>();

    @Data
    public static class RoutingEntry {
        private String messagingModuleId;
        private String messagingSystemId;
    }

    public Map<String, List<ReplicationMessageReceiver>> toRoutingMap() {
        Map<String, List<ReplicationMessageReceiver>> map = new HashMap<>();
        for (Map.Entry<String, List<RoutingEntry>> entry : routing.entrySet()) {
            List<ReplicationMessageReceiver> receivers = new ArrayList<>();
            for (RoutingEntry re : entry.getValue()) {
                receivers.add(new ReplicationMessageReceiver(re.getMessagingModuleId(), re.getMessagingSystemId()));
            }
            map.put(entry.getKey(), receivers);
        }
        return map;
    }
}