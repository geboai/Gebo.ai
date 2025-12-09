package ai.gebo.llms.abstraction.layer.model;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

public interface IChatContext {
	public String getConsolidatedHistory();

	public List<IQuestionAnswerEntry> getInteractions();
	public List<Document> getDocuments();
	public  Map<String, Object> getToolsContext();
}
