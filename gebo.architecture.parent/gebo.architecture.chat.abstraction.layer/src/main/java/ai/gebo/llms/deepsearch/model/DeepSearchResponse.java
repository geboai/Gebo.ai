package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.HashIndexed;

import com.drew.lang.annotations.NotNull;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class DeepSearchResponse extends GBaseObject implements IDeepSearchResult{
	private static final String DEEP_SEARCH_AGGREGATOR = "Deep search aggregator";
	@NotNull
	@HashIndexed
	String deepsearchCode = null;
	@NotNull
	String response = null;
	Boolean searchResultsEmpty = null;
	private double processPercentage=0.0;
	public DeepSearchResponse() {
		this.setCode(UUID.randomUUID().toString());
	}

	@Override
	public String getDataSourceDescription() {
		
		return DEEP_SEARCH_AGGREGATOR;
	}
}
