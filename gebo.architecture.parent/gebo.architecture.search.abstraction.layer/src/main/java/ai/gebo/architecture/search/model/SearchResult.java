package ai.gebo.architecture.search.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class SearchResult implements Cloneable, IGComponentOriginatedDocument {
	@NotNull
	private String id = null;
	@NotNull
	private SearchResultReference resultReference = null;
	@NotNull
	private VFilesystemReference navigationReference = null;
	@NotNull
	private String descriptiveText = null;
	private int nestingLevel = 0;
	private Date modificationDate = null;
	private List<SearchResult> childs = new ArrayList<SearchResult>();
	@NotNull
	GeboComponentInfo originComponent = null;
	@NotNull
	String systemConfigurationCode = null;

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
		this.originComponent = result.originComponent;
		this.systemConfigurationCode = result.systemConfigurationCode;
		this.childs.addAll(result.childs.stream().map(x -> new SearchResult(x)).toList());
	}

	@Override
	public @NotNull String getCode() {
		StringBuffer buffer = new StringBuffer();
		if (originComponent != null) {
			buffer.append(originComponent.getCompleteComponentId());
		}
		buffer.append("-");
		if (systemConfigurationCode != null) {
			buffer.append(systemConfigurationCode);
		}
		buffer.append("-");
		if (resultReference != null && resultReference.getUri() != null) {
			buffer.append(resultReference.getUri());
		} else if (navigationReference != null && navigationReference.path != null) {
			if (navigationReference.root != null) {
				if (navigationReference.root.getAbsolutePath() != null) {
					buffer.append(navigationReference.root.getAbsolutePath());
					buffer.append("-");
				} else if (navigationReference.root.getUri() != null) {
					buffer.append(navigationReference.root.getUri());
					buffer.append("-");
				}
			}
			buffer.append(navigationReference.path.absolutePath);

		} else {
			buffer.append(id);
		}
		return buffer.toString();
	}

}