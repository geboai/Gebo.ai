package ai.gebo.architecture.search.model;

import lombok.Data;

@Data
public class SearchQuery implements Cloneable {
	String queryText;
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}