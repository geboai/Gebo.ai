package ai.gebo.architecture.graphrag.services.impl;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject;
import lombok.Data;

@Data class GraphObjectReference<Neo4JType extends AbstractGraphObject, ExtractedType extends ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject> {
	private String logicKey = null;
	private Neo4JType graphObject = null;
	private ExtractedType extractedObject = null;
	private Map<String, ExtractedType> chunkIds = new HashMap<>();
}