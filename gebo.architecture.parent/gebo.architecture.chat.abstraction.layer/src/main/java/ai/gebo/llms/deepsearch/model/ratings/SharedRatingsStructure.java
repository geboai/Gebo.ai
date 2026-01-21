package ai.gebo.llms.deepsearch.model.ratings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ai.gebo.architecture.search.model.SearchResult;
import lombok.AllArgsConstructor;

public class SharedRatingsStructure {
	@AllArgsConstructor
	static class Entry {
		SearchResult result = null;
		Double rating = 0.0;
		boolean accessed = false;
	}

	int poppedEntries = 0;
	final TreeMap<Double, List<Entry>> orderedTree = new TreeMap<Double, List<Entry>>();

	public void addSearchResultsWithRatings(List<SearchResult> flattenedSearchResults, RatedDocumentsList rated) {
		Map<String, Entry> byId = new HashMap<String, Entry>();
		for (SearchResult searchResult : flattenedSearchResults) {
			byId.put(searchResult.getId(), new Entry(searchResult, 0.0, false));
		}
		if (rated.getRatedDocumentRefs() != null) {
			for (RatedDocumentRefOutput r : rated.getRatedDocumentRefs()) {
				Entry entry = byId.get(r.getItemId());
				if (entry != null) {
					entry.rating = r.getRelevanceScore() != null && r.getConfidence() != null
							? r.getRelevanceScore().doubleValue() * r.getConfidence().doubleValue()
							: 0.0;
				}
			}
		}
		Collection<Entry> data = byId.values();
		synchronized (orderedTree) {
			for (Entry entry : data) {
				if (!orderedTree.containsKey(entry.rating)) {
					orderedTree.put(entry.rating, new ArrayList<SharedRatingsStructure.Entry>());
				}
				orderedTree.get(entry.rating).add(entry);
			}
		}
	}

	public SearchResult popHigherRanked() {
		SearchResult outValue = null;
		synchronized (orderedTree) {
			List<List<Entry>> contents = new ArrayList<List<Entry>>(orderedTree.values());
			for (int i = contents.size() - 1; outValue == null && i >= 0; i--) {
				List<Entry> thisList = contents.get(i);
				for (Entry entry : thisList) {
					if (!entry.accessed) {
						outValue = entry.result;
						entry.accessed = true;
						poppedEntries++;
						break;
					}
				}
			}
		}
		return outValue;
	}

	public int getPoppedEntries() {
		return poppedEntries;
	}

}
