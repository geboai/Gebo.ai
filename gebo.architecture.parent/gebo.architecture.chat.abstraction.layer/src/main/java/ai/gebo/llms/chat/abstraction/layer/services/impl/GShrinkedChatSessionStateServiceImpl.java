package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.system.ingestion.GeboIngestionException;

public class GShrinkedChatSessionStateServiceImpl implements IGShrinkedChatSessionStateService {

	public GShrinkedChatSessionStateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShrinkedChatSessionState retrieveState(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteState(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ShrinkedChatSessionState addRequestToState(GeboChatRequest request, GUserChatContext context,
			int targetTokenBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addInteractionToState(GeboChatRequest request, GeboChatResponse response,
			GUserChatContext context, int targetTokenBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException {
		// TODO Auto-generated method stub
		return null;
	}

}
