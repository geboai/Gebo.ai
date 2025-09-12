package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;

@Node("entity_alias_chunk")
@Data
public class GraphEntityAliasInDocumentChunk extends AbstractInDocumentChunkObject {

	private GraphEntityAliasObject discoveredEntityAlias;
}
