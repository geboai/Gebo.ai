package ai.gebo.architecture.replicator.service;

import ai.gebo.model.base.GBaseObject;

public interface IEntityReplicationService {
    <EntityType extends GBaseObject> void replicate(EntityType entity);
}