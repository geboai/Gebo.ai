package ai.gebo.llms.deepsearch.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchAnalyzedDocument {
	@NotNull
	String dataSourceCode = null;
	@NotNull
	String dataSourceDescription = null;
	private String code = null;
	private String name = null;
	private String url = null;
	@NotNull
	private DeepSearchSourceType sourceType = null;
}