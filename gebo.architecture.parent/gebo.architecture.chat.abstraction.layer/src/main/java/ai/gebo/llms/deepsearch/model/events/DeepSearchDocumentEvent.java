package ai.gebo.llms.deepsearch.model.events;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import lombok.Data;

@Data
public class DeepSearchDocumentEvent
		extends AbstractDeepSearchEvent<GDocumentReference, DeepSearchDocumentAnalisysResultStep> {

}
