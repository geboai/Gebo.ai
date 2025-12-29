package ai.gebo.architecture.rag_threasholds_autotune.service.impl.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;

public class AutoTuneQuestionResult {
	public AutoTuneQuestion query;
	public List<Document> relatedDocuments = new ArrayList<Document>();
	public double threashold;
	public int topK;
}