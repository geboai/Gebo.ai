package ai.gebo.architecture.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Class that rappresents a generical search query and relevant keywords to search on a generic 3rd party system")
public class SearchQuery implements Cloneable {
	@JsonPropertyDescription("searched phrase in case of search engine allow phrases search")
	private String queryText;
	@JsonPropertyDescription("relevant keywords to search for if keyword search is implemented in the search engine")
	private List<String> relevantKeywords = null;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}