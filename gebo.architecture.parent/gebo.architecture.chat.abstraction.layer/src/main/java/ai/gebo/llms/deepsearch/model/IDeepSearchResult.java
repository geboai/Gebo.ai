package ai.gebo.llms.deepsearch.model;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;

public interface IDeepSearchResult {

	public String getResponse();

	public Boolean getSearchResultsEmpty();

	public String getDeepsearchCode();

	public String getProcessingModel();

	public void setProcessingModel(String model);

	public String getDataSourceDescription();

	public double getProcessPercentage();

	public void setProcessPercentage(double p);

	public default void processedBy(IGConfigurableChatModel model) {
		String modelCode = model != null && model.getConfig() != null && model.getConfig().getChoosedModel() != null
				? model.getConfig().getChoosedModel().getCode()
				: null;
		setProcessingModel(modelCode);
	}

}
