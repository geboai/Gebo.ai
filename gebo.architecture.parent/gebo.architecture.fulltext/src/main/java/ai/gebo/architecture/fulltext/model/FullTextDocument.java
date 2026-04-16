package ai.gebo.architecture.fulltext.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FullTextDocument {
	@NotNull
	private String code = null;
	private String title = null;
	private Long size = null;
	private Long tokensTotal = null;
	private Integer nChunks = null;

}
