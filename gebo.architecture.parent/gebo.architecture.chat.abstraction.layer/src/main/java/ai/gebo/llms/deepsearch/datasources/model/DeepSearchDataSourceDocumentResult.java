package ai.gebo.llms.deepsearch.datasources.model;

import java.util.UUID;

import org.springframework.core.annotation.Order;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.deepsearch.model.BaseDeepSearchDocumentAnalisysResult;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchDataSourceDocumentResult extends BaseDeepSearchDocumentAnalisysResult {

	@NotNull
	@Order
	private String handlerId = null;
	@Order
	private Integer documentIndex = null;
	@NotNull
	private String dataSourceDescription = null;
	@NotNull
	private SearchResult analyzedSearchResult = null;

	public DeepSearchDataSourceDocumentResult() {
		setCode(UUID.randomUUID().toString());
	}
}