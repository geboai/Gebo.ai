package ai.gebo.architecture.search.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class SearchResult implements Cloneable {
	@NotNull
	private String id = null;
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
	private List<SearchResult> childs = new ArrayList<SearchResult>();

	public Object clone() throws CloneNotSupportedException {

		return super.clone();

	}

	public SearchResult() {
		this.id = UUID.randomUUID().toString();
	}

	public SearchResult(SearchResult result) {
		this.id = result.id;
		this.descriptiveText = result.descriptiveText;
		this.navigationReference = result.navigationReference;
		this.resultReference = result.resultReference;
		this.nestingLevel = result.nestingLevel;
		this.systemHandlerId = result.systemHandlerId;
		this.systemConfigurationCode = result.systemConfigurationCode;
		this.childs.addAll(result.childs.stream().map(x -> new SearchResult(x)).toList());
	}
}