package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.CatalogueSample;
import lombok.Data;

@Data
public class DeepSearchDataSourceMetaInfos {

	/**
	 * Generic, provider-agnostic code used to present the (single) active web-search
	 * provider to the routing LLM, so it never sees which vendor (Google, Brave,
	 * SerpApi, Tavily, SearXNG, ...) is configured. It is translated back to the real
	 * {@link #handlerId} before the deep-search executor dispatches the query.
	 */
	public static final String WEB_SEARCH_ROUTING_CODE = "web-search";

	private String handlerId = null;
	private String description = null;
	/** True when this data source is a web-search provider (see WEB_SEARCH_ROUTING_CODE). */
	private boolean webSearch = false;
	private List<CatalogueSample> catalogues = new ArrayList<CatalogueSample>();

	/**
	 * Code shown to the routing/pure-search LLM: a generic {@link #WEB_SEARCH_ROUTING_CODE}
	 * for web search (so routing stays provider-agnostic), the real handlerId otherwise.
	 */
	public String getRoutingCode() {
		return webSearch ? WEB_SEARCH_ROUTING_CODE : handlerId;
	}

}
