package ai.gebo.llms.deepsearch.model;

import java.util.UUID;

import lombok.Data;

@Data
public class DeepSearchDocumentAnalisysResultStep {
	String id = UUID.randomUUID().toString();
	String deepsearchId = null;
	String fragment = null;	
	
}
