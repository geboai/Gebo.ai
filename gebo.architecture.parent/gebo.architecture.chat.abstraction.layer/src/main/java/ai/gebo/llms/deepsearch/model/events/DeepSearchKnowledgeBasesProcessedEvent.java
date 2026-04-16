package ai.gebo.llms.deepsearch.model.events;

import ai.gebo.llms.deepsearch.model.DeepSearchKnowledgebasesResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import lombok.ToString;
@ToString
public class DeepSearchKnowledgeBasesProcessedEvent extends AbstractDeepSearchEvent<DeepSearchRequest, DeepSearchKnowledgebasesResultStep>{

}
