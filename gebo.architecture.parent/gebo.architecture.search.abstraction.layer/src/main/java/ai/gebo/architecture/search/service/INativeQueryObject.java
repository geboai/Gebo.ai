package ai.gebo.architecture.search.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface INativeQueryObject {
	@JsonIgnore
	public List<String> relevantKeywords();
	
}
