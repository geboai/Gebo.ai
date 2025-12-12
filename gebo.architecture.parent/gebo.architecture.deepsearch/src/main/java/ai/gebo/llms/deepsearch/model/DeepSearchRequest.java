package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class DeepSearchRequest {
	String requestId = UUID.randomUUID().toString();
	String username = null;
	String query = null;
	List<String> knowledgeBases = new ArrayList<String>();
}
