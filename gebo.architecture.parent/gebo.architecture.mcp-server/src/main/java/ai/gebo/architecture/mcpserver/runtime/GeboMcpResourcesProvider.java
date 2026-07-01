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
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.mcpserver.config.GeboMcpResourcesConfig;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
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
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.IdentityUtil;
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
 * Builds the MCP resources an erogated MCP server exposes for the knowledge
 * bases, projects and project endpoints of its {@link GeboMCPServerConfig}.
 * <p>
 * The platform content model is hierarchical:
 * {@code GKnowledgeBase → GProject → GProjectEndpoint → GVirtualFolder →
 * (child GVirtualFolder | GDocumentReference)}.
 * <p>
 * <b>Listing is static, reading is impersonated.</b> The MCP SDK serves
 * {@code resources/list} from a map built once at server construction, outside
 * any request/user context, so it cannot be personalised per connected user.
 * The list holds the statically declared exports (configured knowledge bases,
 * projects and endpoints) plus a single {@code gebo-root://catalog} entry.
 * Since the listing is shared, it is produced by impersonating the
 * configuration's creator ({@code userCreated}) and keeping only the exports
 * that owner may READ; if the owner cannot be resolved
 * (absent/unknown/disabled) it falls back to the raw configuration list. All
 * per-user behaviour happens on <em>read</em>, under the connected user's
 * identity ({@link GeboMcpSecurityContextSupport#runAs}):
 * <ul>
 * <li>reading {@code gebo-root://catalog} returns the caller's whole accessible
 * hierarchy, rendered recursively and depth-limited — every personally visible
 * root knowledge base when {@code shareAllPersonallyVisible} is set, otherwise
 * the configured exports the caller may READ;</li>
 * <li>reading any single resource — a configured export, or any node reached by
 * descending into one — renders that node (recursively for containers: a
 * knowledge base, project or endpoint expands its subtree),
 * access-checked.</li>
 * </ul>
 * Nodes reached by descent that are not in the static list are addressed by
 * resource <em>templates</em> ({@code gebo-kb://}, {@code gebo-project://},
 * {@code gebo-endpoint://}, {@code gebo-vfolder://}, {@code gebo-document://},
 * {@code gebo-document-text://}); the SDK resolves a read against the static
 * map first and falls back to templates, so a configured export and its
 * template share one code path. Node ids are Base64URL-encoded in the URI
 * because the SDK's template matcher forbids {@code '/'} in a variable and
 * codes routinely contain it (an endpoint id additionally encodes its concrete
 * class, since an endpoint's identity is the {@code (class, code)} pair).
 * <p>
 * Hierarchy rules honoured here (limits are configurable via
 * {@link GeboMcpResourcesConfig}):
 * <ul>
 * <li>Descending a knowledge base, project or endpoint lists the nested virtual
 * folders up to a configurable depth (default 2); documents are not expanded at
 * these levels.</li>
 * <li>{@link GDocumentReference}-level entries are returned <b>only</b> when
 * the client reads (asks for) the parent {@link GVirtualFolder}.</li>
 * <li>A document's content is served on demand: each listed document carries a
 * {@code gebo-document://} URI (raw bytes: text inline, binary as a base64
 * blob, size capped) and a {@code gebo-document-text://} URI (plain text
 * extracted via the ingestion handler, length capped), both streamed through
 * {@link IDocumentsCacheService}.</li>
 * </ul>
 * Every read re-resolves the underlying object and re-checks the caller's
 * access ({@link IGSecurityService#isCanDo}/{@code isCanDoAction} with
 * {@code READ}), while folder/document navigation goes through
 * {@link IGKnowledgebaseVisibilityService}, which only ever returns objects the
 * calling user is entitled to see.
 */
@Service
@AllArgsConstructor
public class GeboMcpResourcesProvider {

	private static final String DOCUMENT_PLAIN_TEXT_DESCRIPTION = "Plain-text rendition of a document under an exported  virtual drive.";

	private static final String DOCUMENT_DESCRIPTION = "Content of a document under an exported virtual drive.";

	private static final String VIRTUAL_FOLDER_DESCRIPTION = "Child folders and documents of a virtual folder under an exported  virtual drive.";

	private static final String PROJECT_ENDPOINT_DESCRIPTION = "A virtual drive and its nested virtual folders.";

	private static final String PROJECT_ENDPOINT_NAME = "Virtual drive";

	private static final String PROJECT_MCP_DESCRIPTION = "A project and its virtual drives, sub-projects and nested folders.";

	private static final String KNOWLEDGE_BASE_MCP_DESCRIPTION = "A knowledge base and its projects, virtual drives and nested folders.";

	private static final char NEWLINE = '\n';

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMcpResourcesProvider.class);

	private static final String KB_SCHEME = "gebo-kb://";
	private static final String PROJECT_SCHEME = "gebo-project://";
	private static final String ENDPOINT_SCHEME = "gebo-endpoint://";
	private static final String VFOLDER_SCHEME = "gebo-vfolder://";
	private static final String DOCUMENT_SCHEME = "gebo-document://";
	private static final String DOCUMENT_TEXT_SCHEME = "gebo-document-text://";
	private static final String MIME_TEXT = "text/plain";
	private static final String MIME_OCTET_STREAM = "application/octet-stream";

	/**
	 * Safety cap on how deep the project/sub-project structural recursion may go.
	 */
	private static final int MAX_STRUCTURAL_DEPTH = 32;

	/**
	 * Appended to extracted text when it is truncated at the configured maximum.
	 */
	private static final String TRUNCATION_MARKER = "\n\n…[truncated]";

	private final IGPersistentObjectManager persistenceManager;
	private final IGSecurityService securityService;
	private final IGKnowledgebaseVisibilityService visibilityService;
	private final IDocumentsCacheService documentsCacheService;
	private final IGDocumentReferenceIngestionHandler ingestionHandler;
	private final GeboMcpResourcesConfig limitsConfig;
	private final GeboMcpSecurityContextSupport securitySupport;

	/**
	 * Builds the single fixed (listable) resource: the {@code gebo-root://catalog}
	 * entry point. The MCP SDK serves {@code resources/list} from a static map
	 * built once outside any user context, so the catalog itself cannot be
	 * personalised there; instead its <em>read</em> runs under the calling user's
	 * identity and returns only the knowledge bases / projects / endpoints that
	 * user may access. All deeper navigation happens through the (equally per-user)
	 * resource templates.
	 *
	 * @param config the MCP server configuration
	 * @return the singleton root resource specification
	 */
	public List<SyncResourceSpecification> buildResources(GeboMCPServerConfig config) throws GeboPersistenceException {
		List<SyncResourceSpecification> specs = new ArrayList<>();
		String owner = config.getUserCreated();
		if (owner != null && !owner.isBlank()) {

			IdentityUtil identity = IdentityUtil.create(owner, List.of("USER"));
			identity.doAsWithException(() -> {
				// The catalog entry point: a per-user, impersonated, recursive view.
				specs.add(buildRootCatalog(config));
				// The statically declared exports of the configuration. Reads are always
				// impersonated (and access-checked) per connected user, but the shared static
				// listing itself is produced by impersonating the configuration's creator
				// (userCreated) and keeping only the exports that owner may READ.
				specs.addAll(resolveConfiguredExports(config));
			});
		} else
			throw new IllegalStateException("No owner present");
		return specs;
	}

	/**
	 * Produces the static export specs. When the configuration has a resolvable
	 * creator ({@code userCreated}), the listing is filtered under that owner's
	 * identity so it mirrors what the owner can read; if the owner is absent,
	 * unknown or disabled — so {@code getCurrentUser()} throws — it falls back to
	 * the raw, unfiltered configuration list.
	 */
	private List<SyncResourceSpecification> resolveConfiguredExports(GeboMCPServerConfig config)
			throws GeboPersistenceException {
		String owner = config.getUserCreated();
		if (owner != null && !owner.isBlank()) {
			try {
				return IdentityUtil.create(owner, null)
						.doRunAsWithReturnAndException(() -> collectConfiguredExports(config, true));
			} catch (GeboPersistenceException e) {
				throw e;
			} catch (RuntimeException e) {
				LOGGER.warn(
						"Cannot produce owner-scoped MCP resource listing for config '{}' (creator '{}'): {}. Falling back to the unfiltered configuration list.",
						config.getCode(), owner, e.getMessage());
			}
		}
		return collectConfiguredExports(config, false);
	}

	/**
	 * Collects the static export specs from the configuration. When
	 * {@code filtered} is {@code true} each export is kept only if the
	 * (impersonated) current user may READ it; when {@code false} every existing
	 * export is listed.
	 */
	private List<SyncResourceSpecification> collectConfiguredExports(GeboMCPServerConfig config, boolean filtered)
			throws GeboPersistenceException {
		List<SyncResourceSpecification> specs = new ArrayList<>();
		if (config.getExportedKnowledgeBasesAsResources() != null) {
			for (String code : config.getExportedKnowledgeBasesAsResources()) {
				GKnowledgeBase kb = findById(code, GKnowledgeBase.class);
				if (kb == null || (filtered && !securityService.isCanDo(kb, true, AclGrantType.READ))) {
					continue;
				}
				specs.add(knowledgeBaseSpec(code, kb));
			}
		}
		if (config.getExportedProjectsAsResources() != null) {
			for (String code : config.getExportedProjectsAsResources()) {
				GProject project = findById(code, GProject.class);
				if (project == null || (filtered && !securityService.isCanDo(project, true, AclGrantType.READ))) {
					continue;
				}
				specs.add(projectSpec(code, project));
			}
		}
		if (config.getExportedProjectEndpoints() != null) {
			for (GObjectRef<GProjectEndpoint> ref : config.getExportedProjectEndpoints()) {
				if (ref == null || ref.getCode() == null) {
					continue;
				}
				GProjectEndpoint endpoint = persistenceManager.findByReference(ref, GProjectEndpoint.class);
				if (endpoint == null
						|| (filtered && !securityService.isCanDoAction(endpoint, true, AclGrantType.READ))) {
					continue;
				}
				specs.add(endpointSpec(ref, endpoint));
			}
		}
		return specs;
	}

	private SyncResourceSpecification knowledgeBaseSpec(String code, GKnowledgeBase kb) {
		String uri = kbUri(code);
		return new SyncResourceSpecification(
				Resource.builder().uri(uri).name(name(kb.getDescription(), code)).description(kb.getDescription())
						.mimeType(MIME_TEXT).build(),
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(),
						() -> readKnowledgeBase(uri, code)));
	}

	private SyncResourceSpecification projectSpec(String code, GProject project) {
		String uri = projectUri(code);
		return new SyncResourceSpecification(
				Resource.builder().uri(uri).name(name(project.getDescription(), code))
						.description(project.getDescription()).mimeType(MIME_TEXT).build(),
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(),
						() -> readProject(uri, code)));
	}

	private SyncResourceSpecification endpointSpec(GObjectRef<GProjectEndpoint> ref, GProjectEndpoint endpoint) {
		String uri = endpointUri(endpoint);
		return new SyncResourceSpecification(
				Resource.builder().uri(uri).name(name(endpoint.getDescription(), endpoint.getCode()))
						.description(endpoint.getDescription()).mimeType(MIME_TEXT).build(),
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(),
						() -> readEndpoint(uri, ref)));
	}

	// ---- shared per-node reads (impersonated single-resource access) -----------

	private ReadResourceResult readKnowledgeBase(String uri, String code) {
		try {
			GKnowledgeBase kb = findById(code, GKnowledgeBase.class);
			if (kb == null) {
				throw new IllegalStateException("Resource no longer exists: " + uri);
			}
			if (!securityService.isCanDo(kb, true, AclGrantType.READ)) {
				throw new SecurityException("You are not allowed to read " + uri);
			}
			return text(uri, summarizeKnowledgeBase(kb));
		} catch (GeboPersistenceException e) {
			throw new IllegalStateException("Problems accessing mongodb", e);
		}
	}

	private ReadResourceResult readProject(String uri, String code) {
		try {
			GProject project = findById(code, GProject.class);
			if (project == null) {
				throw new IllegalStateException("Resource no longer exists: " + uri);
			}
			if (!securityService.isCanDo(project, true, AclGrantType.READ)) {
				throw new SecurityException("You are not allowed to read " + uri);
			}
			return text(uri, summarizeProject(project));
		} catch (GeboPersistenceException e) {
			throw new IllegalStateException("Problems accessing mongodb", e);
		}
	}

	private ReadResourceResult readEndpoint(String uri, GObjectRef<GProjectEndpoint> ref) {
		try {
			GProjectEndpoint endpoint = persistenceManager.findByReference(ref, GProjectEndpoint.class);
			if (endpoint == null) {
				throw new IllegalStateException("Resource no longer exists: " + uri);
			}
			if (!securityService.isCanDoAction(endpoint, true, AclGrantType.READ)) {
				throw new SecurityException("You are not allowed to read " + uri);
			}
			return text(uri, summarizeEndpoint(endpoint));
		} catch (GeboPersistenceException e) {
			throw new IllegalStateException("Problems accessing mongodb", e);
		}
	}

	private static ReadResourceResult text(String uri, String body) {
		return new ReadResourceResult(List.of(new TextResourceContents(uri, MIME_TEXT, body)));
	}

	/**
	 * Builds the dynamic resource templates for the given configuration. The
	 * {@code gebo-vfolder://} and {@code gebo-document://} templates are published
	 * whenever the server exports any content that can lead down to virtual folders
	 * — a knowledge base, a project or a project endpoint — since reading any of
	 * them now descends into the folder/document subtree.
	 *
	 * @param config the MCP server configuration
	 * @return the resource template specifications to register, never {@code null}
	 */
	public List<SyncResourceTemplateSpecification> buildResourceTemplates(GeboMCPServerConfig config) {
		List<SyncResourceTemplateSpecification> templates = new ArrayList<>();
		if (exposesAnyContent(config)) {
			templates.add(buildKnowledgeBaseTemplate());
			templates.add(buildProjectTemplate());
			templates.add(buildEndpointTemplate());
			templates.add(buildVirtualFolderTemplate());
			templates.add(buildDocumentTemplate());
			templates.add(buildDocumentTextTemplate());
		}
		return templates;
	}

	/**
	 * Whether the server exposes anything at all — either every personally visible
	 * knowledge base ({@code shareAllPersonallyVisible}) or an explicit export
	 * list. When it does, the whole navigable template set (knowledge base →
	 * project → endpoint → virtual folder → document) is published.
	 */
	private static boolean exposesAnyContent(GeboMCPServerConfig config) {
		return Boolean.TRUE.equals(config.getShareAllPersonallyVisible()) || exposesFolderedContent(config);
	}

	/**
	 * Whether the server exports any explicit content that can be navigated down to
	 * virtual folders.
	 */
	private static boolean exposesFolderedContent(GeboMCPServerConfig config) {
		return notEmpty(config.getExportedKnowledgeBasesAsResources())
				|| notEmpty(config.getExportedProjectsAsResources()) || notEmpty(config.getExportedProjectEndpoints());
	}

	private static boolean notEmpty(List<?> list) {
		return list != null && !list.isEmpty();
	}

	/** URI of the single listable root resource. */
	private static final String ROOT_URI = "gebo-root://catalog";

	/**
	 * Builds the {@code gebo-root://catalog} resource. Its read runs under the
	 * calling user's identity and lists only the entries that user may access
	 * (either every personally visible root knowledge base, or the configured
	 * knowledge bases/projects/endpoints filtered by READ), each carrying its
	 * navigable URI.
	 */
	private SyncResourceSpecification buildRootCatalog(GeboMCPServerConfig config) {
		Resource resource = Resource.builder().uri(ROOT_URI).name("Gebo catalog")
				.description("The knowledge bases, projects and endpoints you can access, and how to navigate them.")
				.mimeType(MIME_TEXT).build();
		return new SyncResourceSpecification(resource,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
					try {
						return new ReadResourceResult(
								List.of(new TextResourceContents(ROOT_URI, MIME_TEXT, renderCatalog(config))));
					} catch (GeboPersistenceException e) {
						throw new IllegalStateException("Problems accessing mongodb", e);
					}
				}));
	}

	/**
	 * Renders the per-user catalog: the accessible content, recursively (with the
	 * configured depth limitations). Must be called under the caller's security
	 * context (i.e. inside {@code runAs}), so the visibility service and ACL checks
	 * resolve against the connected MCP user. Under
	 * {@code shareAllPersonallyVisible} it descends every personally visible root
	 * knowledge base; otherwise it descends the configured knowledge bases /
	 * projects / endpoints the caller may READ.
	 */
	private String renderCatalog(GeboMCPServerConfig config) throws GeboPersistenceException {
		StringBuilder sb = new StringBuilder();
		boolean[] any = { false };
		if (Boolean.TRUE.equals(config.getShareAllPersonallyVisible())) {
			List<GKnowledgeBase> kbs = visibilityService.allVisibleRootKnowledgebases();
			if (kbs != null) {
				for (GKnowledgeBase kb : kbs) {
					appendCatalogBlock(sb, summarizeKnowledgeBase(kb), any);
				}
			}
		} else {
			if (config.getExportedKnowledgeBasesAsResources() != null) {
				for (String code : config.getExportedKnowledgeBasesAsResources()) {
					GKnowledgeBase kb = findById(code, GKnowledgeBase.class);
					if (kb != null && securityService.isCanDo(kb, true, AclGrantType.READ)) {
						appendCatalogBlock(sb, summarizeKnowledgeBase(kb), any);
					}
				}
			}
			if (config.getExportedProjectsAsResources() != null) {
				for (String code : config.getExportedProjectsAsResources()) {
					GProject project = findById(code, GProject.class);
					if (project != null && securityService.isCanDo(project, true, AclGrantType.READ)) {
						appendCatalogBlock(sb, summarizeProject(project), any);
					}
				}
			}
			if (config.getExportedProjectEndpoints() != null) {
				for (GObjectRef<GProjectEndpoint> ref : config.getExportedProjectEndpoints()) {
					if (ref == null || ref.getCode() == null) {
						continue;
					}
					GProjectEndpoint endpoint = persistenceManager.findByReference(ref, GProjectEndpoint.class);
					if (endpoint != null && securityService.isCanDoAction(endpoint, true, AclGrantType.READ)) {
						appendCatalogBlock(sb, summarizeEndpoint(endpoint), any);
					}
				}
			}
		}
		return any[0] ? sb.toString() : "No accessible content.";
	}

	private static void appendCatalogBlock(StringBuilder sb, String block, boolean[] any) {
		if (any[0]) {
			sb.append(NEWLINE).append(NEWLINE);
		}
		sb.append(block);
		any[0] = true;
	}

	/**
	 * Builds the {@code gebo-kb://{id}} template: reads a knowledge base (its
	 * projects, endpoints and nested folders) under the caller's identity,
	 * re-checking READ.
	 */
	private SyncResourceTemplateSpecification buildKnowledgeBaseTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(KB_SCHEME + "{id}").name("Knowledge base")
				.description(KNOWLEDGE_BASE_MCP_DESCRIPTION).mimeType(MIME_TEXT)
				.build();
		return new SyncResourceTemplateSpecification(template,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, KB_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid knowledge base URI: " + uri);
					}
					return readKnowledgeBase(uri, code);
				}));
	}

	/**
	 * Builds the {@code gebo-project://{id}} template: reads a project (its
	 * endpoints, sub-projects and nested folders) under the caller's identity,
	 * re-checking READ.
	 */
	private SyncResourceTemplateSpecification buildProjectTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(PROJECT_SCHEME + "{id}").name("Project")
				.description(PROJECT_MCP_DESCRIPTION).mimeType(MIME_TEXT)
				.build();
		return new SyncResourceTemplateSpecification(template,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, PROJECT_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid project URI: " + uri);
					}
					return readProject(uri, code);
				}));
	}

	/**
	 * Builds the {@code gebo-endpoint://{id}} template: reads an endpoint (its
	 * nested folders) under the caller's identity, re-checking READ. The {@code id}
	 * encodes the endpoint's concrete class name and code, since an endpoint's
	 * identity is the pair (class, code).
	 */
	private SyncResourceTemplateSpecification buildEndpointTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(ENDPOINT_SCHEME + "{id}")
				.name(PROJECT_ENDPOINT_NAME).description(PROJECT_ENDPOINT_DESCRIPTION)
				.mimeType(MIME_TEXT).build();
		return new SyncResourceTemplateSpecification(template,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					GObjectRef<GProjectEndpoint> ref = endpointRefFromToken(tokenFromUri(uri, ENDPOINT_SCHEME));
					if (ref == null) {
						throw new IllegalArgumentException("Invalid endpoint URI: " + uri);
					}
					return readEndpoint(uri, ref);
				}));
	}

	/**
	 * Rebuilds an endpoint reference (class + code) from a {@code gebo-endpoint://}
	 * id token.
	 */
	private static GObjectRef<GProjectEndpoint> endpointRefFromToken(String token) {
		String decoded = decodeId(token);
		if (decoded == null) {
			return null;
		}
		int sep = decoded.indexOf(' ');
		if (sep < 0) {
			return null;
		}
		GObjectRef<GProjectEndpoint> ref = new GObjectRef<>();
		ref.setClassName(decoded.substring(0, sep));
		ref.setCode(decoded.substring(sep + 1));
		return ref;
	}

	/**
	 * Builds the {@code gebo-vfolder://{code}} template. Its read handler runs
	 * under the calling user's identity and lists, through the visibility service,
	 * the child virtual folders and the child documents of the requested folder —
	 * the only place {@link GDocumentReference}-level entries are returned.
	 */
	private SyncResourceTemplateSpecification buildVirtualFolderTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(VFOLDER_SCHEME + "{code}")
				.name("Virtual folder")
				.description(VIRTUAL_FOLDER_DESCRIPTION)
				.mimeType(MIME_TEXT).build();
		return new SyncResourceTemplateSpecification(template,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
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
	 * Builds the {@code gebo-document://{code}} template. Its read handler runs
	 * under the calling user's identity, re-verifies that the requested document is
	 * visible to the caller through its parent folder, then streams its content via
	 * the {@link IDocumentsCacheService} (which resolves the right
	 * content-management handler from the document's endpoint reference and locally
	 * caches remote files).
	 */
	private SyncResourceTemplateSpecification buildDocumentTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(DOCUMENT_SCHEME + "{code}").name("Document")
				.description(DOCUMENT_DESCRIPTION).build();
		return new SyncResourceTemplateSpecification(template,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, DOCUMENT_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid document URI: " + uri);
					}
					GDocumentReference document;
					try {
						document = resolveVisibleDocument(code);
						if (document == null) {
							throw new SecurityException("You are not allowed to read document " + uri);
						}
						return readDocumentContent(uri, document);
					} catch (GeboPersistenceException e) {
						throw new IllegalStateException("Problems accessing mongodb", e);
					}

				}));
	}

	/**
	 * Builds the {@code gebo-document-text://{code}} template, serving the
	 * <em>extracted plain text</em> of a document rather than its raw bytes. The
	 * content is streamed through {@link IDocumentsCacheService} and parsed by
	 * {@link IGDocumentReferenceIngestionHandler#handleContent(GDocumentReference, TypedInputStream)};
	 * the texts of the returned {@link Document} stream are concatenated to produce
	 * the plain-text rendition (the same extraction used by the ingestion
	 * pipeline).
	 */
	private SyncResourceTemplateSpecification buildDocumentTextTemplate() {
		ResourceTemplate template = ResourceTemplate.builder().uriTemplate(DOCUMENT_TEXT_SCHEME + "{code}")
				.name("Document (extracted text)")
				.description(DOCUMENT_PLAIN_TEXT_DESCRIPTION)
				.mimeType(MIME_TEXT).build();
		return new SyncResourceTemplateSpecification(template,
				(exchange, request) -> securitySupport.runAs(exchange.transportContext(), () -> {
					String uri = request.uri();
					String code = codeFromUri(uri, DOCUMENT_TEXT_SCHEME);
					if (code == null) {
						throw new IllegalArgumentException("Invalid document text URI: " + uri);
					}
					GDocumentReference document;
					try {
						document = resolveVisibleDocument(code);
						if (document == null) {
							throw new SecurityException("You are not allowed to read document " + uri);
						}
						return readDocumentText(uri, document);
					} catch (GeboPersistenceException e) {
						throw new IllegalStateException("Problems accessing mongodb", e);
					}

				}));
	}

	/**
	 * Extracts the raw (still Base64URL-encoded) id token that follows the scheme
	 * in a templated URI, or {@code null} if the URI doesn't carry one.
	 */
	private static String tokenFromUri(String uri, String scheme) {
		if (uri == null || !uri.startsWith(scheme)) {
			return null;
		}
		String token = uri.substring(scheme.length());
		return token.isBlank() ? null : token;
	}

	/** Extracts and decodes the code carried by a templated URI. */
	private static String codeFromUri(String uri, String scheme) {
		return decodeId(tokenFromUri(uri, scheme));
	}

	// ---- id encoding -----------------------------------------------------------
	//
	// Node codes routinely contain '/', ':' or '#', but the MCP SDK matches a URI
	// template {var} with the regex "([^/]+)" — i.e. a value may not contain '/'.
	// Encoding every id as Base64URL (alphabet [A-Za-z0-9_-]) makes it a single,
	// slash-free, reserved-char-free segment that round-trips exactly.

	private static String encodeId(String raw) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
	}

	private static String decodeId(String token) {
		if (token == null) {
			return null;
		}
		try {
			return new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private static String kbUri(String code) {
		return KB_SCHEME + encodeId(code);
	}

	private static String projectUri(String code) {
		return PROJECT_SCHEME + encodeId(code);
	}

	/**
	 * Endpoint identity is (concrete class, code); both are encoded into the id.
	 */
	private static String endpointUri(GProjectEndpoint endpoint) {
		return ENDPOINT_SCHEME + encodeId(endpoint.getClass().getName() + ' ' + endpoint.getCode());
	}

	private static String vfolderUri(String code) {
		return VFOLDER_SCHEME + encodeId(code);
	}

	private static String documentUri(String code) {
		return DOCUMENT_SCHEME + encodeId(code);
	}

	private static String documentTextUri(String code) {
		return DOCUMENT_TEXT_SCHEME + encodeId(code);
	}

	private <T extends GBaseObject> T findById(String code, Class<T> type) throws GeboPersistenceException {
		return persistenceManager.findById(type, code);
	}

	private static String name(String description, String code) {
		return description != null && !description.isBlank() ? description : code;
	}

	/**
	 * Renders a knowledge-base summary followed by its content subtree: every
	 * visible project, each project's endpoints, and, under each endpoint, its
	 * nested virtual folders down to the configured
	 * {@link GeboMcpResourcesConfig#getEndpointMaxFolderDepth() depth}. Documents
	 * are not expanded here; they are only returned when the parent folder is read.
	 */
	private String summarizeKnowledgeBase(GKnowledgeBase kb) {
		StringBuilder sb = new StringBuilder();
		sb.append("Knowledge base: ").append(kb.getCode()).append(NEWLINE);
		sb.append("Description: ").append(safe(kb.getDescription())).append(NEWLINE);
		sb.append("Object space type: ").append(kb.getObjectSpaceType()).append(NEWLINE);
		sb.append("Parent knowledge base: ").append(safe(kb.getParentKnowledgebaseCode())).append(NEWLINE);
		sb.append("Contents:");
		int before = sb.length();
		List<GProject> projects = visibilityService.getVisibleProjectsByKnowledgeBaseCode(kb.getCode());
		if (projects != null) {
			for (GProject project : projects) {
				sb.append(NEWLINE).append("- project: ").append(name(project.getDescription(), project.getCode()))
						.append("  ").append(projectUri(project.getCode()));
				appendProjectSubtree(project, sb, "    ", 1);
			}
		}
		if (sb.length() == before) {
			sb.append(" (none)");
		}
		return sb.toString();
	}

	/**
	 * Renders a project summary followed by its content subtree (endpoints with
	 * their nested folders, and sub-projects, recursively).
	 */
	private String summarizeProject(GProject project) {
		StringBuilder sb = new StringBuilder();
		sb.append("Project: ").append(project.getCode()).append(NEWLINE);
		sb.append("Description: ").append(safe(project.getDescription())).append(NEWLINE);
		sb.append("Root knowledge base: ").append(safe(project.getRootKnowledgeBaseCode())).append(NEWLINE);
		sb.append("Parent project: ").append(safe(project.getParentProjectCode())).append(NEWLINE);
		sb.append("Contents:");
		int before = sb.length();
		appendProjectSubtree(project, sb, "", 1);
		if (sb.length() == before) {
			sb.append(" (none)");
		}
		return sb.toString();
	}

	/**
	 * Renders an endpoint summary followed by its nested virtual folders up to the
	 * configured depth. Documents are intentionally not expanded here — they are
	 * only returned when the parent virtual folder is read via
	 * {@code gebo-vfolder://}.
	 */
	private String summarizeEndpoint(GProjectEndpoint endpoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("Project endpoint: ").append(endpoint.getCode()).append(NEWLINE);
		sb.append("Description: ").append(safe(endpoint.getDescription())).append(NEWLINE);
		sb.append("Parent project: ").append(safe(endpoint.getParentProjectCode())).append(NEWLINE);
		sb.append("Published: ").append(endpoint.getPublished()).append(NEWLINE);
		sb.append("Folders (up to ").append(limitsConfig.getEndpointMaxFolderDepth()).append(" levels):");
		appendEndpointFolders(endpoint, sb, "");
		return sb.toString();
	}

	/**
	 * Descends a project: lists its visible endpoints (each expanded to its nested
	 * folders) and its visible sub-projects (recursively, bounded by
	 * {@link #MAX_STRUCTURAL_DEPTH}). Each node is indented under its parent and
	 * carries its addressable URI.
	 */
	private void appendProjectSubtree(GProject project, StringBuilder sb, String indent, int depth) {
		if (depth > MAX_STRUCTURAL_DEPTH) {
			return;
		}
		List<GProjectEndpoint> endpoints = visibilityService
				.getVisibleProjectsEndpointByParentProjectCode(project.getCode());
		if (endpoints != null) {
			for (GProjectEndpoint endpoint : endpoints) {
				sb.append(NEWLINE).append(indent).append("- endpoint: ")
						.append(name(endpoint.getDescription(), endpoint.getCode())).append("  ")
						.append(endpointUri(endpoint));
				appendEndpointFolders(endpoint, sb, indent + "    ");
			}
		}
		List<GProject> subProjects = visibilityService.getVisibleProjectsByParentProjectCode(project.getCode());
		if (subProjects != null) {
			for (GProject sub : subProjects) {
				sb.append(NEWLINE).append(indent).append("- project: ")
						.append(name(sub.getDescription(), sub.getCode())).append("  ")
						.append(projectUri(sub.getCode()));
				appendProjectSubtree(sub, sb, indent + "    ", depth + 1);
			}
		}
	}

	/**
	 * Appends an endpoint's nested virtual folders (up to the configured depth)
	 * under the given indent, or a marker when the endpoint has no visible folders.
	 */
	private void appendEndpointFolders(GProjectEndpoint endpoint, StringBuilder sb, String indent) {
		List<GVirtualFolder> roots = visibilityService
				.getVisibleProjectEndpointRootsByParentEndpoint(endpoint.getCode(), endpoint.getClass().getName());
		if (!hasVisible(roots)) {
			sb.append("  (no folders)");
			return;
		}
		appendFolderTree(roots, sb, 1, indent);
	}

	/**
	 * Appends the given virtual folders and recurses into their child folders until
	 * the configured {@link GeboMcpResourcesConfig#getEndpointMaxFolderDepth()
	 * depth} is reached. Only folders are listed; each carries its
	 * {@code gebo-vfolder://} URI.
	 */
	private void appendFolderTree(List<? extends GAbstractVirtualFilesystemObject> folders, StringBuilder sb,
			int folderLevel, String indent) {
		if (folders == null) {
			return;
		}
		for (GAbstractVirtualFilesystemObject folder : folders) {
			if (isDeleted(folder)) {
				continue;
			}
			sb.append(NEWLINE).append(indent).append("- ").append(displayName(folder)).append("  ")
					.append(vfolderUri(folder.getCode()));
			if (folderLevel < limitsConfig.getEndpointMaxFolderDepth()) {
				appendFolderTree(visibilityService.getVisibleChildVirtualFolders(folder.getCode()), sb, folderLevel + 1,
						indent + "    ");
			}
		}
	}

	private static boolean hasVisible(List<? extends GAbstractVirtualFilesystemObject> objects) {
		if (objects == null) {
			return false;
		}
		for (GAbstractVirtualFilesystemObject object : objects) {
			if (!isDeleted(object)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Lists the child folders (navigable via {@code gebo-vfolder://}) and the child
	 * documents of the requested folder. Both sets are returned by the visibility
	 * service already filtered to what the calling user may see.
	 */
	private String summarizeVirtualFolder(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("Virtual folder: ").append(code).append(NEWLINE);
		sb.append("Child folders (up to ").append(limitsConfig.getEndpointMaxFolderDepth()).append(" levels):");
		List<GVirtualFolder> folders = visibilityService.getVisibleChildVirtualFolders(code);
		if (!hasVisible(folders)) {
			sb.append(" (none)");
		} else {
			appendFolderTree(folders, sb, 1, "");
		}
		sb.append(NEWLINE).append("Documents:");
		boolean anyDoc = false;
		List<GDocumentReference> documents = visibilityService.getVisibleChildDocuments(code);
		if (documents != null) {
			for (GDocumentReference document : documents) {
				if (isDeleted(document)) {
					continue;
				}
				anyDoc = true;
				sb.append(NEWLINE).append("- ").append(displayName(document)).append("  ")
						.append(documentUri(document.getCode())).append("  (text: ")
						.append(documentTextUri(document.getCode())).append(')');
			}
		}
		if (!anyDoc) {
			sb.append(" (none)");
		}
		return sb.toString();
	}

	/**
	 * Resolves a document by code and confirms the calling user may read it. A
	 * {@link GDocumentReference} carries only the ACL security model
	 * ({@link ai.gebo.acl.IAclGrantedResource}, not
	 * {@link ai.gebo.model.IGObjectWithSecurity}), so access is checked with
	 * {@link IGSecurityService#isCanDoAction} for {@code READ} — the applicable
	 * platform-standard check for this type. Under the {@code ACL_BASED} policy this
	 * enforces the caller's grants; under {@code GROUP_BASED} it is intentionally
	 * permissive, per platform convention (per-user containment is then applied by
	 * {@link IGKnowledgebaseVisibilityService} during the folder navigation through
	 * which the document's URI is discovered). Returns {@code null} when the document
	 * does not exist, is deleted, or the caller may not read it.
	 *
	 * @throws GeboPersistenceException
	 */
	private GDocumentReference resolveVisibleDocument(String code) throws GeboPersistenceException {
		GDocumentReference document = findById(code, GDocumentReference.class);
		if (document == null || isDeleted(document)) {
			return null;
		}
		if (!securityService.isCanDoAction(document, true, AclGrantType.READ))
			return null;

		return document;
	}

	/**
	 * Streams the document content through the documents cache service and wraps it
	 * as MCP resource content: textual MIME types are returned inline as
	 * {@link TextResourceContents}, everything else as a base64
	 * {@link BlobResourceContents}. The payload is capped at the configured
	 * {@link GeboMcpResourcesConfig#getMaxDocumentBytes() maximum} to protect the
	 * server.
	 */
	private ReadResourceResult readDocumentContent(String uri, GDocumentReference document) {
		InputStream input = null;
		try {
			if (!securityService.isCanDoAction(document, true, AclGrantType.READ))
				throw new SecurityException("Trying to read an unaccessible document");
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
	 * {@link GeboMcpResourcesConfig#getMaxDocumentTextChars() maximum}). Returned
	 * as {@code text/plain}.
	 */
	private ReadResourceResult readDocumentText(String uri, GDocumentReference document) {
		InputStream input = null;
		try {
			if (!securityService.isCanDoAction(document, true, AclGrantType.READ))
				throw new SecurityException("Trying to read an unaccessible document");
			TypedInputStream typed = documentsCacheService.streamDocument(StreamingPurpose.INGESTING, document);
			if (typed == null || typed.getInputStream() == null) {
				throw new IllegalStateException("No content available for document " + uri);
			}
			input = typed.getInputStream();
			IngestionHandlerData data = ingestionHandler.handleContent(document, typed);
			if (data == null) {
				throw new IllegalStateException("No extractable text for document " + uri);
			}
			StringBuilder text = new StringBuilder();
			if (!data.isUnmanagedContent()) {
				try (Stream<Document> documents = data.getStream()) {
					documents.forEach(doc -> appendDocumentText(text, doc));
				}
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
	 * Appends a single ingested {@link Document}'s text to the accumulator,
	 * separating fragments with a blank line. When the configured
	 * {@link GeboMcpResourcesConfig#getMaxDocumentTextChars() cap} is reached the
	 * text is truncated (with a {@value #TRUNCATION_MARKER} marker) and any further
	 * fragments are skipped, so the caller still receives a usable prefix of a
	 * large document instead of an error.
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
	 * Whether the given MIME type can be returned inline as UTF-8 text. Anything
	 * not recognised as textual is served as a binary blob.
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

	private static String safe(String value) {
		return value != null ? value : "";
	}
}
