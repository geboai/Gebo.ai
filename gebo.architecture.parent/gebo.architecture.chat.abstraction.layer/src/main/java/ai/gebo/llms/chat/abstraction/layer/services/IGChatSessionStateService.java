package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.llms.chat.abstraction.layer.model.ChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;

public interface IGChatSessionStateService {
	public ChatSessionState extractState(GeboChatRequest request, GUserChatContext context) throws IOException;
}
