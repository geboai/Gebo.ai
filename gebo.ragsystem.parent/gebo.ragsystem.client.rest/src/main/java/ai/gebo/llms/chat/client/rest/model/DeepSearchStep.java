package ai.gebo.llms.chat.client.rest.model;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeepSearchStep {
	final GResponseDocumentRef documentRef;
	final String analisysPortion;
	final double processPercentage;

}