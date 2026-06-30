/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.CentralizedProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.model.base.GObjectRef;
import io.modelcontextprotocol.server.McpServerFeatures.SyncResourceSpecification;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.Resource;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import lombok.AllArgsConstructor;

/**
 * Builds the MCP resource specifications exposed by an erogated MCP server from
 * the exported knowledge bases, projects and project endpoints of its
 * {@link GeboMCPServerConfig}.
 * <p>
 * Each resource is addressed by a stable URI ({@code gebo-kb://}, {@code gebo-project://}
 * or {@code gebo-endpoint://}). The read handler re-resolves the underlying object and
 * re-checks the calling user's access with {@link GeboMcpAccessChecker} at read time, so
 * a user can never read an object they are not entitled to even if it is listed on the
 * server. The body returned is a compact textual summary of the object's metadata, not
 * its full indexed content.
 */
@Service
@AllArgsConstructor
public class GeboMcpResourcesProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMcpResourcesProvider.class);

	private static final String KB_SCHEME = "gebo-kb://";
	private static final String PROJECT_SCHEME = "gebo-project://";
	private static final String ENDPOINT_SCHEME = "gebo-endpoint://";
	private static final String MIME_TEXT = "text/plain";

	private final KnowledgeBaseRepository knowledgeBaseRepository;
	private final ProjectRepository projectRepository;
	private final CentralizedProjectEndpointRepository endpointRepository;
	private final GeboMcpAccessChecker accessChecker;
	private final GeboMcpSecurityContextSupport securitySupport;

	/**
	 * Builds all resource specifications for the given configuration.
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

	private static String summarizeEndpoint(GProjectEndpoint endpoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("Project endpoint: ").append(endpoint.getCode()).append('\n');
		sb.append("Description: ").append(safe(endpoint.getDescription())).append('\n');
		sb.append("Parent project: ").append(safe(endpoint.getParentProjectCode())).append('\n');
		sb.append("Published: ").append(endpoint.getPublished());
		return sb.toString();
	}

	private static String safe(String value) {
		return value != null ? value : "";
	}
}
