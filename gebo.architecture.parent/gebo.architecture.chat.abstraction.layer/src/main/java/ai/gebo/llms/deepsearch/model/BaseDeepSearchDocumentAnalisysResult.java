package ai.gebo.llms.deepsearch.model;

import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseDeepSearchDocumentAnalisysResult extends GBaseObject {
	@NotNull
	@HashIndexed
	String deepsearchCode = null;
	private String processingModel = null;
	private Boolean emptyResult = null;
	private String analisysResult = null;
	@NotNull
	private DeepSearchAnalyzedDocument analyzedDocument = null;
	private double processPercentage = 0.0;

	public void processedBy(IGConfigurableChatModel model) {
		String modelCode = model != null && model.getConfig() != null && model.getConfig().getChoosedModel() != null
				? model.getConfig().getChoosedModel().getCode()
				: null;
		setProcessingModel(modelCode);
	}
}
