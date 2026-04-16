package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.HashIndexed;

import com.drew.lang.annotations.NotNull;

import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class DeepSearchDataSourceResponse extends GBaseObject implements IDeepSearchResult {

	@NotNull
	@HashIndexed
	private String deepsearchCode = null;
	private String processingModel = null;
	@NotNull
	private String response = null;
	private Boolean searchResultsEmpty = null;
	private List<SearchResultReference> dataSourceReferences = new ArrayList<SearchResultReference>();
	private GUserMessage errorMessage = null;
	@NotNull
	private String handlerId = null;
	private Integer dataSourceIndex = null;
	@NotNull
	private String dataSourceDescription = null;
	private double processPercentage=0.0;
	public DeepSearchDataSourceResponse() {
		this.setCode(UUID.randomUUID().toString());
	}

}
