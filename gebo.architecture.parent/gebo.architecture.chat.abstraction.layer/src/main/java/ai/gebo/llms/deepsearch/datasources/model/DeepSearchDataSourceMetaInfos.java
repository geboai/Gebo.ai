package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.CatalogueSample;
import lombok.Data;

@Data
public class DeepSearchDataSourceMetaInfos {
	private String handlerId = null;
	private String description = null;
	private List<CatalogueSample> catalogues = new ArrayList<CatalogueSample>();

}
