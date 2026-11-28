/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

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
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.IGZammadVirtualFilesystemConsumingService;
import ai.gebo.zammad.handler.ZammadResourceReference;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadNavigationCoordinates;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * Reads the selected Zammad groups (their tickets) and knowledge bases (their
 * categories and answers) into a knowledge base. Each ticket and each answer
 * becomes an html document, attachments are streamed as they are.
 */
public class GZammadRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GZammadSystem, GZammadProjectEndpoint, ZammadNativePositionObject, ZammadNavigationCoordinates, ZammadResourceReference>
		implements IGZammadVirtualFilesystemConsumingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GZammadRemoteVirtualFilesystemConsumingServiceImpl.class);
	static final String ZAMMAD_TREE = "ZAMMAD-TREE";
	protected final ZammadApiClientFactory clientFactory;

	public GZammadRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			ZammadApiClientFactory clientFactory) {
		super(documentFactory);
		this.clientFactory = clientFactory;
	}

	private static ZammadRemoteTree tree(Map<String, Object> environment) {
		return (ZammadRemoteTree) environment.get(ZAMMAD_TREE);
	}

	private Map<String, Object> newEnvironment(GZammadSystem system) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(ZAMMAD_TREE, new ZammadRemoteTree(clientFactory.getConnection(system)));
		} catch (GeboCryptSecretException | IllegalArgumentException e) {
			LOGGER.error("Cannot open the Zammad connection of the system " + system.getCode(), e);
			throw new GeboContentHandlerSystemException(e.getMessage(), e);
		}
		return environment;
	}

	@Override
	protected Map<String, Object> createEnvironment(GZammadSystem system, GZammadProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		return newEnvironment(system);
	}

	@Override
	protected Map<String, Object> createEnvironment(GZammadSystem system) throws GeboContentHandlerSystemException {
		return newEnvironment(system);
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GZammadSystem system,
			GZammadProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		if (environment != null) {
			environment.clear();
		}
	}

	@Override
	protected ZammadNavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			ZammadNavigationCoordinates coordinates = ZammadNavigationUtil.toCoordinates(path);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad reference decoded as {}", coordinates);
			}
			return coordinates;
		} catch (IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Not a Zammad reference: " + path, e);
		}
	}

	/**
	 * Reads the root (group or knowledge base) and every step of the path; an
	 * empty list when one of them is gone from Zammad.
	 */
	private List<ZammadNativePositionObject> resolve(ZammadNavigationCoordinates position,
			Map<String, Object> environment) {
		ZammadRemoteTree tree = tree(environment);
		List<ZammadNativePositionObject> natives = new ArrayList<ZammadNativePositionObject>();
		ZammadPathComponent root = ZammadNavigationUtil.rootOf(position.getRoot());
		try {
			natives.add(tree.root(root));
			String parentId = root.id;
			for (ZammadPathComponent step : position.getBrowsingStepsCustom()) {
				natives.add(tree.node(root, step, parentId));
				parentId = step.id;
			}
		} catch (RestClientException e) {
			if (ZammadRemoteTree.isNotFound(e)) {
				LOGGER.warn("The Zammad item {} does not exist any more", position);
				return List.of();
			}
			throw e;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad position {} resolved to {}", position, natives);
		}
		return natives;
	}

	@Override
	protected List<ZammadNativePositionObject> toNativeCoordinates(ZammadNavigationCoordinates position,
			GZammadSystem system, GZammadProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot read the Zammad item " + position, e);
			// recorded once, as the administrator reads it in the job messages
			final ContentsAccessError error = ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER,
					position.describe());
			error.setMsg(ZammadErrors.of(e, system.getBaseUri()));
			errorConsumer.accept(error);
			throw new GeboContentHandlerSystemException("Cannot read the Zammad item " + position, e);
		}
	}

	@Override
	protected List<ZammadNativePositionObject> toResourcesNativeCoordinates(ZammadNavigationCoordinates position,
			GZammadSystem system, Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RestClientException | IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Cannot read the Zammad item " + position, e);
		}
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<ZammadNativePositionObject> nativeCoordinates,
			ZammadNavigationCoordinates position, GZammadSystem system, GZammadProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		ZammadNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		ZammadNativePositionObject first = nativeCoordinates.get(0);
		ZammadPathComponent root = new ZammadPathComponent(first.getType(), first.getId());
		try {
			for (ZammadNativePositionObject child : tree(environment).children(root, last.getType(), last.getId())) {
				NativeCoordinatePointer pointer = new NativeCoordinatePointer();
				pointer.parentCoordinates = new ArrayList<ZammadNativePositionObject>(nativeCoordinates);
				pointer.child = child;
				childs.add(pointer);
			}
		} catch (RestClientException | IllegalArgumentException e) {
			throw new GeboContentHandlerSystemException("Cannot list the contents of the Zammad " + last, e);
		}
		return childs;
	}

	@Override
	protected ZammadNavigationCoordinates getPositionCoordinate(List<ZammadNativePositionObject> childCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return ZammadNavigationUtil.toCoordinates(childCoordinates);
	}

	private static String metaString(Map<String, Object> metas, String key) {
		Object value = metas.get(key);
		return value != null ? value.toString() : null;
	}

	private static Long metaLong(Map<String, Object> metas, String key) {
		String value = metaString(metas, key);
		try {
			return value != null ? Long.valueOf(value) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * The reference of a stored item: never fails, also for folders, because
	 * the end of job deletion check asks it for every item not met.
	 */
	static ZammadResourceReference toReference(Map<String, Object> metas) {
		ZammadResourceReference reference = new ZammadResourceReference();
		if (metas == null) {
			return reference;
		}
		String type = metaString(metas, ZammadNativePositionObject.ZAMMAD_OBJECT_TYPE);
		try {
			reference.type = type != null ? ZammadPathNodeType.valueOf(type) : null;
		} catch (IllegalArgumentException e) {
			reference.type = null;
		}
		reference.rootCode = metaString(metas, ZammadNativePositionObject.ZAMMAD_ROOT);
		reference.ticketId = metaLong(metas, ZammadNativePositionObject.ZAMMAD_TICKET_ID);
		reference.articleId = metaLong(metas, ZammadNativePositionObject.ZAMMAD_ARTICLE_ID);
		reference.knowledgeBaseId = metaLong(metas, ZammadNativePositionObject.ZAMMAD_KNOWLEDGE_BASE_ID);
		reference.answerId = metaLong(metas, ZammadNativePositionObject.ZAMMAD_ANSWER_ID);
		reference.attachmentId = metaLong(metas, ZammadNativePositionObject.ZAMMAD_ATTACHMENT_ID);
		reference.contentType = metaString(metas, ZammadNativePositionObject.ZAMMAD_CONTENT_TYPE);
		return reference;
	}

	@Override
	public ZammadResourceReference getResourceHandle(GZammadSystem system, GZammadProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		return toReference(reference.getCustomMetaInfos());
	}

	@Override
	protected ZammadResourceReference getResourceHandle(SearchableSystemMetaData system,
			ZammadNavigationCoordinates navigationPosition, List<ZammadNativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (nativeCoordinates.isEmpty()) {
			return null;
		}
		return toReference(nativeCoordinates.get(nativeCoordinates.size() - 1).getResourceReferenceMetaInfos());
	}

	/**
	 * Opens an item: tickets and answers (or their contents) as their html
	 * rendering, attachments as they are.
	 */
	static InputStream open(ZammadResourceReference reference, ZammadRemoteTree tree)
			throws GeboContentHandlerSystemException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Streaming the Zammad item {}", reference);
		}
		if (reference.type == null) {
			throw new GeboContentHandlerSystemException("Not a Zammad item: " + reference);
		}
		String base = tree.getBaseUri();
		String text;
		switch (reference.type) {
		case TICKET:
		case TICKET_CONTENT: {
			if (reference.ticketId == null) {
				throw new GeboContentHandlerSystemException("A Zammad ticket without its id: " + reference);
			}
			Ticket ticket = tree.ticket(reference.ticketId);
			text = ZammadContentRenderer.renderTicket(ticket, tree.articles(reference.ticketId), base);
		}
			break;
		case ANSWER:
		case ANSWER_CONTENT: {
			if (reference.knowledgeBaseId == null || reference.answerId == null) {
				throw new GeboContentHandlerSystemException("A Zammad answer without its ids: " + reference);
			}
			ZammadKnowledgeBaseIndex index = tree.knowledgeBases();
			ZammadAnswer answer = tree.answer(reference.knowledgeBaseId, reference.answerId);
			Long categoryId = answer.categoryId();
			text = ZammadContentRenderer.renderAnswer(answer, index.knowledgeBaseTitle(reference.knowledgeBaseId),
					categoryId != null ? index.categoryPath(categoryId) : null,
					ZammadNodes.knowledgeBaseItemUrl(base, reference.knowledgeBaseId,
							tree.localeCode(reference.knowledgeBaseId), "answer", reference.answerId));
		}
			break;
		case ATTACHMENT:
			if (reference.attachmentId == null) {
				throw new GeboContentHandlerSystemException("A Zammad attachment without its id: " + reference);
			}
			return reference.ticketId != null && reference.articleId != null
					? tree.downloadTicketAttachment(reference.ticketId, reference.articleId, reference.attachmentId)
					: tree.downloadAttachment(reference.attachmentId);
		default:
			throw new GeboContentHandlerSystemException("The Zammad " + reference.type + " has no content");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Zammad item {} rendered as:\n{}", reference, text);
		}
		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}

	private ZammadRemoteTree cachedTree(GZammadSystem system, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		if (!cache.containsKey(ZAMMAD_TREE)) {
			cache.putAll(newEnvironment(system));
		}
		return tree(cache);
	}

	@Override
	public InputStream streamResource(GZammadSystem system, GZammadProjectEndpoint endpoint,
			ZammadResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		try {
			return open(reference, cachedTree(system, cache));
		} catch (RestClientException e) {
			throw new GeboContentHandlerSystemException("Cannot read the Zammad item " + reference, e);
		}
	}

	@Override
	protected InputStream streamResource(GZammadSystem system, ZammadResourceReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException {
		try {
			return open(reference, cachedTree(system, cache));
		} catch (IOException | RestClientException e) {
			throw new GeboContentHandlerSystemException("Cannot read the Zammad item " + reference, e);
		}
	}

	@Override
	protected String describeObject(List<ZammadNativePositionObject> references, GZammadSystem system,
			GZammadProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references.isEmpty()) {
			return "<<Incoherent hierarchy>>";
		}
		ZammadNativePositionObject last = references.get(references.size() - 1);
		return "Zammad " + last.getType() + " " + last.getName() + " (" + last.getUrl() + ")";
	}

	@Override
	protected String describeSystem(GZammadSystem system) {
		return "Zammad " + system.getDescription();
	}

	@Override
	protected String describeProjectEndpoint(GZammadSystem system, GZammadProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "Zammad contents source " + endpoint.getDescription();
	}

	/**
	 * Whether a stored item is still part of its data source: a ticket that is
	 * gone or moved to another group, an answer that is gone, archived or back to
	 * draft, an attachment its ticket or answer no longer has. Anything else
	 * (found, folders, other errors) gives no information, so the item is left
	 * as it is.
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GZammadSystem system,
			GZammadProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, ZammadResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (reference == null || reference.type == null || reference.type.isFolder()) {
			return null;
		}
		ZammadRemoteTree tree = tree(environment);
		String gone = null;
		try {
			if (reference.ticketId != null) {
				Ticket ticket = tree.ticket(reference.ticketId);
				String groupRoot = ticket.getGroupId() != null
						? ZammadNodes.rootCode(ZammadPathNodeType.GROUP, ticket.getGroupId())
						: null;
				if (reference.rootCode != null && !reference.rootCode.equals(groupRoot)) {
					gone = "moved to the group " + ticket.getGroupId();
				} else if (reference.type == ZammadPathNodeType.ATTACHMENT && !hasTicketAttachment(tree, reference)) {
					gone = "no longer attached";
				}
			} else if (reference.answerId != null) {
				ZammadKnowledgeBaseIndex index = tree.knowledgeBases();
				if (!ZammadKnowledgeBaseIndex.isListed(index.answerOf(reference.answerId))) {
					gone = "deleted, archived or a draft";
				} else if (reference.type == ZammadPathNodeType.ATTACHMENT
						&& index.answerAttachments(reference.answerId).stream().noneMatch(
								a -> String.valueOf(reference.attachmentId).equals(String.valueOf(a.get("id"))))) {
					gone = "no longer attached";
				}
			} else {
				return null;
			}
		} catch (RestClientException e) {
			if (!ZammadRemoteTree.isNotFound(e)) {
				LOGGER.warn("Cannot verify the Zammad item " + reference + ", left as it is", e);
				return null;
			}
			gone = "not found";
		}
		if (gone == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The Zammad item {} still exists", reference);
			}
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The Zammad item {} is {}, the document {} is deleted", reference, gone, doc.getCode());
		}
		doc.setDeleted(true);
		return doc;
	}

	private static boolean hasTicketAttachment(ZammadRemoteTree tree, ZammadResourceReference reference) {
		if (reference.articleId == null || reference.attachmentId == null) {
			return false;
		}
		for (TicketArticle article : tree.articles(reference.ticketId)) {
			if (reference.articleId.equals(article.getId()) && article.getAttachments() != null
					&& article.getAttachments().stream().anyMatch(a -> reference.attachmentId.equals(a.getId()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ZAMMAD_MODULE;
	}
}
