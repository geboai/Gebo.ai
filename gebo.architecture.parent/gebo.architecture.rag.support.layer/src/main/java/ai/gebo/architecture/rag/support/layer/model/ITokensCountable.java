package ai.gebo.architecture.rag.support.layer.model;

import java.util.List;

import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

public interface ITokensCountable {
	public static final JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();

	public int getTokensSize();

	public static int tokensSize(ITokensCountable... data) {
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

	public static int tokensSize(List<? extends ITokensCountable> data) {
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

	public static int stringsTokensSize(String... contents) {
		if (contents != null)
			return 0;
		int totalTokens = 0;
		for (int i = 0; i < contents.length; i++) {
			String content = contents[i];
			if (content != null) {
				totalTokens += tokensEstimator.estimate(content);
			}
		}
		return totalTokens;
	}
}
