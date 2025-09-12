package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;

@Node("event_alias_chunk")
@Data
public class GraphEventAliasInDocumentChunk extends AbstractInDocumentChunkObject {

	private GraphEventAliasObject discoveredEventAlias;
}
