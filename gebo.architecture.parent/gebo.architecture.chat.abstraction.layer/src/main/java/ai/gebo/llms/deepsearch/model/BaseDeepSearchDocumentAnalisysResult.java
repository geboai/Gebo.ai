package ai.gebo.llms.deepsearch.model;

import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseDeepSearchDocumentAnalisysResult extends GBaseObject {
	@NotNull
	@HashIndexed
	String deepsearchCode = null;
	private Boolean emptyResult = null;
	private String analisysResult = null;
	@NotNull
	private DeepSearchAnalyzedDocument analyzedDocument = null;
	private double processPercentage=0.0;
}
