package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class DeepSearchResponse {
	String id = UUID.randomUUID().toString();
	String deepsearchId = null;
	List<DeepSearchDocumentAnalisysResultStep> steps = new ArrayList<DeepSearchDocumentAnalisysResultStep>();
	String response = null;

}
