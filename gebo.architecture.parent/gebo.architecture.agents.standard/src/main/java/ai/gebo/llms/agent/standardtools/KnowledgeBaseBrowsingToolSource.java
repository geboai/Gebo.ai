/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.core.contents.security.services.VirtualFilesystemQuery;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentChunksReader.DocumentChunks;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.architecture.ai.model.ITokensCountable;
import org.springframework.ai.util.json.JsonParser;
import lombok.Data;

/**
 * Tools to know and walk the knowledge bases of the chat, query-by-example style,
 * each returning a list: how many documents they hold, their knowledge bases,
 * projects and project endpoints (content sources), their folders and documents,
 * and the whole content of documents by their uniqueId (see
 * {@link GAbstractVirtualFilesystemObject#getUniqueId()}).
 *
 * <p>
 * Everything is limited to the knowledge bases of the chat the tools are called for,
 * as its chat profile gives them (none when the chat has none), and to what the user
 * may read: the access rights apply as in the knowledge base search. The lists come
 * a page at a time and are fitted in the room the model call leaves to its tools.
 * </p>
 */
@Component
public class KnowledgeBaseBrowsingToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseBrowsingToolSource.class);

	public static final String KNOWLEDGE_BASE_BROWSING_TOOL_SOURCE = "knowledge-base-browsing-tool-source";
	public static final String COUNT_DOCUMENTS_TOOL = "countKnowledgeBaseDocuments";
	public static final String BROWSE_KNOWLEDGE_BASES_TOOL = "browseKnowledgeBases";
	public static final String BROWSE_PROJECTS_TOOL = "browseKnowledgeBaseProjects";
	public static final String BROWSE_PROJECT_ENDPOINTS_TOOL = "browseKnowledgeBaseProjectEndpoints";
	public static final String BROWSE_FOLDERS_TOOL = "browseKnowledgeBaseFolders";
	public static final String BROWSE_DOCUMENTS_TOOL = "browseKnowledgeBaseDocuments";
	public static final String DOCUMENT_CONTENTS_TOOL = "getKnowledgeBaseDocumentContents";
	/** Every tool of this source. */
	public static final Set<String> TOOLS = Set.of(COUNT_DOCUMENTS_TOOL, BROWSE_KNOWLEDGE_BASES_TOOL,
			BROWSE_PROJECTS_TOOL, BROWSE_PROJECT_ENDPOINTS_TOOL, BROWSE_FOLDERS_TOOL, BROWSE_DOCUMENTS_TOOL,
			DOCUMENT_CONTENTS_TOOL);

	/** Items in a page when the call does not say. */
	public static final int DEFAULT_PAGE_SIZE = 50;
	/** Most items in a page. */
	public static final int MAX_PAGE_SIZE = 200;
	static final String COUNT_DOCUMENTS_DESCRIPTION = "Counts the documents of the knowledge bases of this chat, in total and by knowledge base, "
			+ "optionally only those of a project or of a project endpoint (content source).";
	static final String BROWSE_KNOWLEDGE_BASES_DESCRIPTION = "Lists the knowledge bases of this chat (code, description, parent), a page at a time.";
	static final String BROWSE_PROJECTS_DESCRIPTION = "Lists the projects of the knowledge bases of this chat (code, description, knowledge base, parent project), "
			+ "optionally of one knowledge base or one parent project, a page at a time.";
	static final String BROWSE_PROJECT_ENDPOINTS_DESCRIPTION = "Lists the project endpoints (the content sources: folders, sites, repositories...) of a project, "
			+ "a page at a time.";
	static final String BROWSE_FOLDERS_DESCRIPTION = "Lists the folders of the knowledge bases of this chat (uniqueId, name, title, description, code, "
			+ "parentCode, customMetaData), filtered by knowledge base, project, project endpoint, parent folder, "
			+ "roots only or a part of the name, a page at a time.";
	static final String BROWSE_DOCUMENTS_DESCRIPTION = "Lists the documents of the knowledge bases of this chat (uniqueId, name, title, description, code, "
			+ "parentCode, customMetaData), filtered by knowledge base, project, project endpoint, parent folder, "
			+ "roots only or a part of the name, a page at a time. Use their uniqueId to read them whole.";
	static final String DOCUMENT_CONTENTS_DESCRIPTION = "Returns the whole text of documents of the knowledge bases of this chat by their uniqueId "
			+ "(from the document lists, or the documentUniqueId of the fragments a search returned), "
			+ "as far as the room left in the context allows.";

	private final ObjectProvider<IGKnowledgebaseVisibilityService> visibilityService;
	private final ObjectProvider<KnowledgeBaseDocumentChunksReader> chunksReader;

	public KnowledgeBaseBrowsingToolSource(ObjectProvider<IGKnowledgebaseVisibilityService> visibilityService,
			ObjectProvider<KnowledgeBaseDocumentChunksReader> chunksReader) {
		this.visibilityService = visibilityService;
		this.chunksReader = chunksReader;
	}

	// ----------------------------------------------------------------- parameters

	@Data
	public static class PageParam {
		@JsonPropertyDescription("Optional page number, from 0, 0 when not given")
		private Integer page;
		@JsonPropertyDescription("Optional number of items in a page, 50 when not given, at most 200")
		private Integer pageSize;
	}

	@Data
	@JsonClassDescription("Which documents to count")
	public static class CountDocumentsParam {
		@JsonPropertyDescription("Optional code of one knowledge base of the chat")
		private String knowledgeBaseCode;
		@JsonPropertyDescription("Optional code of a project")
		private String projectCode;
		@JsonPropertyDescription("Optional code of a project endpoint (content source)")
		private String projectEndpointCode;
	}

	@Data
	@lombok.EqualsAndHashCode(callSuper = true)
	@JsonClassDescription("Which knowledge bases to list")
	public static class BrowseKnowledgeBasesParam extends PageParam {
		@JsonPropertyDescription("Optional part of the code or of the description, ignoring case")
		private String textContains;
	}

	@Data
	@lombok.EqualsAndHashCode(callSuper = true)
	@JsonClassDescription("Which projects to list")
	public static class BrowseProjectsParam extends PageParam {
		@JsonPropertyDescription("Optional code of one knowledge base of the chat")
		private String knowledgeBaseCode;
		@JsonPropertyDescription("Optional code of the parent project, for its sub-projects")
		private String parentProjectCode;
		@JsonPropertyDescription("Optional part of the code or of the description, ignoring case")
		private String textContains;
	}

	@Data
	@lombok.EqualsAndHashCode(callSuper = true)
	@JsonClassDescription("Whose project endpoints to list")
	public static class BrowseProjectEndpointsParam extends PageParam {
		@JsonPropertyDescription("The code of the project")
		private String projectCode;
	}

	@Data
	@lombok.EqualsAndHashCode(callSuper = true)
	@JsonClassDescription("Which folders or documents to list")
	public static class BrowseVirtualFilesystemParam extends PageParam {
		@JsonPropertyDescription("Optional code of one knowledge base of the chat")
		private String knowledgeBaseCode;
		@JsonPropertyDescription("Optional code of a project")
		private String projectCode;
		@JsonPropertyDescription("Optional code of a project endpoint (content source)")
		private String projectEndpointCode;
		@JsonPropertyDescription("Optional code of the folder they are directly in")
		private String parentFolderCode;
		@JsonPropertyDescription("Optional: true for only those at the root of their project endpoint (in no folder)")
		private Boolean rootsOnly;
		@JsonPropertyDescription("Optional part of the name, ignoring case")
		private String nameContains;
	}

	@Data
	@JsonClassDescription("Which documents to read whole")
	public static class DocumentContentsParam {
		@JsonPropertyDescription("The uniqueIds of the documents")
		private List<Long> uniqueIds;
	}

	// -------------------------------------------------------------------- results

	/** A page of a list: its items, and where it stands in the whole list. */
	public record ListPage<T>(List<T> items, int page, int pageSize, long total, boolean hasMorePages,
			Integer itemsLeftOutForRoom, String message) {
	}

	public record KnowledgeBaseItem(String code, String description, String parentKnowledgeBaseCode) {
	}

	public record ProjectItem(String code, String description, String knowledgeBaseCode, String parentProjectCode) {
	}

	public record ProjectEndpointItem(String code, String description, String projectCode, String type) {
	}

	public record VirtualFilesystemItem(Long uniqueId, String name, String title, String description, String code,
			String parentCode, Map<String, Object> customMetaData) {
	}

	public record DocumentsCount(long total, Map<String, Long> byKnowledgeBase, String message) {
	}

	public record DocumentContent(Long uniqueId, String name, String code, String content, boolean complete,
			String message) {
	}

	// ---------------------------------------------------------------- the source

	@Override
	public String getId() {
		return KNOWLEDGE_BASE_BROWSING_TOOL_SOURCE;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.KNOWLEDGE_BASE_VARIOUS_SEARCHES;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		final List<ToolReference> references = new ArrayList<>();
		for (String[] tool : new String[][] { { COUNT_DOCUMENTS_TOOL, COUNT_DOCUMENTS_DESCRIPTION },
				{ BROWSE_KNOWLEDGE_BASES_TOOL, BROWSE_KNOWLEDGE_BASES_DESCRIPTION },
				{ BROWSE_PROJECTS_TOOL, BROWSE_PROJECTS_DESCRIPTION },
				{ BROWSE_PROJECT_ENDPOINTS_TOOL, BROWSE_PROJECT_ENDPOINTS_DESCRIPTION },
				{ BROWSE_FOLDERS_TOOL, BROWSE_FOLDERS_DESCRIPTION },
				{ BROWSE_DOCUMENTS_TOOL, BROWSE_DOCUMENTS_DESCRIPTION },
				{ DOCUMENT_CONTENTS_TOOL, DOCUMENT_CONTENTS_DESCRIPTION } }) {
			final ToolReference reference = new ToolReference();
			reference.setName(tool[0]);
			reference.setDescription(tool[1]);
			references.add(reference);
		}
		return references;
	}

	/**
	 * The browsing tools read the platform's catalogue of the knowledge bases; the
	 * contents tool reads the documents' chunks from the vector store, the query it
	 * needs going through the embedding model.
	 */
	@Override
	public List<ToolDataFlowTarget> getDataFlowTargets(String toolName) {
		if (DOCUMENT_CONTENTS_TOOL.equals(toolName)) {
			return List.of(
					ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.KNOWLEDGE_BASE_VECTOR_STORE,
							"Knowledge base documents read whole"),
					ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.EMBEDDING_MODEL, "Document name embedded to read its chunks"));
		}
		return TOOLS.contains(toolName)
				? List.of(ToolDataFlowTarget.platformData("Knowledge bases catalogue",
						"Knowledge bases, projects, folders and documents listed"))
				: List.of();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		final List<ToolCallback> callbacks = new ArrayList<>();
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<CountDocumentsParam, ToolContext, DocumentsCount>) (param, context) -> countDocuments(param,
						ToolCallbackDeclarationUtil.chatKnowledgeBases(context)),
				COUNT_DOCUMENTS_TOOL, COUNT_DOCUMENTS_DESCRIPTION, CountDocumentsParam.class, DocumentsCount.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<BrowseKnowledgeBasesParam, ToolContext, ListPage>) (param, context) -> browseKnowledgeBases(
						param, ToolCallbackDeclarationUtil.chatKnowledgeBases(context), ToolsTokenBudget.from(context)),
				BROWSE_KNOWLEDGE_BASES_TOOL, BROWSE_KNOWLEDGE_BASES_DESCRIPTION, BrowseKnowledgeBasesParam.class,
				ListPage.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<BrowseProjectsParam, ToolContext, ListPage>) (param, context) -> browseProjects(param,
						ToolCallbackDeclarationUtil.chatKnowledgeBases(context), ToolsTokenBudget.from(context)),
				BROWSE_PROJECTS_TOOL, BROWSE_PROJECTS_DESCRIPTION, BrowseProjectsParam.class, ListPage.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<BrowseProjectEndpointsParam, ToolContext, ListPage>) (param, context) -> browseProjectEndpoints(
						param, ToolCallbackDeclarationUtil.chatKnowledgeBases(context), ToolsTokenBudget.from(context)),
				BROWSE_PROJECT_ENDPOINTS_TOOL, BROWSE_PROJECT_ENDPOINTS_DESCRIPTION, BrowseProjectEndpointsParam.class,
				ListPage.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<BrowseVirtualFilesystemParam, ToolContext, ListPage>) (param, context) -> browseFolders(param,
						ToolCallbackDeclarationUtil.chatKnowledgeBases(context), ToolsTokenBudget.from(context)),
				BROWSE_FOLDERS_TOOL, BROWSE_FOLDERS_DESCRIPTION, BrowseVirtualFilesystemParam.class, ListPage.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<BrowseVirtualFilesystemParam, ToolContext, ListPage>) (param, context) -> browseDocuments(param,
						ToolCallbackDeclarationUtil.chatKnowledgeBases(context), ToolsTokenBudget.from(context)),
				BROWSE_DOCUMENTS_TOOL, BROWSE_DOCUMENTS_DESCRIPTION, BrowseVirtualFilesystemParam.class, ListPage.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<DocumentContentsParam, ToolContext, List>) (param, context) -> documentContents(param,
						ToolCallbackDeclarationUtil.chatKnowledgeBases(context), ToolsTokenBudget.from(context)),
				DOCUMENT_CONTENTS_TOOL, DOCUMENT_CONTENTS_DESCRIPTION, DocumentContentsParam.class, List.class));
		return callbacks;
	}

	// ---------------------------------------------------------------------- scope

	/**
	 * The codes of the knowledge bases a call may read: those of the chat the tool is
	 * called for, as its chat profile gives them (see
	 * {@link ToolCallbackDeclarationUtil#chatKnowledgeBases(org.springframework.ai.chat.model.ToolContext)}),
	 * none when the chat has none; only the requested one when the call names one of them.
	 */
	List<String> scope(List<String> chatKnowledgeBases, String requestedKnowledgeBaseCode) {
		final Set<String> codes = new LinkedHashSet<>();
		if (chatKnowledgeBases != null) {
			for (String code : chatKnowledgeBases) {
				if (notBlank(code)) {
					codes.add(code);
				}
			}
		}
		final List<String> scope;
		if (notBlank(requestedKnowledgeBaseCode)) {
			scope = codes.contains(requestedKnowledgeBaseCode) ? List.of(requestedKnowledgeBaseCode) : List.of();
		} else {
			scope = new ArrayList<>(codes);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("scope(...) chat knowledge bases:" + codes + " requested:" + requestedKnowledgeBaseCode + " -> "
					+ scope);
		}
		return scope;
	}

	static int pageOf(PageParam param) {
		return param != null && param.getPage() != null && param.getPage() > 0 ? param.getPage() : 0;
	}

	static int pageSizeOf(PageParam param) {
		final Integer asked = param != null ? param.getPageSize() : null;
		if (asked == null || asked <= 0) {
			return DEFAULT_PAGE_SIZE;
		}
		return Math.min(asked, MAX_PAGE_SIZE);
	}

	// ---------------------------------------------------------------------- tools

	DocumentsCount countDocuments(CountDocumentsParam param, List<String> chatKnowledgeBases) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin countDocuments(" + param + ")");
		}
		try {
			final List<String> scope = scope(chatKnowledgeBases, param != null ? param.getKnowledgeBaseCode() : null);
			if (scope.isEmpty()) {
				return new DocumentsCount(0, Map.of(), noKnowledgeBaseMessage(param != null ? param.getKnowledgeBaseCode() : null));
			}
			final Map<String, Long> byKnowledgeBase = new LinkedHashMap<>();
			long total = 0;
			for (String kbCode : scope) {
				final long count = visibilityService.getObject()
						.countVisibleDocuments(VirtualFilesystemQuery.builder().knowledgeBaseCodes(List.of(kbCode))
								.projectCode(param != null ? param.getProjectCode() : null)
								.projectEndpointCode(param != null ? param.getProjectEndpointCode() : null).build());
				byKnowledgeBase.put(kbCode, count);
				total += count;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End countDocuments(...) " + total + " document(s) " + byKnowledgeBase);
			}
			return new DocumentsCount(total, byKnowledgeBase, null);
		} catch (RuntimeException e) {
			LOGGER.error("countDocuments(...) failed", e);
			return new DocumentsCount(0, Map.of(), "The documents could not be counted: go on without this count.");
		}
	}

	ListPage<KnowledgeBaseItem> browseKnowledgeBases(BrowseKnowledgeBasesParam param, List<String> chatKnowledgeBases,
			ToolsTokenBudget budget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin browseKnowledgeBases(" + param + ")");
		}
		try {
			final List<String> scope = scope(chatKnowledgeBases, null);
			if (scope.isEmpty()) {
				return empty(param, noKnowledgeBaseMessage(null));
			}
			final List<KnowledgeBaseItem> items = new ArrayList<>();
			for (GKnowledgeBase kb : visibilityService.getObject().getVisibleKnowledgeBaseByCodes(scope)) {
				if (kb != null && contains(param != null ? param.getTextContains() : null, kb.getCode(), kb.getDescription())) {
					items.add(new KnowledgeBaseItem(kb.getCode(), kb.getDescription(), kb.getParentKnowledgebaseCode()));
				}
			}
			return page(items, param, budget, BROWSE_KNOWLEDGE_BASES_TOOL);
		} catch (RuntimeException e) {
			LOGGER.error("browseKnowledgeBases(...) failed", e);
			return failed(param, BROWSE_KNOWLEDGE_BASES_TOOL);
		}
	}

	ListPage<ProjectItem> browseProjects(BrowseProjectsParam param, List<String> chatKnowledgeBases, ToolsTokenBudget budget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin browseProjects(" + param + ")");
		}
		try {
			final List<String> scope = scope(chatKnowledgeBases, param != null ? param.getKnowledgeBaseCode() : null);
			if (scope.isEmpty()) {
				return empty(param, noKnowledgeBaseMessage(param != null ? param.getKnowledgeBaseCode() : null));
			}
			final List<ProjectItem> items = new ArrayList<>();
			for (String kbCode : scope) {
				for (GProject project : visibilityService.getObject().getVisibleProjectsByKnowledgeBaseCode(kbCode)) {
					if (project == null) {
						continue;
					}
					if (notBlank(param != null ? param.getParentProjectCode() : null)
							&& !param.getParentProjectCode().equals(project.getParentProjectCode())) {
						continue;
					}
					if (contains(param != null ? param.getTextContains() : null, project.getCode(), project.getDescription())) {
						items.add(new ProjectItem(project.getCode(), project.getDescription(), project.getRootKnowledgeBaseCode(),
								project.getParentProjectCode()));
					}
				}
			}
			return page(items, param, budget, BROWSE_PROJECTS_TOOL);
		} catch (RuntimeException e) {
			LOGGER.error("browseProjects(...) failed", e);
			return failed(param, BROWSE_PROJECTS_TOOL);
		}
	}

	ListPage<ProjectEndpointItem> browseProjectEndpoints(BrowseProjectEndpointsParam param, List<String> chatKnowledgeBases,
			ToolsTokenBudget budget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin browseProjectEndpoints(" + param + ")");
		}
		if (param == null || !notBlank(param.getProjectCode())) {
			return empty(param, "Give the code of the project whose endpoints to list.");
		}
		try {
			final List<String> scope = scope(chatKnowledgeBases, null);
			// the project must be one of the chat's knowledge bases
			boolean inScope = false;
			for (String kbCode : scope) {
				for (GProject project : visibilityService.getObject().getVisibleProjectsByKnowledgeBaseCode(kbCode)) {
					if (project != null && param.getProjectCode().equals(project.getCode())) {
						inScope = true;
					}
				}
			}
			if (!inScope) {
				return empty(param, "The project " + param.getProjectCode()
						+ " is not among the projects of this chat's knowledge bases the user can see.");
			}
			final List<ProjectEndpointItem> items = new ArrayList<>();
			for (GProjectEndpoint endpoint : visibilityService.getObject()
					.getVisibleProjectsEndpointByParentProjectCode(param.getProjectCode())) {
				if (endpoint != null) {
					items.add(new ProjectEndpointItem(endpoint.getCode(), endpoint.getDescription(),
							endpoint.getParentProjectCode(), endpoint.getClass().getSimpleName()));
				}
			}
			return page(items, param, budget, BROWSE_PROJECT_ENDPOINTS_TOOL);
		} catch (RuntimeException e) {
			LOGGER.error("browseProjectEndpoints(...) failed", e);
			return failed(param, BROWSE_PROJECT_ENDPOINTS_TOOL);
		}
	}

	ListPage<VirtualFilesystemItem> browseFolders(BrowseVirtualFilesystemParam param, List<String> chatKnowledgeBases,
			ToolsTokenBudget budget) {
		return browseVirtualFilesystem(param, chatKnowledgeBases, budget, true);
	}

	ListPage<VirtualFilesystemItem> browseDocuments(BrowseVirtualFilesystemParam param, List<String> chatKnowledgeBases,
			ToolsTokenBudget budget) {
		return browseVirtualFilesystem(param, chatKnowledgeBases, budget, false);
	}

	ListPage<VirtualFilesystemItem> browseVirtualFilesystem(BrowseVirtualFilesystemParam param, List<String> chatKnowledgeBases,
			ToolsTokenBudget budget, boolean folders) {
		final String tool = folders ? BROWSE_FOLDERS_TOOL : BROWSE_DOCUMENTS_TOOL;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin " + tool + "(" + param + ")");
		}
		try {
			final List<String> scope = scope(chatKnowledgeBases, param != null ? param.getKnowledgeBaseCode() : null);
			if (scope.isEmpty()) {
				return empty(param, noKnowledgeBaseMessage(param != null ? param.getKnowledgeBaseCode() : null));
			}
			final VirtualFilesystemQuery query = VirtualFilesystemQuery.builder().knowledgeBaseCodes(scope)
					.projectCode(param != null ? param.getProjectCode() : null)
					.projectEndpointCode(param != null ? param.getProjectEndpointCode() : null)
					.parentVirtualFolderCode(param != null ? param.getParentFolderCode() : null)
					.rootsOnly(param != null && Boolean.TRUE.equals(param.getRootsOnly()))
					.nameContains(param != null ? param.getNameContains() : null).build();
			final int page = pageOf(param);
			final int pageSize = pageSizeOf(param);
			final IGKnowledgebaseVisibilityService visibility = visibilityService.getObject();
			final Page<? extends GAbstractVirtualFilesystemObject> found = folders
					? visibility.browseVisibleVirtualFolders(query, PageRequest.of(page, pageSize))
					: visibility.browseVisibleDocuments(query, PageRequest.of(page, pageSize));
			final List<VirtualFilesystemItem> items = found.getContent().stream().map(KnowledgeBaseBrowsingToolSource::item)
					.toList();
			return fitted(items, page, pageSize, found.getTotalElements(), budget, tool);
		} catch (RuntimeException e) {
			LOGGER.error(tool + "(...) failed", e);
			return failed(param, tool);
		}
	}

	/**
	 * The whole text of the visible documents with the uniqueIds, in the order asked,
	 * rebuilt from their chunks in the vector store; each document as much of it as
	 * the room left allows, the next ones nothing once the room is used up.
	 */
	List<DocumentContent> documentContents(DocumentContentsParam param, List<String> chatKnowledgeBases, ToolsTokenBudget budget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin documentContents(" + param + ")");
		}
		if (param == null || param.getUniqueIds() == null || param.getUniqueIds().isEmpty()) {
			return List.of(new DocumentContent(null, null, null, null, false, "Give the uniqueIds of the documents to read."));
		}
		final List<DocumentContent> contents = new ArrayList<>();
		try {
			final List<String> scope = scope(chatKnowledgeBases, null);
			final List<Long> asked = param.getUniqueIds().stream().filter(id -> id != null).distinct().toList();
			final Map<Long, GDocumentReference> visibles = new LinkedHashMap<>();
			if (!scope.isEmpty() && !asked.isEmpty()) {
				for (GDocumentReference document : visibilityService.getObject()
						.browseVisibleDocuments(VirtualFilesystemQuery.builder().knowledgeBaseCodes(scope).uniqueIds(asked).build(),
								PageRequest.of(0, asked.size()))
						.getContent()) {
					visibles.put(document.getUniqueId(), document);
				}
			}
			final KnowledgeBaseDocumentChunksReader reader = chunksReader.getObject();
			// the room left to the tools, all of it for these documents
			int room = budget != null ? budget.grant(budget.left()) : Integer.MAX_VALUE;
			for (Long uniqueId : asked) {
				final GDocumentReference document = visibles.get(uniqueId);
				if (document == null) {
					contents.add(new DocumentContent(uniqueId, null, null, null, false,
							"No document with this uniqueId among the documents of this chat's knowledge bases the user can read."));
					continue;
				}
				if (room < ToolsTokenBudget.MIN_USEFUL_TOKENS) {
					contents.add(new DocumentContent(uniqueId, document.getName(), document.getCode(), null, false,
							"Not read: no room is left in the context for it."));
					continue;
				}
				final DocumentContent content = documentContent(reader, document, room);
				room -= content.content() != null ? ITokensCountable.stringsTokensSize(content.content()) : 0;
				contents.add(content);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End documentContents(...) " + contents.size() + " document(s), "
						+ contents.stream().filter(DocumentContent::complete).count() + " complete");
			}
			return contents;
		} catch (RuntimeException e) {
			LOGGER.error("documentContents(...) failed", e);
			return List.of(new DocumentContent(null, null, null, null, false,
					"The documents could not be read: go on without them."));
		}
	}

	/** The text of a document from its chunks in the vector store, at most {@code room} tokens of it. */
	DocumentContent documentContent(KnowledgeBaseDocumentChunksReader reader, GDocumentReference document, int room) {
		final DocumentChunks read;
		try {
			read = reader.read(document);
		} catch (Exception e) {
			LOGGER.error("Cannot read the chunks of document " + document.getCode(), e);
			return new DocumentContent(document.getUniqueId(), document.getName(), document.getCode(), null, false,
					"The text of this document could not be read.");
		}
		if (read.chunks().isEmpty()) {
			return new DocumentContent(document.getUniqueId(), document.getName(), document.getCode(), null, false,
					"No text of this document is in the knowledge base: it is not vectorized (yet).");
		}
		final StringBuilder text = new StringBuilder();
		int chunks = 0;
		int tokens = 0;
		boolean complete = true;
		for (Document chunk : read.chunks()) {
			if (chunk.getText() == null) {
				continue;
			}
			text.append(chunk.getText()).append('\n');
			chunks++;
			tokens += ITokensCountable.stringsTokensSize(chunk.getText());
			if (tokens > room) {
				complete = false;
				break;
			}
		}
		String content = text.toString();
		if (!complete) {
			content = ToolsTokenBudget.fitText(content, room);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("documentContent(...) uniqueId:" + document.getUniqueId() + " code:" + document.getCode() + " "
					+ chunks + " chunk(s) of " + read.chunks().size() + " from the vector store of "
					+ read.embeddingModelCode() + ", " + content.length() + " character(s), complete:" + complete);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<KNOWLEDGE_BASE_DOCUMENT_CONTENT uniqueId=" + document.getUniqueId() + ">");
			LOGGER.trace(content);
			LOGGER.trace("</KNOWLEDGE_BASE_DOCUMENT_CONTENT>");
		}
		return new DocumentContent(document.getUniqueId(), document.getName(), document.getCode(), content, complete,
				complete ? null : "Only the beginning of the document fits the room left in the context.");
	}

	// -------------------------------------------------------------------- helpers

	/** A folder or a document as the tools list it. */
	static VirtualFilesystemItem item(GAbstractVirtualFilesystemObject object) {
		final Map<String, Object> meta = object.getCustomMetaInfos();
		final Object title = meta != null ? meta.get(DocumentMetaInfos.TITLE) : null;
		return new VirtualFilesystemItem(object.getUniqueId(), object.getName(), title != null ? title.toString() : null,
				object.getDescription(), object.getCode(), object.getParentVirtualFolderCode(), meta);
	}

	/** A page of an in-memory list. */
	<T> ListPage<T> page(List<T> all, PageParam param, ToolsTokenBudget budget, String tool) {
		final int page = pageOf(param);
		final int pageSize = pageSizeOf(param);
		final int from = (int) Math.min((long) page * pageSize, all.size());
		final int to = Math.min(from + pageSize, all.size());
		return fitted(all.subList(from, to), page, pageSize, all.size(), budget, tool);
	}

	/** A page fitted in the room left to the tools, whole items only. */
	<T> ListPage<T> fitted(List<T> items, int page, int pageSize, long total, ToolsTokenBudget budget, String tool) {
		List<T> kept = items;
		if (budget != null) {
			final Function<T, String> rendering = JsonParser::toJson;
			kept = ToolsTokenBudget.fitItems(items, budget.grant(budget.left()), rendering);
		}
		final int leftOut = items.size() - kept.size();
		final boolean hasMore = (long) (page + 1) * pageSize < total;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End " + tool + "(...) page:" + page + " size:" + pageSize + " " + kept.size() + " item(s) of "
					+ total + (leftOut > 0 ? ", " + leftOut + " left out for room" : "") + " hasMorePages:" + hasMore);
		}
		return new ListPage<>(kept, page, pageSize, total, hasMore, leftOut > 0 ? leftOut : null,
				leftOut > 0 ? "Some items of this page do not fit the room left in the context." : null);
	}

	<T> ListPage<T> empty(PageParam param, String message) {
		return new ListPage<>(List.of(), pageOf(param), pageSizeOf(param), 0, false, null, message);
	}

	<T> ListPage<T> failed(PageParam param, String tool) {
		return empty(param, "The list could not be read: go on without it.");
	}

	static String noKnowledgeBaseMessage(String requestedKnowledgeBaseCode) {
		return notBlank(requestedKnowledgeBaseCode)
				? "The knowledge base " + requestedKnowledgeBaseCode + " is not among this chat's knowledge bases the user can see."
				: "This chat has no knowledge base.";
	}

	static boolean contains(String part, String... values) {
		if (!notBlank(part)) {
			return true;
		}
		final String lower = part.trim().toLowerCase(Locale.ROOT);
		for (String value : values) {
			if (value != null && value.toLowerCase(Locale.ROOT).contains(lower)) {
				return true;
			}
		}
		return false;
	}

	static boolean notBlank(String value) {
		return value != null && !value.isBlank();
	}
}
