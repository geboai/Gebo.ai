package ai.gebo.architecture.search.model;

import java.util.List;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

@Data
public class WebSearchQueryObject implements INativeQueryObject {
	private List<String> searchedTexts=null;
	@Override
	public List<String> relevantKeywords() {
	
		return searchedTexts;
	}
	
}