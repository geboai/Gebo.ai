package ai.gebo.knlowledgebase.model.contents;

import java.util.List;

import lombok.Data;

@Data
public class GContentSelectionFilterCriteria {
	public static enum NameFilterMatchCriteria {
		CONTAINS, EQUALS, STARTS_WITH, ENDS_WITH
	}

	private List<String> mimeContentTypes = null;
	private List<String> extensions = null;
	private String nameFilter = null;
	private NameFilterMatchCriteria nameFilterCriteria = null;
	private Long maxFileSize = null;
	private Long maxTokenSize = null;
	private Integer maxModificationAgeInDays = null;

	public boolean isEmpty() {
		return !(nameFilter != null && nameFilter.trim().length() > 0 && nameFilterCriteria != null)
				&& (maxFileSize == null || maxFileSize == 0l) && (maxTokenSize == null || maxTokenSize == 0l)
				&& (maxModificationAgeInDays == null || maxModificationAgeInDays == 0l);
	}
}
