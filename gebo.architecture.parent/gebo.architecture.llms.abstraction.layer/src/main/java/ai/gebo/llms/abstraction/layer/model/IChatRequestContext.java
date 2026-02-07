package ai.gebo.llms.abstraction.layer.model;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

public interface IChatRequestContext {
	public static final String USER_QUESTION_PROMPT_PLACEHOLDER = "question";
	public static final String CONSOLIDATED_HISTORY_PROMPT_PLACEHOLDER = "consolidated";
	public static final String DOCUMENTS_PROMPT_PLACEHOLDER = "documents";
		
	public String getConsolidatedHistory();

	public List<IChatSessionEntry> getInteractions();

	public List<Document> getDocuments();

	public String getActualUserRequest();

	public Map<String, Object> getToolsContext();
	
}
