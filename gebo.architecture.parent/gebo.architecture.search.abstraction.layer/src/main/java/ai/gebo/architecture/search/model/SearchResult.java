package ai.gebo.architecture.search.model;

import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class SearchResult implements Cloneable {
	@NotNull
	private SearchResultReference resultReference = null;
	@NotNull
	private VFilesystemReference navigationReference = null;
	@NotNull
	private String descriptiveText = null;
	private int nestingLevel = 0;
	@NotNull
	private String systemHandlerId = null;
	@NotNull
	private String systemConfigurationCode = null;

	public Object clone() throws CloneNotSupportedException {

		return super.clone();

	}
}