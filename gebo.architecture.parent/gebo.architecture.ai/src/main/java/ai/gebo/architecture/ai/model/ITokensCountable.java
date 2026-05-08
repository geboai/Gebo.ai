package ai.gebo.architecture.ai.model;

import java.util.List;
import java.util.Map;

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
		int totalTokens = 0;
		if (contents != null) {
			for (int i = 0; i < contents.length; i++) {
				String content = contents[i];
				if (content != null) {
					totalTokens += tokensEstimator.estimate(content);
				}
			}
		}
		return totalTokens;
	}

	public static int tokensSize(Map<String, Object> params) {
		String variables[]=new String[params.size()];
		int index=0;
		for(Object value:params.values()) {
			variables[index]=value.toString();
		}
		return stringsTokensSize(variables);
	}
}
