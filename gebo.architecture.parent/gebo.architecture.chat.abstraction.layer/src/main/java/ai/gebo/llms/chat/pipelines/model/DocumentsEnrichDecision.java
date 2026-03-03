package ai.gebo.llms.chat.pipelines.model;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DocumentsEnrichDecision {
	private final LLMChatRequestResources requestResources;
	private final SearchesSuggestions searchesDecisions;
}