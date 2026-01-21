package ai.gebo.llms.deepsearch.datasources.model;

import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchDataSourceDocumentResult extends GBaseObject {
	private Boolean emptyResult = null;
	private String analyzedResult = null;
	@NotNull
	@HashIndexed
	String deepsearchCode = null;
	@NotNull
	@Order
	private String handlerId = null;
	@Order
	private Integer documentIndex = null;
	@NotNull
	private String dataSourceDescription = null;
	@NotNull
	private SearchResult analyzedSearchResult=null;
	private double processPercentage=0.0;
	public DeepSearchDataSourceDocumentResult() {
		setCode(UUID.randomUUID().toString());
	}
}