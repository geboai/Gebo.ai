package ai.gebo.architecture.ai.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
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

	/**
	 * The tokens of every value of the map (its keys are not counted), each sized by
	 * itself: a countable value gives its own size, any other its text's.
	 */
	public static int tokensSize(Map<String, Object> params) {
		if (params == null)
			return 0;
		int toks = 0;
		for (Object value : params.values()) {
			toks += valueTokensSize(value);
		}
		return toks;
	}

	/** The tokens of one value: its own size when countable, its text's otherwise, none when null. */
	private static int valueTokensSize(Object value) {
		if (value == null) {
			return 0;
		}
		if (value instanceof ITokensCountable countable) {
			return countable.getTokensSize();
		}
		return stringsTokensSize(value.toString());
	}
}
