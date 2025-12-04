package ai.gebo.architecture.graphrag.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject;
import lombok.AllArgsConstructor;
import lombok.Data;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@Data 
@AllArgsConstructor
class GraphObjectReference<Neo4JType extends AbstractGraphObject, ExtractedType extends ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject> {
	private final ExtractedType extractedObject;
	private final Neo4JType graphObject;	
	private final Map<String, ExtractedType> chunkIds = new HashMap<>();
	private final Map<String, ExtractedType> chunkGroupIds = new HashMap<>();
}