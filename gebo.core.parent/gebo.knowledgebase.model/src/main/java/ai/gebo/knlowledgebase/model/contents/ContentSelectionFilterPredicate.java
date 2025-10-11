package ai.gebo.knlowledgebase.model.contents;

import java.util.List;
import java.util.function.Predicate;

import ai.gebo.knlowledgebase.model.contents.GContentSelectionFilterCriteria.NameFilterMatchCriteria;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContentSelectionFilterPredicate implements Predicate<GDocumentReference> {
	private final GContentSelectionFilter filter;

	@Override
	public boolean test(GDocumentReference document) {

		return filterMatches(filter, document);
	}

	public static final boolean filterMatches(GContentSelectionFilter filter, GDocumentReference document) {
		if (filter != null) {
			for (GContentSelectionFilterCriteria criteria : filter.getCriterias()) {
				if (filterCriteriaMatches(criteria, document))
					return true;
			}
		}
		return false;
	}

	private static boolean filterCriteriaMatches(GContentSelectionFilterCriteria criteria,
			GDocumentReference document) {
		boolean ok = true;
		if (criteria.getNameFilter() != null && criteria.getNameFilterCriteria() != null) {
			ok = ok && applyNameFilterCriteria(criteria.getNameFilter(), criteria.getNameFilterCriteria());
		}
		if (ok && criteria.getMaxFileSize() != null && criteria.getMaxFileSize() > 0l
				&& document.getFileSize() != null) {
			ok = ok && criteria.getMaxFileSize() >= document.getFileSize();
		}
		if (ok && criteria.getExtensions() != null && !criteria.getExtensions().isEmpty()) {
			ok = ok && applyExtensionIn(document.getExtension(), criteria.getExtensions());
		}
		if (ok && criteria.getMaxModificationAgeInDays() != null && document.getModificationDate() != null) {
			long time = document.getModificationDate().getTime();
			long threshold = System.currentTimeMillis()
					- ((1l + criteria.getMaxModificationAgeInDays().longValue()) * 24 * 60 * 1000);
			ok = ok && time >= threshold;
		}
		return false;
	}

	private static boolean applyExtensionIn(String extension, List<String> extensions) {

		return false;
	}

	private static boolean applyNameFilterCriteria(String nameFilter, NameFilterMatchCriteria nameFilterCriteria) {

		return false;
	}

	public static Predicate<GDocumentReference> of(GContentSelectionFilter filter) {
		return new ContentSelectionFilterPredicate(filter);
	}
}
