package ai.gebo.llms.chat.abstraction.layer.model.session;

public interface ITokensCountable {
	public int getTokensSize();

	public default int tokensSize(ITokensCountable... data) {
		int toks = 0;
		if (data != null) {
			for (ITokensCountable iTokensCountable : data) {
				if (iTokensCountable != null) {
					toks += iTokensCountable.getTokensSize();
				}
			}
		}
		return toks;
	}
}
