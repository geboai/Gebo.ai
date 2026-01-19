package ai.gebo.architecture.fulltext.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FullTextChunk {
	@NotNull
	String id = null;
	@NotNull
	String content = null;
	String lang = null;
	Map<String, Object> metaData = new HashMap<String, Object>();
	@NotNull
	long tokensLength = 0l;
	int position = 0;
	@NotNull
	FullTextDocument document = null;
}
