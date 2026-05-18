package ai.gebo.architecture.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MetaDocumentCategory {
	private final String categoryName;
	private final String categoryValue;
}