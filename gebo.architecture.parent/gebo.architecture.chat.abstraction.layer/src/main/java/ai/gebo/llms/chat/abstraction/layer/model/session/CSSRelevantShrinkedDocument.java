package ai.gebo.llms.chat.abstraction.layer.model.session;

import lombok.Data;

@Data
public class CSSRelevantShrinkedDocument {
	private String documentReference = null;
	private String documentName = null;
	private String documentTitle = null;
	private String summarizedContent = null;
	private Float relevancyRate = null;
	private Integer tokensSize = null;	
}