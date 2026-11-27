/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.model.GUserMessage;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.IGRedmineVirtualFilesystemConsumingService;
import ai.gebo.redmine.handler.RedmineResourceReference;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedmineNavigationCoordinates;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * Reads the selected Redmine projects, sections, issues, wiki pages and files
 * into a knowledge base. Issues and wiki pages become markdown documents,
 * attachments and files are streamed as they are.
 */
public class GRedmineRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GRedmineSystem, GRedmineProjectEndpoint, RedmineNativePositionObject, RedmineNavigationCoordinates, RedmineResourceReference>
		implements IGRedmineVirtualFilesystemConsumingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GRedmineRemoteVirtualFilesystemConsumingServiceImpl.class);
	static final String REDMINE_TREE = "REDMINE-TREE";
	protected final RedmineApiClientFactory clientFactory;

	public GRedmineRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			RedmineApiClientFactory clientFactory) {
		super(documentFactory);
		this.clientFactory = clientFactory;
	}

	private static RedmineRemoteTree tree(Map<String, Object> environment) {
		return (RedmineRemoteTree) environment.get(REDMINE_TREE);
	}

	private Map<String, Object> newEnvironment(GRedmineSystem system) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(REDMINE_TREE, new RedmineRemoteTree(clientFactory.getConnection(system)));
		} catch (GeboCryptSecretException | IllegalArgumentException e) {
			LOGGER.error("Cannot open the Redmine connection of the system " + system.getCode(), e);
			throw new GeboContentHandlerSystemException(e.getMessage(), e);
		}
		return environment;
	}

	@Override
	protected Map<String, Object> createEnvironment(GRedmineSystem system, GRedmineProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		return newEnvironment(system);
	}

	@Override
	protected Map<String, Object> createEnvironment(GRedmineSystem system) throws GeboContentHandlerSystemException {
		return newEnvironment(system);
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GRedmineSystem system,
			GRedmineProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		if (environment != null) {
			environment.clear();
		}
	}

	@Override
	protected RedmineNavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			RedmineNavigationCoordinates coordinates = RedmineNavigationUtil.toCoordinates(path);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine reference decoded as {}", coordinates);
			}
			return coordinates;
		} catch (IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Not a Redmine reference: " + path, e);
		}
	}

	/**
	 * Reads the project and every step of the path; an empty list when one of
	 * them is gone from Redmine.
	 */
	private List<RedmineNativePositionObject> resolve(RedmineNavigationCoordinates position,
			Map<String, Object> environment) {
		RedmineRemoteTree tree = tree(environment);
		List<RedmineNativePositionObject> natives = new ArrayList<RedmineNativePositionObject>();
		String projectId = RedmineNavigationUtil.projectIdOf(position.getRoot());
		try {
			RedmineNativePositionObject project = tree.project(projectId);
			natives.add(project);
			for (RedminePathComponent step : position.getBrowsingStepsCustom()) {
				natives.add(tree.node(project.getId(), step));
			}
		} catch (RestClientException e) {
			if (RedmineRemoteTree.isNotFound(e)) {
				LOGGER.warn("The Redmine item {} does not exist any more", position);
				return List.of();
			}
			throw e;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Redmine position {} resolved to {}", position, natives);
		}
		return natives;
	}

	@Override
	protected List<RedmineNativePositionObject> toNativeCoordinates(RedmineNavigationCoordinates position,
			GRedmineSystem system, GRedmineProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot read the Redmine item " + position, e);
			errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, position.toString()));
			// the shared consuming loop only logs this exception: the job's messages are
			// where the administrator reads why nothing was ingested
			messagesConsumer.accept(GUserMessage.errorMessage("Cannot read " + position.describe(),
					RedmineErrors.of(e, system.getBaseUri())));
			throw new GeboContentHandlerSystemException("Cannot read the Redmine item " + position, e);
		}
	}

	@Override
	protected List<RedmineNativePositionObject> toResourcesNativeCoordinates(RedmineNavigationCoordinates position,
			GRedmineSystem system, Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RestClientException | IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Cannot read the Redmine item " + position, e);
		}
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<RedmineNativePositionObject> nativeCoordinates,
			RedmineNavigationCoordinates position, GRedmineSystem system, GRedmineProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		RedmineNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		String projectId = nativeCoordinates.get(0).getId();
		try {
			for (RedmineNativePositionObject child : tree(environment).children(projectId, last.getType(),
					last.getId())) {
				NativeCoordinatePointer pointer = new NativeCoordinatePointer();
				pointer.parentCoordinates = new ArrayList<RedmineNativePositionObject>(nativeCoordinates);
				pointer.child = child;
				childs.add(pointer);
			}
		} catch (RestClientException | IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Cannot list the contents of the Redmine " + last, e);
		}
		return childs;
	}

	@Override
	protected RedmineNavigationCoordinates getPositionCoordinate(List<RedmineNativePositionObject> childCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return RedmineNavigationUtil.toCoordinates(childCoordinates);
	}

	private static String metaString(Map<String, Object> metas, String key) {
		Object value = metas.get(key);
		return value != null ? value.toString() : null;
	}

	private static Integer metaInteger(Map<String, Object> metas, String key) {
		String value = metaString(metas, key);
		try {
			return value != null ? Integer.valueOf(value) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * The reference of a stored item: never fails, also for folders, because
	 * the end of job deletion check asks it for every item not met.
	 */
	static RedmineResourceReference toReference(Map<String, Object> metas) {
		RedmineResourceReference reference = new RedmineResourceReference();
		if (metas == null) {
			return reference;
		}
		String type = metaString(metas, RedmineNativePositionObject.REDMINE_OBJECT_TYPE);
		try {
			reference.type = type != null ? RedminePathNodeType.valueOf(type) : null;
		} catch (IllegalArgumentException e) {
			reference.type = null;
		}
		reference.projectId = metaString(metas, RedmineNativePositionObject.REDMINE_PROJECT_ID);
		reference.issueId = metaInteger(metas, RedmineNativePositionObject.REDMINE_ISSUE_ID);
		reference.wikiTitle = metaString(metas, RedmineNativePositionObject.REDMINE_WIKI_TITLE);
		reference.attachmentId = metaInteger(metas, RedmineNativePositionObject.REDMINE_ATTACHMENT_ID);
		reference.contentType = metaString(metas, RedmineNativePositionObject.REDMINE_CONTENT_TYPE);
		return reference;
	}

	@Override
	public RedmineResourceReference getResourceHandle(GRedmineSystem system, GRedmineProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		return toReference(reference.getCustomMetaInfos());
	}

	@Override
	protected RedmineResourceReference getResourceHandle(SearchableSystemMetaData system,
			RedmineNavigationCoordinates navigationPosition, List<RedmineNativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (nativeCoordinates.isEmpty()) {
			return null;
		}
		return toReference(nativeCoordinates.get(nativeCoordinates.size() - 1).getResourceReferenceMetaInfos());
	}

	/**
	 * Opens an item: an issue (or its content) and a wiki page (or its content)
	 * as their markdown rendering, attachments and files as they are.
	 */
	private InputStream open(RedmineResourceReference reference, RedmineRemoteTree tree)
			throws GeboContentHandlerSystemException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Streaming the Redmine item {}", reference);
		}
		if (reference.type == null) {
			throw new GeboContentHandlerSystemException("Not a Redmine item: " + reference);
		}
		String text;
		switch (reference.type) {
		case ISSUE:
		case ISSUE_CONTENT:
			text = RedmineMarkdownRenderer.renderIssue(
					tree.issue(reference.issueId, RedmineRemoteTree.ISSUE_CONTENT_INCLUDES), tree.getBaseUri());
			break;
		case WIKI_PAGE:
		case WIKI_CONTENT:
			text = RedmineMarkdownRenderer.renderWikiPage(
					tree.wikiPage(reference.projectId, reference.wikiTitle, RedmineRemoteTree.WIKI_INCLUDES),
					reference.projectId, tree.getBaseUri());
			break;
		case ATTACHMENT:
		case FILE:
			return tree.download(reference.attachmentId);
		default:
			throw new GeboContentHandlerSystemException("The Redmine " + reference.type + " has no content");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Redmine item {} rendered as:\n{}", reference, text);
		}
		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}

	private RedmineRemoteTree cachedTree(GRedmineSystem system, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		if (!cache.containsKey(REDMINE_TREE)) {
			cache.putAll(newEnvironment(system));
		}
		return tree(cache);
	}

	@Override
	public InputStream streamResource(GRedmineSystem system, GRedmineProjectEndpoint endpoint,
			RedmineResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		try {
			return open(reference, cachedTree(system, cache));
		} catch (RestClientException e) {
			throw new GeboContentHandlerSystemException("Cannot read the Redmine item " + reference, e);
		}
	}

	@Override
	protected InputStream streamResource(GRedmineSystem system, RedmineResourceReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException {
		try {
			return open(reference, cachedTree(system, cache));
		} catch (IOException | RestClientException e) {
			throw new GeboContentHandlerSystemException("Cannot read the Redmine item " + reference, e);
		}
	}

	@Override
	protected String describeObject(List<RedmineNativePositionObject> references, GRedmineSystem system,
			GRedmineProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references.isEmpty()) {
			return "<<Incoherent hierarchy>>";
		}
		RedmineNativePositionObject last = references.get(references.size() - 1);
		return "Redmine " + last.getType() + " " + last.getName() + " (" + last.getUrl() + ")";
	}

	@Override
	protected String describeSystem(GRedmineSystem system) {
		return "Redmine " + system.getDescription();
	}

	@Override
	protected String describeProjectEndpoint(GRedmineSystem system, GRedmineProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "Redmine contents source " + endpoint.getDescription();
	}

	/**
	 * An issue, wiki page, attachment or file Redmine answers "not found" for is
	 * reported deleted; anything else (found, folders, other errors) gives no
	 * information, so the item is left as it is.
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GRedmineSystem system,
			GRedmineProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, RedmineResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (reference == null || reference.type == null || reference.type.isFolder()) {
			return null;
		}
		RedmineRemoteTree tree = tree(environment);
		try {
			switch (reference.type) {
			case ISSUE_CONTENT:
				tree.issue(reference.issueId, null);
				break;
			case WIKI_CONTENT:
				tree.wikiPage(reference.projectId, reference.wikiTitle, null);
				break;
			case ATTACHMENT:
			case FILE:
				tree.attachment(reference.attachmentId);
				break;
			default:
				return null;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The Redmine item {} still exists", reference);
			}
			return null;
		} catch (RestClientException e) {
			if (RedmineRemoteTree.isNotFound(e)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The Redmine item {} is gone, the document {} is deleted", reference, doc.getCode());
				}
				doc.setDeleted(true);
				return doc;
			}
			LOGGER.warn("Cannot verify the Redmine item " + reference + ", left as it is", e);
			return null;
		}
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.REDMINE_MODULE;
	}
}
