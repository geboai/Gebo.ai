package ai.gebo.llms.deepsearch.datasources.model;

import ai.gebo.llms.deepsearch.model.DeepSearchDataSourceReference;
import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchResult {
	@NotNull
	private DeepSearchDataSourceReference resultReference = null;
	@NotNull
	private VFilesystemReference navigationReference = null;
	@NotNull
	private String descriptiveText = null;
	private int nestingLevel = 0;
}