package ai.gebo.llms.chat.abstraction.layer.services;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;

/****************************************************
 * Commonly used prompt templates substitutions regarding chat history
 */
public class CommonChatPromptParamsUtil {
	private static final String ASSISTANT_TURN_END = "<<<END_ASSISTANT>>>";
	private static final String ASSISTANT_TURN_START = "<<<ASSISTANT>>>";
	private static final String USER_TURN_END = "<<<END_USER>>>";
	private static final String USER_TURN_START = "<<<USER>>>";
	private static final String NL = "\r\n";
	private static final String TURN_SEP = NL + "-----" + NL;
	public static final String CURRENT_USER_QUESTION_PROMPT_PARAM = "question";
	public static final String LATEST_INTERACTIONS_PROMPT_PARAM = "latestInteractions";
	public static final String CONSOLIDATED_CHAT_HISTORY_PROMPT_PARAM = "consolidatedChatHistory";

	private static Map<String, Object> preparePromptParameters(LLMChatRequestResources request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CONSOLIDATED_CHAT_HISTORY_PROMPT_PARAM, renderConsolidatedChatHistory(request));
		params.put(LATEST_INTERACTIONS_PROMPT_PARAM, renderLatestInteractions(request));
		params.put(CURRENT_USER_QUESTION_PROMPT_PARAM, renderCurrentUserQuestion(request));
		return params;
	}

	public static Map<String, Object> preparePromptParameters(MinimalChatContext context) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CONSOLIDATED_CHAT_HISTORY_PROMPT_PARAM, renderConsolidatedChatHistory(context.getChatHistory()));
		params.put(LATEST_INTERACTIONS_PROMPT_PARAM, renderLatestInteractions(context.getChatHistory()));
		params.put(CURRENT_USER_QUESTION_PROMPT_PARAM, renderCurrentUserQuestion(context.getCurrentRequest()));
		return params;
	}

	public static String renderLatestInteractions(CSSConsolidatedChatHistory chatHistory) {

		return renderInteractions(chatHistory.getLatestEntries());
	}

	public static String renderConsolidatedChatHistory(CSSConsolidatedChatHistory chatHistory) {

		return chatHistory.getConsolidationText() != null ? chatHistory.getConsolidationText() : "";
	}

	public static String renderCurrentUserQuestion(LLMChatRequestResources request) {
		String question = renderCurrentUserQuestion(request.getCurrentRequest());
		return question;
	}

	public static String renderCurrentUserQuestion(GeboChatRequest lastRequest) {
		String question = GeboChatRequest.actualQuery(lastRequest);
		return question != null ? question : "";
	}

	public static String renderLatestInteractions(LLMChatRequestResources request) {

		return renderInteractions(request.getChathistory().getLatestEntries());
	}

	public static String renderInteractions(CSSSimplifiedChatHistory latestEntries) {
		List<CSSSimplefiedInteraction> interactions = latestEntries != null ? latestEntries.getInteractions()
				: List.of();
		StringBuffer sb = new StringBuffer();
		if (interactions != null && !interactions.isEmpty()) {

			for (CSSSimplefiedInteraction turn : interactions) {
				String user = safe(turn.getUser());
				String assistant = safe(turn.getAssistant());

				sb.append(USER_TURN_START).append(NL).append(user).append(NL).append(USER_TURN_END).append(NL);

				sb.append(ASSISTANT_TURN_START).append(NL).append(assistant).append(NL).append(ASSISTANT_TURN_END)
						.append(NL);

				sb.append(TURN_SEP);
			}
		}
		return sb.toString();
	}

	private static String safe(String s) {

		return s != null ? s : "";
	}

	public static String renderConsolidatedChatHistory(LLMChatRequestResources request) {

		return request.getChathistory().getConsolidationText() != null ? request.getChathistory().getConsolidationText()
				: "";
	}

}
