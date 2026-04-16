package ai.gebo.bingsearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BingWebPageResult {
	String id, name, url, displayUrl, snippet, dateLastCrawled;
	List<BingDeepLinks> deepLinks = new ArrayList<BingDeepLinks>();
}