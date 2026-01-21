package ai.gebo.llms.chat.pipelines.model;

import lombok.Data;

@Data
public abstract class AbstractContextEnrichingData {
	private Long renderedTokensLength = null;
	public abstract String render();
}
