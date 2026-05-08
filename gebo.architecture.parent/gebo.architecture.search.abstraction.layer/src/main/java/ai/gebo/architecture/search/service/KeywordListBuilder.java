package ai.gebo.architecture.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordListBuilder {
	Map<String, Boolean> kwds = new HashMap<>();

	@FunctionalInterface
	public static interface KeywordsProvider {

		public List<String> get() throws Throwable;
	}

	public void addKeywordsProvider(KeywordsProvider provider) {
		try {
			List<String> k = provider.get();
			if (k != null && !k.isEmpty()) {
				for (String string : k) {
					kwds.put(string.toLowerCase(), true);
				}
			}
		} catch (Throwable th) {
		}
	}

	public boolean isEmpty() {
		return kwds == null || kwds.isEmpty();
	}

	public List<String> getKeywords() {
		return new ArrayList<String>(kwds.keySet());
	}

}
