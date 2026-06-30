/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.mcpserver.config.GeboMcpResourcesConfig;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.CentralizedProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.systems.abstraction.layer.model.StreamingPurpose;
import io.modelcontextprotocol.server.McpServerFeatures.SyncResourceSpecification;
import io.modelcontextprotocol.server.McpServerFeatures.SyncResourceTemplateSpecification;
import io.modelcontextprotocol.spec.McpSchema.BlobResourceContents;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.Resource;
import io.modelcontextprotocol.spec.McpSchema.ResourceTemplate;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import lombok.AllArgsConstructor;

/**
 * Builds the MCP resource specifications exposed by an erogated MCP server from
 * the exported knowledge bases, projects and project endpoints of its
 * {@link GeboMCPServerConfig}.
 * <p>
 * The platform content model is hierarchical:
 * {@code GKnowledgeBase → GProject → GProjectEndpoint → GVirtualFolder →
 * (child GVirtualFolder | GDocumentReference)}. The configured knowledge bases,
 * projects and endpoints are exposed as fixed, listable resources (addressed by
 * the {@code gebo-kb://}, {@code gebo-project://} and {@code gebo-endpoint://}
 * schemes). The folder/document subtree below an exported endpoint is, instead,
 * navigated dynamically and per user, so it is exposed through a single
 * {@code gebo-vfolder://{code}} resource <em>template</em> rather than a static
 * list (the visible set depends on the caller and cannot be precomputed at
 * build time, which runs once outside any user context).
 * <p>
 * Hierarchy rules honoured here (limits are configurable via
 * {@link GeboMcpResourcesConfig}):
 * <ul>
 * <li>Reading an endpoint lists its nested virtual folders up to a configurable
 * depth (default 2); documents are not expanded at this level.</li>
 * <li>{@link GDocumentReference}-level entries are returned <b>only</b> when the
 * client reads (asks for) the parent {@link GVirtualFolder}.</li>
 * <li>A document's actual content is served on demand: each listed document carries
 * a {@code gebo-document://{code}} URI whose read streams the file through
 * {@link IDocumentsCacheService} (text inline, binary as a base64 blob, capped at a
 * configurable size).</li>
 * </ul>
 * Every read re-resolves the underlying object and re-checks the caller's access:
 * the configured KB/project/endpoint reads use {@link GeboMcpAccessChecker}, while
 * the folder/document navigation (and the per-document access decision) goes through
 * {@link IGKnowledgebaseVisibilityService}, which only ever returns objects the
 * calling user is entitled to see.
 */
