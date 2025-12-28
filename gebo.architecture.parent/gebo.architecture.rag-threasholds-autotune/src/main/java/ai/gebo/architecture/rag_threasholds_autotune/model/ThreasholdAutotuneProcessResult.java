package ai.gebo.architecture.rag_threasholds_autotune.model;

import java.util.Date;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class ThreasholdAutotuneProcessResult extends GBaseObject {
	private OptimizedThreashold threasholds = new OptimizedThreashold();
	private String rootKnowledgeBase = null;
	private String vectorStoreId = null;
	private String embeddingModelCode = null;
	private Long vectorStoreVectorizedCount = null;
	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	private Date processedDateTime = null;

}
