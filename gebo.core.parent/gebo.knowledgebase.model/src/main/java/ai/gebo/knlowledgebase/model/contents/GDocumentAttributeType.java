package ai.gebo.knlowledgebase.model.contents;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

@Data
public class GDocumentAttributeType extends GBaseObject {
	public static enum Cardinality {
		ONE, MANY
	}

	public static enum ValueType {
		LIST_REFERENCE, STRING, INTEGER
	}

	public static class AttributeScope {
		String projectCode = null;
		String knowledgeBaseCode = null;
		GObjectRef<GProjectEndpoint> endpointReference = null;
	}

	private Cardinality cardinality = null;
	private ValueType valueType = null;
	private AttributeScope scope = null;
	private Boolean referencesTranslatedList = null;
	private Boolean uiVisible=null;
	private TranslatedAttribute multilanguageDescription=null;

}
