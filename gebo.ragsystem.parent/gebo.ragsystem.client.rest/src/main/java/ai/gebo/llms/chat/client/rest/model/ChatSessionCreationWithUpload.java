package ai.gebo.llms.chat.client.rest.model;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatSessionCreationWithUpload {
	final List<UserUploadedContent> uploads;
	final GUserChatInfo chatInfo;
}
