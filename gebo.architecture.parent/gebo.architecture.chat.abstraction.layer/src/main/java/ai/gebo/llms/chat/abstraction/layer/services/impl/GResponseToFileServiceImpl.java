package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGResponseToFileService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import io.github.twwch.markdown2office.Markdown2Office;
import io.github.twwch.markdown2office.model.FileType;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GResponseToFileServiceImpl implements IGResponseToFileService {
	private final GUserChatSessionRepository chatSessionRepository;
	private final IGSecurityService securityService;

	@Override
	public void convert(String userContextCode, String responseId, ResponseFormat format, OutputStream os)
			throws IOException {
		final GUserChatSession session = chatSessionRepository.findById(userContextCode)
				.orElseThrow(IllegalStateException::new);
		final UserInfos user = securityService.getCurrentUser();
		if (session.getUsername() != null && session.getUsername().equals(user.getUsername())
				&& session.getInteractions() != null) {
			GeboTemplatedChatResponse response = session.getInteractions().stream()
					.filter(x -> x.getResponse() != null && x.getResponse().getQueryResponse() != null
							&& x.getResponse().getId().equals(responseId))
					.map(y -> y.getResponse()).findFirst().orElseThrow(FileNotFoundException::new);
			Markdown2Office markdown = new Markdown2Office();
			FileType filetype;
			switch (format) {
			case EXCEL:
				filetype = FileType.EXCEL;
				break;
			case PDF:
				filetype = FileType.PDF;
				break;
			case WORD:
				filetype = FileType.WORD;
				break;
			default:
				filetype = FileType.WORD;
			}
			markdown.convert(response.getQueryResponse().toString(), filetype, os);
			os.flush();
		} else
			throw new SecurityException("You cannot access that chat session or that chat session is empty");

	}

}
