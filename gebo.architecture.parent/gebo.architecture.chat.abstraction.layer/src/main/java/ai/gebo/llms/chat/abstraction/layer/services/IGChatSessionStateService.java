package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatSessionState;

public interface IGChatSessionStateService {
	public ChatSessionState extractState(GeboChatRequest request, GUserChatContext context) throws IOException;
}