@Service
@AllArgsConstructor
public class GeboMcpResourcesProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMcpResourcesProvider.class);

	private static final String KB_SCHEME = "gebo-kb://";
	private static final String PROJECT_SCHEME = "gebo-project://";
	private static final String ENDPOINT_SCHEME = "gebo-endpoint://";
	private static final String VFOLDER_SCHEME = "gebo-vfolder://";
	private static final String DOCUMENT_SCHEME = "gebo-document://";
	private static final String DOCUMENT_TEXT_SCHEME = "gebo-document-text://";
	private static final String MIME_TEXT = "text/plain";
	private static final String MIME_OCTET_STREAM = "application/octet-stream";

	/** Appended to extracted text when it is truncated at the configured maximum. */
	private static final String TRUNCATION_MARKER = "\n\n…[truncated]";

	private final KnowledgeBaseRepository knowledgeBaseRepository;
	private final ProjectRepository projectRepository;
	private final CentralizedProjectEndpointRepository endpointRepository;
	private final DocumentReferenceRepository documentReferenceRepository;
	private final IGKnowledgebaseVisibilityService visibilityService;
	private final IDocumentsCacheService documentsCacheService;
	private final IGDocumentReferenceIngestionHandler ingestionHandler;
	private final GeboMcpResourcesConfig limitsConfig;
	private final GeboMcpAccessChecker accessChecker;
	private final GeboMcpSecurityContextSupport securitySupport;

	/**
	 * Builds the fixed (listable) resource specifications for the given
	 * configuration: one per exported knowledge base, project and project endpoint.
	 *
	 * @param config the MCP server configuration
	 * @return the resource specifications to register, never {@code null}
	 */
	public List<SyncResourceSpecification> buildResources(GeboMCPServerConfig config) {
		List<SyncResourceSpecification> specs = new ArrayList<>();
		addKnowledgeBases(config, specs);
		addProjects(config, specs);
		addEndpoints(config, specs);
		return specs;
	}

	/**
	 * Builds the dynamic resource templates for the given configuration. A
	 * {@code gebo-vfolder://{code}} template is published only when the server
	 * exports at least one project endpoint, since that is the only way a virtual
	 * folder becomes reachable.
	 *
	 * @param config the MCP server configuration
	 * @return the resource template specifications to register, never {@code null}
	 */
	public List<SyncResourceTemplateSpecification> buildResourceTemplates(GeboMCPServerConfig config) {
		List<SyncResourceTemplateSpecification> templates = new ArrayList<>();
		if (config.getExportedProjectEndpoints() != null && !config.getExportedProjectEndpoints().isEmpty()) {
			templates.add(buildVirtualFolderTemplate());
			templates.add(buildDocumentTemplate());
			templates.add(buildDocumentTextTemplate());
		}
		return templates;
	}

	private void addKnowledgeBases(GeboMCPServerConfig config, List<SyncResourceSpecification> specs) {
		if (config.getExportedKnowledgeBasesAsResources() == null) {
			return;
		}
		for (String code : config.getExportedKnowledgeBasesAsResources()) {
			GKnowledgeBase kb = findById(knowledgeBaseRepository.findById(code));
			if (kb == null) {
				continue;
			}
			specs.add(buildSpec(KB_SCHEME + code, name(kb.getDescription(), code), kb.getDescription(),
					() -> findById(knowledgeBaseRepository.findById(code)),
					obj -> summarizeKnowledgeBase((GKnowledgeBase) obj)));
		}
	}

	private void addProjects(GeboMCPServerConfig config, List<SyncResourceSpecification> specs) {
		if (config.getExportedProjectsAsResources() == null) {
			return;
		}
		for (String code : config.getExportedProjectsAsResources()) {
			GProject project = findById(projectRepository.findById(code));
			if (project == null) {
				continue;
			}
			specs.add(buildSpec(PROJECT_SCHEME + code, name(project.getDescription(), code), project.getDescription(),
					() -> findById(projectRepository.findById(code)),
					obj -> summarizeProject((GProject) obj)));
		}
	}

	private void addEndpoints(GeboMCPServerConfig config, List<SyncResourceSpecification> specs) {
		if (config.getExportedProjectEndpoints() == null) {
			return;
		}
		for (GObjectRef<GProjectEndpoint> ref : config.getExportedProjectEndpoints()) {
			if (ref == null || ref.getCode() == null) {
				continue;
			}
			String code = ref.getCode();
			GCentralizedProjectEndpoint endpoint = findById(endpointRepository.findById(code));
			if (endpoint == null) {
				continue;
			}
			specs.add(buildSpec(ENDPOINT_SCHEME + code, name(endpoint.getDescription(), code),
					endpoint.getDescription(), () -> findById(endpointRepository.findById(code)),
					obj -> summarizeEndpoint((GProjectEndpoint) obj)));
		}
	}

	/**
	 * Builds a single resource specification whose read handler re-resolves the
	 * object, enforces access, and renders the supplied textual summary.
	 */
	private SyncResourceSpecification buildSpec(String uri, String name, String description,
			Supplier<Object> resolver, Function<Object, String> renderer) {
		Resource resource = Resource.builder().uri(uri).name(name).description(description).mimeType(MIME_TEXT).build();
		return new SyncResourceSpecification(resource, (exchange, request) -> securitySupport
				.runAs(exchange.transportContext(), () -> {
					Object object = resolver.get();
					if (object == null) {
						throw new IllegalStateException("Resource no longer exists: " + uri);
					}
					if (!accessChecker.canReadResource(object)) {
						throw new SecurityException("You are not allowed to read resource " + uri);
					}
					String body = renderer.apply(object);
					return new ReadResourceResult(List.of(new TextResourceContents(uri, MIME_TEXT, body)));
				}));
	}

	/**
	 * Builds the {@code gebo-vfolder://{code}} template. Its read handler runs under
	 * the calling user's identity and lists, through the visibility service, the
	 * child virtual folders and the child documents of the requested folder — the
	 * only place {@link GDocumentReference}-level entries are returned.
	 */
	private SyncResourceTemplateSpecification buildVirtualFolderTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(VFOLDER_SCHEME + "{code}")
				.name("Virtual folder")
				.description("Child folders and documents of a virtual folder under an exported project endpoint.")
				.mimeType(MIME_TEXT).build();
		return new SyncResourceTemplateSpecification(template, (exchange, request) -> securitySupport
				.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, VFOLDER_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid virtual folder URI: " + uri);
					}
					return new ReadResourceResult(
							List.of(new TextResourceContents(uri, MIME_TEXT, summarizeVirtualFolder(code))));
				}));
	}

	/**
	 * Builds the {@code gebo-document://{code}} template. Its read handler runs under
	 * the calling user's identity, re-verifies that the requested document is visible
	 * to the caller through its parent folder, then streams its content via the
	 * {@link IDocumentsCacheService} (which resolves the right content-management
	 * handler from the document's endpoint reference and locally caches remote files).
	 */
	private SyncResourceTemplateSpecification buildDocumentTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(DOCUMENT_SCHEME + "{code}").name("Document")
				.description("Content of a document under an exported project endpoint.").build();
		return new SyncResourceTemplateSpecification(template, (exchange, request) -> securitySupport
				.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, DOCUMENT_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid document URI: " + uri);
					}
					GDocumentReference document = resolveVisibleDocument(code);
					if (document == null) {
						throw new SecurityException("You are not allowed to read document " + uri);
					}
					return readDocumentContent(uri, document);
				}));
	}

	/**
	 * Builds the {@code gebo-document-text://{code}} template, serving the
	 * <em>extracted plain text</em> of a document rather than its raw bytes. The
	 * content is streamed through {@link IDocumentsCacheService} and parsed by
	 * {@link IGDocumentReferenceIngestionHandler#handleContent(GDocumentReference, TypedInputStream)};
	 * the texts of the returned {@link Document} stream are concatenated to produce
	 * the plain-text rendition (the same extraction used by the ingestion pipeline).
	 */
	private SyncResourceTemplateSpecification buildDocumentTextTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(DOCUMENT_TEXT_SCHEME + "{code}")
				.name("Document (extracted text)")
				.description("Plain-text rendition of a document under an exported project endpoint.")
				.mimeType(MIME_TEXT).build();
		return new SyncResourceTemplateSpecification(template, (exchange, request) -> securitySupport
				.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, DOCUMENT_TEXT_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid document text URI: " + uri);
					}
					GDocumentReference document = resolveVisibleDocument(code);
					if (document == null) {
						throw new SecurityException("You are not allowed to read document " + uri);
					}
					return readDocumentText(uri, document);
				}));
	}

	private static String codeFromUri(String uri, String scheme) {
		if (uri == null || !uri.startsWith(scheme)) {
			return null;
		}
		String code = uri.substring(scheme.length());
		return code.isBlank() ? null : code;
	}

	private static <T> T findById(Optional<T> optional) {
		return optional.orElse(null);
	}

	private static String name(String description, String code) {
		return description != null && !description.isBlank() ? description : code;
	}

	private static String summarizeKnowledgeBase(GKnowledgeBase kb) {
		StringBuilder sb = new StringBuilder();
		sb.append("Knowledge base: ").append(kb.getCode()).append('\n');
		sb.append("Description: ").append(safe(kb.getDescription())).append('\n');
		sb.append("Object space type: ").append(kb.getObjectSpaceType()).append('\n');
		sb.append("Parent knowledge base: ").append(safe(kb.getParentKnowledgebaseCode()));
		return sb.toString();
	}

	private static String summarizeProject(GProject project) {
		StringBuilder sb = new StringBuilder();
		sb.append("Project: ").append(project.getCode()).append('\n');
		sb.append("Description: ").append(safe(project.getDescription())).append('\n');
		sb.append("Root knowledge base: ").append(safe(project.getRootKnowledgeBaseCode())).append('\n');
		sb.append("Parent project: ").append(safe(project.getParentProjectCode()));
		return sb.toString();
	}

	/**
	 * Renders an endpoint summary followed by its nested virtual folders up to the
	 * 2nd level. Documents are intentionally not expanded here — they are only
	 * returned when the parent virtual folder is read via {@code gebo-vfolder://}.
	 */
	private String summarizeEndpoint(GProjectEndpoint endpoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("Project endpoint: ").append(endpoint.getCode()).append('\n');
		sb.append("Description: ").append(safe(endpoint.getDescription())).append('\n');
		sb.append("Parent project: ").append(safe(endpoint.getParentProjectCode())).append('\n');
		sb.append("Published: ").append(endpoint.getPublished()).append('\n');
		sb.append("Folders (up to ").append(limitsConfig.getEndpointMaxFolderDepth()).append(" levels):");
		List<GVirtualFolder> roots = visibilityService.getVisibleProjectEndpointRootsByParentEndpoint(endpoint.getCode(),
				endpoint.getClass().getName());
		appendFolderTree(roots, sb, 1);
		return sb.toString();
	}

	/**
	 * Appends the given virtual folders and recurses into their child folders until
	 * the configured {@link GeboMcpResourcesConfig#getEndpointMaxFolderDepth() depth}
	 * is reached. Only folders are listed.
	 */
	private void appendFolderTree(List<? extends GAbstractVirtualFilesystemObject> folders, StringBuilder sb,
			int level) {
		boolean any = false;
		if (folders != null) {
			for (GAbstractVirtualFilesystemObject folder : folders) {
				if (isDeleted(folder)) {
					continue;
				}
				any = true;
				sb.append('\n').append(indent(level)).append("- ").append(displayName(folder)).append("  ")
						.append(VFOLDER_SCHEME).append(folder.getCode());
				if (level < limitsConfig.getEndpointMaxFolderDepth()) {
					appendFolderTree(visibilityService.getVisibleChildVirtualFolders(folder.getCode()), sb, level + 1);
				}
			}
		}
		if (!any && level == 1) {
			sb.append(" (none)");
		}
	}

	/**
	 * Lists the child folders (navigable via {@code gebo-vfolder://}) and the child
	 * documents of the requested folder. Both sets are returned by the visibility
	 * service already filtered to what the calling user may see.
	 */
	private String summarizeVirtualFolder(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("Virtual folder: ").append(code).append('\n');
		sb.append("Child folders:");
		boolean anyFolder = false;
		List<? extends GAbstractVirtualFilesystemObject> folders = visibilityService.getVisibleChildVirtualFolders(code);
		if (folders != null) {
			for (GAbstractVirtualFilesystemObject folder : folders) {
				if (isDeleted(folder)) {
					continue;
				}
				anyFolder = true;
				sb.append('\n').append("- ").append(displayName(folder)).append("  ").append(VFOLDER_SCHEME)
						.append(folder.getCode());
			}
		}
		if (!anyFolder) {
			sb.append(" (none)");
		}
		sb.append('\n').append("Documents:");
		boolean anyDoc = false;
		List<? extends GAbstractVirtualFilesystemObject> documents = visibilityService.getVisibleChildDocuments(code);
		if (documents != null) {
			for (GAbstractVirtualFilesystemObject document : documents) {
				if (isDeleted(document)) {
					continue;
				}
				anyDoc = true;
				sb.append('\n').append("- ").append(displayName(document)).append("  ").append(DOCUMENT_SCHEME)
						.append(document.getCode()).append("  (text: ").append(DOCUMENT_TEXT_SCHEME)
						.append(document.getCode()).append(')');
			}
		}
		if (!anyDoc) {
			sb.append(" (none)");
		}
		return sb.toString();
	}

	/**
	 * Resolves a document by code and confirms the calling user may see it, by
	 * checking it is among the visible children of its parent virtual folder. Returns
	 * {@code null} when the document does not exist, has no parent folder (so it is
	 * not reachable through an exported endpoint), or is not visible to the caller.
	 */
	private GDocumentReference resolveVisibleDocument(String code) {
		GDocumentReference document = findById(documentReferenceRepository.findById(code));
		if (document == null || isDeleted(document)) {
			return null;
		}
		String parentFolderCode = document.getParentVirtualFolderCode();
		if (parentFolderCode == null) {
			return null;
		}
		List<GDocumentReference> visible = visibilityService.getVisibleChildDocuments(parentFolderCode);
		if (visible != null) {
			for (GDocumentReference candidate : visible) {
				if (candidate != null && code.equals(candidate.getCode())) {
					return document;
				}
			}
		}
		return null;
	}

	/**
	 * Streams the document content through the documents cache service and wraps it as
	 * MCP resource content: textual MIME types are returned inline as
	 * {@link TextResourceContents}, everything else as a base64 {@link BlobResourceContents}.
	 * The payload is capped at the configured
	 * {@link GeboMcpResourcesConfig#getMaxDocumentBytes() maximum} to protect the server.
	 */
	private ReadResourceResult readDocumentContent(String uri, GDocumentReference document) {
		InputStream input = null;
		try {
			TypedInputStream typed = documentsCacheService.streamDocument(StreamingPurpose.SERVING, document);
			if (typed == null || typed.getInputStream() == null) {
				throw new IllegalStateException("No content available for document " + uri);
			}
			input = typed.getInputStream();
			int maxBytes = limitsConfig.getMaxDocumentBytes();
			byte[] bytes = input.readNBytes(maxBytes + 1);
			if (bytes.length > maxBytes) {
				throw new IllegalStateException(
						"Document " + uri + " exceeds the maximum servable size of " + maxBytes + " bytes");
			}
			String mime = firstNonBlank(typed.getContentType(), document.getContentType(), MIME_OCTET_STREAM);
			if (isTextual(mime)) {
				return new ReadResourceResult(
						List.of(new TextResourceContents(uri, mime, new String(bytes, StandardCharsets.UTF_8))));
			}
			return new ReadResourceResult(
					List.of(new BlobResourceContents(uri, mime, Base64.getEncoder().encodeToString(bytes))));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot read document " + uri + ": " + e.getMessage(), e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ignored) {
					LOGGER.debug("Error closing document stream for {}", uri, ignored);
				}
			}
		}
	}

	/**
	 * Streams the document, runs it through the ingestion handler, and concatenates
	 * the texts of the produced {@link Document} stream into a single plain-text
	 * payload (truncated at the configured
	 * {@link GeboMcpResourcesConfig#getMaxDocumentTextChars() maximum}). Returned as
	 * {@code text/plain}.
	 */
	private ReadResourceResult readDocumentText(String uri, GDocumentReference document) {
		InputStream input = null;
		try {
			TypedInputStream typed = documentsCacheService.streamDocument(StreamingPurpose.SERVING, document);
			if (typed == null || typed.getInputStream() == null) {
				throw new IllegalStateException("No content available for document " + uri);
			}
			input = typed.getInputStream();
			IngestionHandlerData data = ingestionHandler.handleContent(document, typed);
			if (data == null || data.getStream() == null) {
				throw new IllegalStateException("No extractable text for document " + uri);
			}
			StringBuilder text = new StringBuilder();
			try (Stream<Document> documents = data.getStream()) {
				documents.forEach(doc -> appendDocumentText(text, doc));
			}
			return new ReadResourceResult(List.of(new TextResourceContents(uri, MIME_TEXT, text.toString())));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot extract text from document " + uri + ": " + e.getMessage(), e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ignored) {
					LOGGER.debug("Error closing document stream for {}", uri, ignored);
				}
			}
		}
	}

	/**
	 * Appends a single ingested {@link Document}'s text to the accumulator, separating
	 * fragments with a blank line. When the configured
	 * {@link GeboMcpResourcesConfig#getMaxDocumentTextChars() cap} is reached the text
	 * is truncated (with a {@value #TRUNCATION_MARKER} marker) and any further fragments
	 * are skipped, so the caller still receives a usable prefix of a large document
	 * instead of an error.
	 */
	private void appendDocumentText(StringBuilder accumulator, Document doc) {
		int maxChars = limitsConfig.getMaxDocumentTextChars();
		if (accumulator.length() >= maxChars) {
			// Cap already reached on a previous fragment: stop accumulating.
			return;
		}
		if (doc == null || !doc.isText() || doc.getText() == null || doc.getText().isBlank()) {
			return;
		}
		String separator = accumulator.length() > 0 ? "\n\n" : "";
		String fragment = doc.getText();
		int remaining = maxChars - accumulator.length();
		if (separator.length() + fragment.length() <= remaining) {
			accumulator.append(separator).append(fragment);
			return;
		}
		// Does not fit: append as much of this fragment as room allows, then a marker,
		// which pushes the length past the cap so subsequent fragments are skipped.
		int room = remaining - separator.length();
		if (room > 0) {
			accumulator.append(separator).append(fragment, 0, room);
		}
		accumulator.append(TRUNCATION_MARKER);
	}

	/**
	 * Whether the given MIME type can be returned inline as UTF-8 text. Anything not
	 * recognised as textual is served as a binary blob.
	 */
	private static boolean isTextual(String mime) {
		if (mime == null) {
			return false;
		}
		String m = mime.toLowerCase();
		return m.startsWith("text/") || m.contains("json") || m.contains("xml") || m.contains("yaml")
				|| m.contains("csv") || m.contains("javascript") || m.contains("ecmascript")
				|| m.equals("application/x-sh");
	}

	private static String firstNonBlank(String... values) {
		for (String value : values) {
			if (value != null && !value.isBlank()) {
				return value;
			}
		}
		return MIME_OCTET_STREAM;
	}

	private static boolean isDeleted(GAbstractVirtualFilesystemObject object) {
		return object.getDeleted() != null && object.getDeleted();
	}

	private static String displayName(GAbstractVirtualFilesystemObject object) {
		return object.getName() != null && !object.getName().isBlank() ? object.getName() : object.getCode();
	}

	private static String indent(int level) {
		return "    ".repeat(Math.max(0, level - 1));
	}

	private static String safe(String value) {
		return value != null ? value : "";
	}
}
