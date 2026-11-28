/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

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
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.openproject.handler.IGOpenProjectVirtualFilesystemConsumingService;
import ai.gebo.openproject.handler.OpenProjectResourceReference;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectNavigationCoordinates;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * Reads the selected OpenProject projects, sections, work packages, documents,
 * meetings and news into a knowledge base. Each item becomes a markdown document,
 * attachments are streamed as they are.
 */
public class GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GOpenProjectSystem, GOpenProjectProjectEndpoint, OpenProjectNativePositionObject, OpenProjectNavigationCoordinates, OpenProjectResourceReference>
		implements IGOpenProjectVirtualFilesystemConsumingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl.class);
	static final String OPENPROJECT_TREE = "OPENPROJECT-TREE";
	protected final OpenProjectApiClientFactory clientFactory;

	public GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			OpenProjectApiClientFactory clientFactory) {
		super(documentFactory);
		this.clientFactory = clientFactory;
	}

	private static OpenProjectRemoteTree tree(Map<String, Object> environment) {
		return (OpenProjectRemoteTree) environment.get(OPENPROJECT_TREE);
	}

	private Map<String, Object> newEnvironment(GOpenProjectSystem system) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(OPENPROJECT_TREE, new OpenProjectRemoteTree(clientFactory.getConnection(system)));
		} catch (GeboCryptSecretException | IllegalArgumentException e) {
			LOGGER.error("Cannot open the OpenProject connection of the system " + system.getCode(), e);
			throw new GeboContentHandlerSystemException(e.getMessage(), e);
		}
		return environment;
	}

	@Override
	protected Map<String, Object> createEnvironment(GOpenProjectSystem system, GOpenProjectProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		return newEnvironment(system);
	}

	@Override
	protected Map<String, Object> createEnvironment(GOpenProjectSystem system) throws GeboContentHandlerSystemException {
		return newEnvironment(system);
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GOpenProjectSystem system,
			GOpenProjectProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		if (environment != null) {
			environment.clear();
		}
	}

	@Override
	protected OpenProjectNavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			OpenProjectNavigationCoordinates coordinates = OpenProjectNavigationUtil.toCoordinates(path);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenProject reference decoded as {}", coordinates);
			}
			return coordinates;
		} catch (IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Not an OpenProject reference: " + path, e);
		}
	}

	/**
	 * Reads the project and every step of the path; an empty list when one of
	 * them is gone from OpenProject.
	 */
	private List<OpenProjectNativePositionObject> resolve(OpenProjectNavigationCoordinates position,
			Map<String, Object> environment) {
		OpenProjectRemoteTree tree = tree(environment);
		List<OpenProjectNativePositionObject> natives = new ArrayList<OpenProjectNativePositionObject>();
		String projectId = OpenProjectNavigationUtil.projectIdOf(position.getRoot());
		try {
			OpenProjectNativePositionObject project = tree.project(projectId);
			natives.add(project);
			for (OpenProjectPathComponent step : position.getBrowsingStepsCustom()) {
				natives.add(tree.node(project.getId(), step));
			}
		} catch (RestClientException e) {
			if (OpenProjectRemoteTree.isNotFound(e)) {
				LOGGER.warn("The OpenProject item {} does not exist any more", position);
				return List.of();
			}
			throw e;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject position {} resolved to {}", position, natives);
		}
		return natives;
	}

	@Override
	protected List<OpenProjectNativePositionObject> toNativeCoordinates(OpenProjectNavigationCoordinates position,
			GOpenProjectSystem system, GOpenProjectProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot read the OpenProject item " + position, e);
			// recorded once, as the administrator reads it in the job messages
			final ContentsAccessError error = ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER,
					position.describe());
			error.setMsg(OpenProjectErrors.of(e, system.getBaseUri()));
			errorConsumer.accept(error);
			throw new GeboContentHandlerSystemException("Cannot read the OpenProject item " + position, e);
		}
	}

	@Override
	protected List<OpenProjectNativePositionObject> toResourcesNativeCoordinates(OpenProjectNavigationCoordinates position,
			GOpenProjectSystem system, Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RestClientException | IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Cannot read the OpenProject item " + position, e);
		}
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<OpenProjectNativePositionObject> nativeCoordinates,
			OpenProjectNavigationCoordinates position, GOpenProjectSystem system, GOpenProjectProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		OpenProjectNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		String projectId = nativeCoordinates.get(0).getId();
		try {
			for (OpenProjectNativePositionObject child : tree(environment).children(projectId, last.getType(),
					last.getId())) {
				NativeCoordinatePointer pointer = new NativeCoordinatePointer();
				pointer.parentCoordinates = new ArrayList<OpenProjectNativePositionObject>(nativeCoordinates);
				pointer.child = child;
				childs.add(pointer);
			}
		} catch (RestClientException | IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Cannot list the contents of the OpenProject " + last, e);
		}
		return childs;
	}

	@Override
	protected OpenProjectNavigationCoordinates getPositionCoordinate(List<OpenProjectNativePositionObject> childCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return OpenProjectNavigationUtil.toCoordinates(childCoordinates);
	}

	private static String metaString(Map<String, Object> metas, String key) {
		Object value = metas.get(key);
		return value != null ? value.toString() : null;
	}

	/** The meta info holding the number of an item of the given type. */
	static String itemIdOf(OpenProjectPathNodeType type, Map<String, Object> metas) {
		if (type == null) {
			return null;
		}
		switch (type) {
		case WORK_PACKAGE:
		case WORK_PACKAGE_CONTENT:
			return metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID);
		case DOCUMENT:
		case DOCUMENT_CONTENT:
			return metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_DOCUMENT_ID);
		case MEETING:
		case MEETING_CONTENT:
			return metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_MEETING_ID);
		case NEWS:
			return metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_NEWS_ID);
		case ATTACHMENT:
			return metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_ATTACHMENT_ID);
		default:
			return null;
		}
	}

	/**
	 * The reference of a stored item: never fails, also for folders, because
	 * the end of job deletion check asks it for every item not met.
	 */
	static OpenProjectResourceReference toReference(Map<String, Object> metas) {
		OpenProjectResourceReference reference = new OpenProjectResourceReference();
		if (metas == null) {
			return reference;
		}
		String type = metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_OBJECT_TYPE);
		try {
			reference.type = type != null ? OpenProjectPathNodeType.valueOf(type) : null;
		} catch (IllegalArgumentException e) {
			reference.type = null;
		}
		reference.projectId = metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_PROJECT_ID);
		reference.itemId = itemIdOf(reference.type, metas);
		reference.contentType = metaString(metas, OpenProjectNativePositionObject.OPENPROJECT_CONTENT_TYPE);
		return reference;
	}

	@Override
	public OpenProjectResourceReference getResourceHandle(GOpenProjectSystem system, GOpenProjectProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		return toReference(reference.getCustomMetaInfos());
	}

	@Override
	protected OpenProjectResourceReference getResourceHandle(SearchableSystemMetaData system,
			OpenProjectNavigationCoordinates navigationPosition, List<OpenProjectNativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (nativeCoordinates.isEmpty()) {
			return null;
		}
		return toReference(nativeCoordinates.get(nativeCoordinates.size() - 1).getResourceReferenceMetaInfos());
	}

	/**
	 * Opens an item: work packages, documents, meetings (or their contents) and
	 * news as their markdown rendering, attachments as they are.
	 */
	static InputStream open(OpenProjectResourceReference reference, OpenProjectRemoteTree tree)
			throws GeboContentHandlerSystemException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Streaming the OpenProject item {}", reference);
		}
		if (reference.type == null || reference.itemId == null) {
			throw new GeboContentHandlerSystemException("Not an OpenProject item: " + reference);
		}
		String id = reference.itemId;
		String base = tree.getBaseUri();
		String text;
		switch (reference.type) {
		case WORK_PACKAGE:
		case WORK_PACKAGE_CONTENT: {
			Map<String, Object> wp = tree.workPackage(id);
			String project = reference.projectId;
			text = OpenProjectMarkdownRenderer.renderWorkPackage(wp, tree.relations(id),
					project != null ? tree.childWorkPackages(project, id) : List.of(),
					tree.attachments("/work_packages/" + id), tree.activities(id), tree::userName, base);
		}
			break;
		case DOCUMENT:
		case DOCUMENT_CONTENT:
			text = OpenProjectMarkdownRenderer.renderDocument(tree.document(id), tree.attachments("/documents/" + id),
					base);
			break;
		case MEETING:
		case MEETING_CONTENT:
			text = OpenProjectMarkdownRenderer.renderMeeting(tree.meeting(id), tree.agendaItems(id),
					tree.attachments("/meetings/" + id), tree::userName, base);
			break;
		case NEWS:
			text = OpenProjectMarkdownRenderer.renderNews(tree.news(id), tree::userName, base);
			break;
		case ATTACHMENT:
			return tree.download(id);
		default:
			throw new GeboContentHandlerSystemException("The OpenProject " + reference.type + " has no content");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("OpenProject item {} rendered as:\n{}", reference, text);
		}
		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}

	private OpenProjectRemoteTree cachedTree(GOpenProjectSystem system, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		if (!cache.containsKey(OPENPROJECT_TREE)) {
			cache.putAll(newEnvironment(system));
		}
		return tree(cache);
	}

	@Override
	public InputStream streamResource(GOpenProjectSystem system, GOpenProjectProjectEndpoint endpoint,
			OpenProjectResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		try {
			return open(reference, cachedTree(system, cache));
		} catch (RestClientException e) {
			throw new GeboContentHandlerSystemException("Cannot read the OpenProject item " + reference, e);
		}
	}

	@Override
	protected InputStream streamResource(GOpenProjectSystem system, OpenProjectResourceReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException {
		try {
			return open(reference, cachedTree(system, cache));
		} catch (IOException | RestClientException e) {
			throw new GeboContentHandlerSystemException("Cannot read the OpenProject item " + reference, e);
		}
	}

	@Override
	protected String describeObject(List<OpenProjectNativePositionObject> references, GOpenProjectSystem system,
			GOpenProjectProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references.isEmpty()) {
			return "<<Incoherent hierarchy>>";
		}
		OpenProjectNativePositionObject last = references.get(references.size() - 1);
		return "OpenProject " + last.getType() + " " + last.getName() + " (" + last.getUrl() + ")";
	}

	@Override
	protected String describeSystem(GOpenProjectSystem system) {
		return "OpenProject " + system.getDescription();
	}

	@Override
	protected String describeProjectEndpoint(GOpenProjectSystem system, GOpenProjectProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "OpenProject contents source " + endpoint.getDescription();
	}

	/**
	 * A work package, document, meeting, news or attachment OpenProject answers "not found" for is
	 * reported deleted; anything else (found, folders, other errors) gives no
	 * information, so the item is left as it is.
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GOpenProjectSystem system,
			GOpenProjectProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, OpenProjectResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (reference == null || reference.type == null || reference.type.isFolder()) {
			return null;
		}
		OpenProjectRemoteTree tree = tree(environment);
		try {
			switch (reference.type) {
			case WORK_PACKAGE_CONTENT:
				tree.workPackage(reference.itemId);
				break;
			case DOCUMENT_CONTENT:
				tree.document(reference.itemId);
				break;
			case MEETING_CONTENT:
				tree.meeting(reference.itemId);
				break;
			case NEWS:
				tree.news(reference.itemId);
				break;
			case ATTACHMENT:
				tree.attachment(reference.itemId);
				break;
			default:
				return null;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The OpenProject item {} still exists", reference);
			}
			return null;
		} catch (RestClientException e) {
			if (OpenProjectRemoteTree.isNotFound(e)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The OpenProject item {} is gone, the document {} is deleted", reference, doc.getCode());
				}
				doc.setDeleted(true);
				return doc;
			}
			LOGGER.warn("Cannot verify the OpenProject item " + reference + ", left as it is", e);
			return null;
		}
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.OPENPROJECT_MODULE;
	}
}
