package ai.gebo.bingsearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BingSearchQuery {
	String query = null;
	List<ResponseFilters> filters = new ArrayList<ResponseFilters>();
	Integer topN = null;
}