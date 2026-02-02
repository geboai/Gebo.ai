package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchDataSourceSampledCatalogs extends GBaseObject {
	private static final String SEPARATOR = "-|-";
	@NotNull
	private String messagingModuleId = null;
	@NotNull
	private String messagingSystemId = null;
	@NotNull
	private String systemConfigurationCode = null;
	@NotNull @NotEmpty
	private List<String> catalogs = new ArrayList<String>();

	public void recalculateCode() {
		this.setCode(messagingModuleId + SEPARATOR + messagingSystemId + SEPARATOR + systemConfigurationCode);
	}
}
