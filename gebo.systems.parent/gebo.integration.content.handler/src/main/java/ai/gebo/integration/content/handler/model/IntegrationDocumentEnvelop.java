package ai.gebo.integration.content.handler.model;

import ai.gebo.document.model.GeboDocument;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IntegrationDocumentEnvelop {
	@NotNull
	private GeboDocument document = null;
	

}
