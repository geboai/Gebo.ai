package ai.gebo.bingsearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BingWebPages {
	String webSearchUrl = null;
	Long totalEstimatedMatches;
	List<BingWebPageResult> value = new ArrayList<BingWebPageResult>();
}