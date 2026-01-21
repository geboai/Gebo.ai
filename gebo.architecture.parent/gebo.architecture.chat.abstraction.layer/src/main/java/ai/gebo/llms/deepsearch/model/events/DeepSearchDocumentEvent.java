package ai.gebo.llms.deepsearch.model.events;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class DeepSearchDocumentEvent
		extends AbstractDeepSearchEvent<GDocumentReference, DeepSearchDocumentAnalisysResultStep> {

}
