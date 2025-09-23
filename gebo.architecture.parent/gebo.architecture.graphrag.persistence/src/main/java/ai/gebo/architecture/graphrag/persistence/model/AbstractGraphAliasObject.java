package ai.gebo.architecture.graphrag.persistence.model;

import java.util.UUID;

import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Data
public class AbstractGraphAliasObject<Type extends AbstractGraphObject> extends AbstractGraphObject {
	public static enum EquivalenceType {
		ALIAS, SYNONYM
	}

	@Relationship(type = "referred_on", direction = Relationship.Direction.OUTGOING)
	private Type referenceObject = null;
	@Relationship(type = "alias_of", direction = Relationship.Direction.OUTGOING)
	private Type aliasObject = null;
	private EquivalenceType equivalenceType = null;

	@Override
	public String computeId() {

		return UUID.randomUUID().toString();
	}

}
