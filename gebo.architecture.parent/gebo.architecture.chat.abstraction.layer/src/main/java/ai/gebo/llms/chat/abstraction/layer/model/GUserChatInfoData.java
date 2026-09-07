package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;

import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import lombok.Data;

@Data
public class GUserChatInfoData implements GUserChatInfo {
	private String code, username, description, contextCode, chatProfileCode, chatModelCode;
	private Date chatCreationDateTime;
	private Boolean ragChat = null;

	public GUserChatInfoData() {
	}

	public GUserChatInfoData(GUserChatInfo info) {
		this.code = info.getCode();
		this.username = info.getUsername();
		this.description = info.getDescription();
		this.contextCode = info.getContextCode();
		this.chatProfileCode = info.getChatProfileCode();
		this.chatModelCode = info.getChatModelCode();
		this.chatCreationDateTime = info.getChatCreationDateTime();
		this.ragChat = info.getRagChat();
	}

	public GUserChatInfoData(GUserChatSession info) {
		this.code = info.getCode();
		this.username = info.getUsername();
		this.description = info.getDescription();
		this.contextCode = info.getContextCode();
		this.chatProfileCode = info.getChatProfileCode();
		this.chatModelCode = info.getChatModelCode();
		this.chatCreationDateTime = info.getChatCreationDateTime();
		this.ragChat = info.getRagChat();
	}

}
