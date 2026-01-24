package ai.gebo.llms.abstraction.layer.model;

import java.util.List;

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

	public default int tokensSize(List<? extends ITokensCountable> data) {
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
