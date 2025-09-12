package ai.gebo.architecture.graphrag.persistence.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GraphExtractionMatching {
	private final List<GraphEntityObject> entities;
	private final List<GraphEventObject> events;
	private final List<GraphRelationObject> relations;
	private final List<GraphEntityAliasObject> entityAliases;
	private final List<GraphEventAliasObject> eventAliases;
}