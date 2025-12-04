package ai.gebo.architecture.graphrag.extraction.model;

import lombok.Data;

@Data
public class RelationObject extends AbstractGraphObject {
	private EntityObject fromEntity = null;
	private EntityObject toEntity = null;
}
