package ai.gebo.knlowledgebase.model.contents;

import java.util.List;

import lombok.Data;

@Data
public class GContentSelectionFilterCriteria {
	private List<String> mimeContentTypes = null;
	private List<String> extensions = null;
	private String nameFilter = null;
	private Long maxFileSize = null;
	private Long maxTokenSize = null;
	private Integer maxModificationAgeInDays = null;

	public boolean isEmpty() {
		return (nameFilter == null || nameFilter.trim().length() == 0) && (maxFileSize == null || maxFileSize == 0l)
				&& (maxTokenSize == null || maxTokenSize == 0l) && (maxModificationAgeInDays == null || maxModificationAgeInDays == 0l);
	}
}
