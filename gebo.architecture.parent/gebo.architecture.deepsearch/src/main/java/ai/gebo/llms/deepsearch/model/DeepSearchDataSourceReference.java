package ai.gebo.llms.deepsearch.model;

import java.util.UUID;

import lombok.Data;

@Data
public class DeepSearchDataSourceReference {
	private String referenceUri = null;
	private String description = null;
	private String id = UUID.randomUUID().toString();
}
