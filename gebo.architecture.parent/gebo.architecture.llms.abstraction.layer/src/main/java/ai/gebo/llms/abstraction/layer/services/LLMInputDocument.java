package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LLMInputDocument {
	final String documentReference;
	final String documentUrl;
	final String title;
	final String text;

}