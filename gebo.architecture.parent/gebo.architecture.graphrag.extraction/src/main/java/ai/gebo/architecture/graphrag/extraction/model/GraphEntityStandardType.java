package ai.gebo.architecture.graphrag.extraction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GraphEntityStandardType {
	PRODUCT("PRODUCT", "A good or a product of some kind, phisical or digital, material or immaterial"),
	PERSON("PERSON", "Person"), COMPANY("COMPANY", "Company"), PROJECT("PROJECT", "Project");

	private final String typeCode;
	private final String description;

}