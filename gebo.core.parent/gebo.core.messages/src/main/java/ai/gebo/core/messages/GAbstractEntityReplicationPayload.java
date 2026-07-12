package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class GAbstractEntityReplicationPayload<EntityType extends GBaseObject> extends GBaseMessagePayload {
	@NotNull
	private EntityType entity = null;
	private boolean deleted = false;	
}
