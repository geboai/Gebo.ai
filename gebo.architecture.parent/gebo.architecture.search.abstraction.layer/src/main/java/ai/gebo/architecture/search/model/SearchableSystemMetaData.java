package ai.gebo.architecture.search.model;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class SearchableSystemMetaData<SystemConfigurationReference, SystemType> extends GBaseObject {
	private SystemConfigurationReference systemConfigurationReference = null;
	private SystemType systemType = null;

}
