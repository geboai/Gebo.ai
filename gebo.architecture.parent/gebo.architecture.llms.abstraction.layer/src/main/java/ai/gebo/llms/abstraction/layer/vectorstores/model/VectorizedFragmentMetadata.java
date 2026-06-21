package ai.gebo.llms.abstraction.layer.vectorstores.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VectorizedFragmentMetadata {
	private final String id;
	private final Map<String, Object> metadata;

}
