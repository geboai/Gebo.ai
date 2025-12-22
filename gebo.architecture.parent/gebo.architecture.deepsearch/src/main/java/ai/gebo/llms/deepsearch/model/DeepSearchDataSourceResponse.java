package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.HashIndexed;

import com.drew.lang.annotations.NotNull;

import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class DeepSearchDataSourceResponse extends GBaseObject implements IDeepSearchResult {

	@NotNull
	@HashIndexed
	String deepsearchCode = null;
	@NotNull
	String response = null;
	Boolean searchResultsEmpty = null;
	String dataSourceDescription = null;
	List<SearchResultReference> dataSourceReferences = new ArrayList<SearchResultReference>();
	GUserMessage errorMessage = null;

	public DeepSearchDataSourceResponse() {
		this.setCode(UUID.randomUUID().toString());
	}

}
