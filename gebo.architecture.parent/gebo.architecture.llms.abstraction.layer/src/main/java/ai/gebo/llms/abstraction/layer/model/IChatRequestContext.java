package ai.gebo.llms.abstraction.layer.model;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

public interface IChatRequestContext {
	public String getConsolidatedHistory();

	public List<IQuestionAnswerEntry> getInteractions();

	public List<Document> getHistoricalDocuments();

	public String getActualUserRequest();

	public List<Document> getActualUserRequestDocuments();

	public Map<String, Object> getToolsContext();
}
