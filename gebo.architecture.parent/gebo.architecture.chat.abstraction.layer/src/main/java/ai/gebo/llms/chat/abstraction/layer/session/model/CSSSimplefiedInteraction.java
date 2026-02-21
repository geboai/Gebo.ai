package ai.gebo.llms.chat.abstraction.layer.session.model;

import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSSimplefiedInteraction implements ITokensCountable, Cloneable {
	private String user = null;
	private Integer userTokenSize = null;
	private String assistant = null;
	private Integer assistantTokenSize = null;
	private String pipelineRoutingDecision = null;
	private Integer interactionIndex = null;
	private String requestId = null;

	@Override
	public int getTokensSize() {

		return (userTokenSize != null ? userTokenSize : 0) + (assistantTokenSize != null ? assistantTokenSize : 0);
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Exception cloning", e);
		}
	}
}
