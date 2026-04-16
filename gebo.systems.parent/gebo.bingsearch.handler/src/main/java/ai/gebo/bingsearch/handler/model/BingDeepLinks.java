package ai.gebo.bingsearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BingDeepLinks {
	String name = null, url = null, snippet = null;
	List<BingDeepLinks> deepLinks = new ArrayList<BingDeepLinks>();
}