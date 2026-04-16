package ai.gebo.architecture.rag_threasholds_autotune.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class ThreasholdAutotuneProcessResult extends GBaseObject {
	public static class ListOfJsonObjects extends ArrayList<LinkedHashMap<String, Object>> {

	}

	private OptimizedThreashold threasholds = new OptimizedThreashold();
	private String rootKnowledgeBase = null;
	private String vectorStoreId = null;
	private String embeddingModelCode = null;
	private Long vectorStoreVectorizedCount = null;
	private double evaluationPoints = 0;
	private double score = 0;
	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	private Date processedDateTime = null;
	private List<LinkedHashMap<String, Object>> computedElements = new ListOfJsonObjects();

}
