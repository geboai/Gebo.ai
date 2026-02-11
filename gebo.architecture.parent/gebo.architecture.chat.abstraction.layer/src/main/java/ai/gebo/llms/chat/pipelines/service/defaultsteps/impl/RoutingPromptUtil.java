package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;

final class RoutingPromptUtil {

	private static final String SPACE = " ";
	private static final String LIST_ITEM = "-";
	private static final String END_ROUTING_DECISION = "<<<END_ROUTING_DECISION>>>";
	private static final String ROUTING_DECISION = "<<<ROUTING_DECISION>>>";
	private static final String END_MAIN_SECTIONS_SAMPLE = "END_MAIN_SECTIONS_SAMPLE";
	private static final String MAIN_SECTIONS_SAMPLE = "MAIN_SECTIONS_SAMPLE";

	private static String documentRendering(AIDocumentReferenceItem doc, int maxTokenBudget) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(DOC_BEGIN);
		buffer.append(NEWLINE);
		buffer.append(DOCUMENT_CODE);
		buffer.append(doc.getCode());
		buffer.append(NEWLINE);
		if (doc.getName() != null) {
			buffer.append(DOCUMENT_NAME);
			buffer.append(doc.getName());
			buffer.append(NEWLINE);
		}
		Map<String, Object> meta = doc.getFragments().get(0).getMetaData();
		ExtractedDocumentMetaData md = ExtractedDocumentMetaData.of(meta);
		String title = md.getTitle();
		if (title != null) {
			buffer.append(DOCUMENT_TITLE);
			buffer.append(title.trim());
			buffer.append(NEWLINE);
		}
		String language = md.getLanguage();
		if (language != null) {
			buffer.append(LANGUAGE);
			buffer.append(language);
			buffer.append(NEWLINE);
		}
		int totalSegments = md.getChunksTotal() != null ? md.getChunksTotal() : doc.getFragments().size();
		StringBuffer content = new StringBuffer();
		int consideredSegments = 0;
		int totalTokens = tokensLength(buffer.toString(), CONTENT_BEGIN, CONTENT_END, DOC_END);
		for (AIDocumentFragment fragment : doc.getFragments()) {
			String text = fragment.getDocumentContent();
			int tokensCount = fragment.getTokensSize();
			if (tokensCount == 0 && text != null) {
				tokensCount = tokensLength(text);
			}
			consideredSegments++;
			if ((totalTokens + tokensCount) <= maxTokenBudget) {
				content.append(text);
				content.append(NEWLINE);
				totalTokens += tokensCount;
			} else {
				int chars2considerApprox = (maxTokenBudget - totalTokens) * 4;
				String cutText = text.substring(0, Math.min(chars2considerApprox, text.length() - 1));
				content.append(cutText);
				content.append(NEWLINE);
			}
		}
		buffer.append(CONTENT_BEGIN);
		buffer.append(NEWLINE);
		buffer.append(content.toString());
		buffer.append(CONTENT_END);
		buffer.append(NEWLINE);
		buffer.append(DOC_END);
		buffer.append(NEWLINE);
		return buffer.toString();
	}

	static String documentsPromptPart(LLMChatRequestResources requestResources, int documentsTokenBudget) {
		StringBuffer buffer = new StringBuffer();

		AIDocumentsSet lastDocs = addUntillBudgetLatestDocuments(requestResources, documentsTokenBudget);
		List<AIDocumentReferenceItem> docs = lastDocs.getDocumentItems();
		if (!docs.isEmpty()) {
			buffer.append(DOCUMENTS_CATALOG);
			buffer.append(NEWLINE);
			int totaltokens = ITokensCountable.tokensSize(docs);
			String docRendered = null;
			if (documentsTokenBudget <= totaltokens) {
				docRendered = fullDocumentRendering(docs);
			} else {
				docRendered = partialDocumentRendering(docs, documentsTokenBudget);
			}
			buffer.append(docRendered);
			buffer.append(END_DOCUMENTS_CATALOG);
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

	private static AIDocumentsSet addUntillBudgetLatestDocuments(LLMChatRequestResources requestResources,
			int documentsTokenBudget) {
		AtomicInteger tokensBudget = new AtomicInteger(documentsTokenBudget);
		AIDocumentsSet result = new AIDocumentsSet();
		result = tryAdd(result, requestResources.getRetrievedDocuments(), tokensBudget);
		result = tryAdd(result, requestResources.getChatWithDocuments(), tokensBudget);
		result = tryAdd(result, requestResources.getUploadedDocuments(), tokensBudget);
		result = tryAdd(result, requestResources.getLlmGeneratedDocuments(), tokensBudget);
		return result;
	}

	private static AIDocumentsSet tryAdd(AIDocumentsSet ds, AIDocumentsSet toBeAdd, AtomicInteger tokensBudget) {
		if (toBeAdd != null && tokensBudget.get() >= toBeAdd.getTokensSize()) {
			ds = AIDocumentsSet.join(ds, toBeAdd);
			tokensBudget.addAndGet(-1 * toBeAdd.getTokensSize());
		}
		return ds;
	}

	private static String fullDocumentRendering(List<AIDocumentReferenceItem> docs) {
		StringBuffer buffer = new StringBuffer();
		for (AIDocumentReferenceItem doc : docs) {
			if (doc.getFragments().isEmpty())
				continue;
			buffer.append(documentRendering(doc, Integer.MAX_VALUE));
		}
		return buffer.toString();
	}

	private static String partialDocumentRendering(List<AIDocumentReferenceItem> docs, int documentsTokenBudget) {
		StringBuffer buffer = new StringBuffer();
		for (AIDocumentReferenceItem doc : docs) {
			String _docRendered = documentRendering(doc, documentsTokenBudget);
			int tokensCount = tokensLength(_docRendered);
			if (documentsTokenBudget >= tokensCount) {
				buffer.append(_docRendered);
				buffer.append(NEWLINE);
				documentsTokenBudget -= tokensCount;
			} else {
				break;
			}
		}
		return buffer.toString();
	}

	private static int tokensLength(String... values) {
		return ITokensCountable.stringsTokensSize(values);
	}

	private static final String DOC_BEGIN = "DOC_BEGIN";
	private static final String DOC_END = "DOC_END";
	private static final String DOCUMENT_CODE = "document-code: ";
	private static final String DOCUMENT_NAME = "document-name: ";
	private static final String DOCUMENT_TITLE = "document-title: ";
	private static final String DOCUMENTS_CATALOG = "DOCUMENTS_CATALOG";
	private static final String END_DOCUMENTS_CATALOG = "END_DOCUMENTS_CATALOG";
	private static final String LANGUAGE = "language: ";
	private static final String NEWLINE = "\r\n";
	private static final String CONTENT_BEGIN = "CONTENT_BEGIN";
	private static final String CONTENT_END = "CONTENT_END";

	static String toolsListPromptPart(List<ToolCategoriesTree> tools) {
		StringBuffer buffer = new StringBuffer();
		if (tools != null && !tools.isEmpty()) {
			buffer.append(TOOLS_CATALOG);
			buffer.append(NEWLINE);

			for (ToolCategoriesTree tree : tools) {
				ToolsCategory category = tree.getCategory();
				for (ToolReference tool : tree.getToolsReference()) {
					buffer.append(TOOL_BEGIN);
					buffer.append(NEWLINE);
					buffer.append(TOOL_CODE);
					buffer.append(tool.getName());
					buffer.append(NEWLINE);
					if (tool.getDescription() != null) {
						buffer.append(TOOL_DESCRIPTION);
						buffer.append(tool.getDescription());
						buffer.append(NEWLINE);
					}
					if (category != null) {
						buffer.append(TOOL_CATEGORY_DESCRIPTION);
						buffer.append(category.getDescription());
						buffer.append(NEWLINE);
					}
					buffer.append(TOOL_END);
					buffer.append(NEWLINE);
				}
				buffer.append(NEWLINE);
			}
			buffer.append(END_TOOLS_CATALOG);
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

	private static final String TOOL_BEGIN = "TOOL_BEGIN";
	private static final String TOOL_CATEGORY_DESCRIPTION = "TOOL_CATEGORY_DESCRIPTION: ";
	private static final String TOOL_CODE = "TOOL_NAME: ";
	private static final String TOOL_DESCRIPTION = "TOOL_DESCRIPTION: ";
	private static final String TOOL_END = "TOOL_END";
	private static final String TOOLS_CATALOG = "TOOLS_CATALOG";

	private static final String END_TOOLS_CATALOG = "END_TOOLS_CATALOG";

	static String latestInteractionsPromptPart(List<CSSSimplefiedInteraction> lastInteractions) {
		StringBuffer buffer = new StringBuffer();
		if (lastInteractions != null) {
			for (int i = lastInteractions.size() - 1; i >= 0; i--) {
				CSSSimplefiedInteraction interaction = lastInteractions.get(i);
				if (interaction.getUser() != null) {
					buffer.append(USER_TURN_START);
					buffer.append(NEWLINE);
					buffer.append(interaction.getUser());
					buffer.append(NEWLINE);
					buffer.append(USER_TURN_END);
					buffer.append(NEWLINE);
				}
				if (interaction.getPipelineRoutingDecision() != null) {
					buffer.append(ROUTING_DECISION);
					buffer.append(NEWLINE);
					buffer.append(interaction.getPipelineRoutingDecision());
					buffer.append(NEWLINE);
					buffer.append(END_ROUTING_DECISION);
					buffer.append(NEWLINE);
				}
				if (interaction.getAssistant() != null) {
					buffer.append(ASSISTANT_TURN_START);
					buffer.append(NEWLINE);

					buffer.append(interaction.getAssistant());
					buffer.append(NEWLINE);
					buffer.append(ASSISTANT_TURN_END);
					buffer.append(NEWLINE);
				}
			}
		}
		return buffer.toString();
	}

	private static final String ASSISTANT_TURN_END = "<<<END_ASSISTANT>>>";
	private static final String ASSISTANT_TURN_START = "<<<ASSISTANT>>>";
	private static final String USER_TURN_END = "<<<END_USER>>>";
	private static final String USER_TURN_START = "<<<USER>>>";

	/*
	 * static String dataSourcesListPromptPart(List<GBaseObject> dataSources) {
	 * StringBuffer buffer = new StringBuffer(); if (dataSources != null &&
	 * !dataSources.isEmpty()) { buffer.append(DEEP_SEARCH_DATA_SOURCES_CATALOG);
	 * buffer.append(NEWLINE); for (GBaseObject obj : dataSources) {
	 * buffer.append(CODE); buffer.append(obj.getCode()); buffer.append(NEWLINE);
	 * buffer.append(DESCRIPTION); buffer.append(obj.getDescription());
	 * buffer.append(NEWLINE); }
	 * buffer.append(END_DEEP_SEARCH_DATA_SOURCES_CATALOG); buffer.append(NEWLINE);
	 * } return buffer.toString(); }
	 */

	private static final String CODE = "- code: ";
	private static final String DESCRIPTION = "  description:";
	private static final String DEEP_SEARCH_DATA_SOURCES_CATALOG = "DEEP_SEARCH_DATA_SOURCES_CATALOG";
	private static final String END_DEEP_SEARCH_DATA_SOURCES_CATALOG = "END_DEEP_SEARCH_DATA_SOURCES_CATALOG";

	public static Object dataSourcesListPromptPart(List<DeepSearchDataSourceMetaInfos> dataSources) {
		StringBuffer buffer = new StringBuffer();
		if (dataSources != null && !dataSources.isEmpty()) {
			buffer.append(DEEP_SEARCH_DATA_SOURCES_CATALOG);
			buffer.append(NEWLINE);
			for (DeepSearchDataSourceMetaInfos obj : dataSources) {
				buffer.append(CODE);
				buffer.append(obj.getHandlerId());
				buffer.append(NEWLINE);
				buffer.append(DESCRIPTION);
				buffer.append(obj.getDescription());
				buffer.append(NEWLINE);
				if (obj.getCatalogues() != null && !obj.getCatalogues().isEmpty()) {
					buffer.append(MAIN_SECTIONS_SAMPLE);
					buffer.append(NEWLINE);
					for (String catalog : obj.getCatalogues()) {
						buffer.append(LIST_ITEM);
						buffer.append(SPACE);
						buffer.append(catalog);
						buffer.append(NEWLINE);
					}
					buffer.append(END_MAIN_SECTIONS_SAMPLE);
					buffer.append(NEWLINE);
				}
			}
			buffer.append(END_DEEP_SEARCH_DATA_SOURCES_CATALOG);
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

}
