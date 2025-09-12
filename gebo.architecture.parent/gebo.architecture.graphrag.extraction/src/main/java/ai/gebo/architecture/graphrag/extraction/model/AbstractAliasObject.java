package ai.gebo.architecture.graphrag.extraction.model;

import lombok.Data;

@Data
public class AbstractAliasObject<Type extends AbstractGraphObject> extends AbstractGraphObject {
	public static enum EquivalenceType {
		ALIAS, SYNONYM
	}

	private Type referenceObject = null;
	private Type aliasObject = null;
	private EquivalenceType equivalenceType = null;
	private Double confidence;

}
