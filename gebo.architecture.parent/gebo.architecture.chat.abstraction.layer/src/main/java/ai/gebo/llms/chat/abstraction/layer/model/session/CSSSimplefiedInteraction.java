package ai.gebo.llms.chat.abstraction.layer.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSSimplefiedInteraction implements ITokensCountable {
	private String user = null;
	private Integer userTokenSize = null;
	private String assistant = null;
	private Integer assistantTokenSize = null;

	@Override
	public int getTokensSize() {

		return (userTokenSize != null ? userTokenSize : 0) + (assistantTokenSize != null ? assistantTokenSize : 0);
	}
}
