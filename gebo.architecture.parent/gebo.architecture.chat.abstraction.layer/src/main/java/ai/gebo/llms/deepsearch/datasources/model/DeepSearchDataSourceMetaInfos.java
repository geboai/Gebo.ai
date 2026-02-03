package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DeepSearchDataSourceMetaInfos {
	private String handlerId = null;
	private String description = null;
	private List<String> catalogues = new ArrayList<String>();

}
