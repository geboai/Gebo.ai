package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromptsParametersCache {

	public static final String SEPARATOR = "-|-";
	@NotNull
	private String code = null;
	@NotNull
	@NotEmpty
	private Map<String, Object> promptsParameters = new HashMap<String, Object>();
	@NotNull
	private String promptUse = null;
	@NotNull
	private String userChatContext = null;
	@NotNull
	private String contextKey = null;
	@NotNull
	private String username = null;
	@NotNull
	private String langCode = "en";
	@NotNull
	private Date creationDateTime = new Date();
	@NotNull
	private Date lastHitDateTime = new Date();

	public static String calculateCode(String promptUse, String userChatContext, String contextKey, String username,
			String langCode) {
		String _code = userChatContext + SEPARATOR + promptUse + SEPARATOR + contextKey + SEPARATOR + username
				+ SEPARATOR + langCode;
		return _code;
	}

	public void recalculateCode() {
		setCode(calculateCode(promptUse, userChatContext, contextKey, username, langCode));
	}
}
