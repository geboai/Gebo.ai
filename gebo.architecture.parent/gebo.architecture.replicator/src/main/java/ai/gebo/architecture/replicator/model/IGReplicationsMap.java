package ai.gebo.architecture.replicator.model;

import java.util.List;
import java.util.Map;

public interface IGReplicationsMap {
    Map<String, List<ReplicationMessageReceiver>> getRoutingMap();
}