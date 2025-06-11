package ai.gebo.integration.content.handler;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import lombok.Data;

@Data
public class GIntegrationProjectEndpoint extends GProjectEndpoint {

	public GIntegrationProjectEndpoint() {

	}

	private List<String> allowedApplicationUsers = new ArrayList<>();
}
