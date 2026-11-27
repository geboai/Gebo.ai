/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Entity;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.client.model.TicketTask;
import ai.gebo.glpi.handler.GlpiBrowsingContext;
import ai.gebo.glpi.handler.model.GlpiPathComponent;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * Lets the GLPI data source tree selector walk a GLPI system live: the
 * entities as roots, then their Tickets and Knowledge base sections, the
 * tickets and their items, the articles. It speaks the same encoding the
 * ingestion reads back from the saved selection (GlpiNavigationUtil).
 */
@Service
public class GlpiBrowsingService implements IGVirtualFilesystemBrowsingService<GlpiBrowsingContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlpiBrowsingService.class);
	private static final String GLPI = "GLPI";

	private final GlpiApiClientFactory apiClientFactory;

	public GlpiBrowsingService(GlpiApiClientFactory apiClientFactory) {
		this.apiClientFactory = apiClientFactory;
	}

	private GlpiRemoteAccess access(GlpiBrowsingContext context) throws GeboCryptSecretException {
		return new GlpiRemoteAccess(apiClientFactory.getApiClient(context.getSystemCode()));
	}

	private static <T> OperationStatus<T> failure(T partial, Throwable th, String content) {
		GUserMessage message = th instanceof HttpClientErrorException http
				? RestTemplateWrapperService.toMessage(http, GLPI, content)
				: GUserMessage.errorMessage("Error accessing GLPI " + content, th);
		OperationStatus<T> status = OperationStatus.of(partial);
		status.getMessages().clear();
		status.getMessages().add(message);
		return status;
	}

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(GlpiBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
		try {
			for (Entity entity : access(context).entities()) {
				roots.add(GlpiNavigationUtil.toRoot(entity.getId(), GlpiNativeObjects.entityName(entity)));
			}
			roots.sort(Comparator.comparing(GVirtualFilesystemRoot::getDescription,
					Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GLPI system " + context.getSystemCode() + " has " + roots.size() + " visible entities");
			}
		} catch (Throwable th) {
			LOGGER.error("Cannot list the entities of GLPI system " + context.getSystemCode(), th);
			return failure(roots, th, "entities list");
		}
		return OperationStatus.of(roots);
	}

	private static PathInfo child(PathInfo parent, GlpiPathNodeType type, Long id, String name) {
		return GlpiNavigationUtil.toPathInfo(parent, new GlpiPathComponent(type, id), name);
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, GlpiBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> paths = new ArrayList<PathInfo>();
		try {
			Long entityId = GlpiNavigationUtil.entityIdOf(param.root);
			if (entityId == null) {
				throw new VirtualFilesystemBrowsingException("Not a GLPI entity root: " + param.root);
			}
			if (param.path == null) {
				paths.add(child(null, GlpiPathNodeType.TICKETS_SECTION, null, GlpiNavigationUtil.TICKETS_SECTION_NAME));
				paths.add(child(null, GlpiPathNodeType.KB_SECTION, null, GlpiNavigationUtil.KB_SECTION_NAME));
				return OperationStatus.of(paths);
			}
			List<GlpiPathComponent> components = GlpiNavigationUtil.parsePath(param.path.absolutePath);
			if (components.isEmpty()) {
				return OperationStatus.of(paths);
			}
			GlpiPathComponent last = components.get(components.size() - 1);
			GlpiRemoteAccess access = access(context);
			switch (last.type) {
			case TICKETS_SECTION:
				for (Ticket ticket : access.tickets(entityId)) {
					paths.add(child(param.path, GlpiPathNodeType.TICKET, ticket.getId(),
							GlpiNativeObjects.ticketTitle(ticket)));
				}
				break;
			case KB_SECTION:
				for (KBArticle article : access.kbArticles(entityId)) {
					paths.add(child(param.path, GlpiPathNodeType.KB_ARTICLE, article.getId(),
							GlpiNativeObjects.kbArticleTitle(article) + GlpiNativeObjects.HTML_EXTENSION));
				}
				break;
			case TICKET: {
				final long ticketId = last.id;
				Ticket ticket = access.ticket(ticketId);
				if (ticket == null) {
					break;
				}
				paths.add(child(param.path, GlpiPathNodeType.TICKET_BODY, ticketId,
						GlpiNativeObjects.ticketBody(ticket, null).getName()));
				for (Followup followup : access.followups(ticketId)) {
					paths.add(child(param.path, GlpiPathNodeType.FOLLOWUP, followup.getId(),
							GlpiNativeObjects.followup(ticketId, followup, null).getName()));
				}
				for (TicketTask task : access.tasks(ticketId)) {
					paths.add(child(param.path, GlpiPathNodeType.TASK, task.getId(),
							GlpiNativeObjects.task(ticketId, task, null).getName()));
				}
				for (Solution solution : access.solutions(ticketId)) {
					paths.add(child(param.path, GlpiPathNodeType.SOLUTION, solution.getId(),
							GlpiNativeObjects.solution(ticketId, solution, null).getName()));
				}
				for (Document document : access.ticketDocuments(ticketId)) {
					paths.add(child(param.path, GlpiPathNodeType.DOCUMENT, document.getId(),
							GlpiNativeObjects.documentName(document)));
				}
				break;
			}
			default:
				// resources have no children
				break;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GLPI browse " + param.root.getCode() + " " + param.path.absolutePath + " => "
						+ paths.size() + " children");
			}
		} catch (VirtualFilesystemBrowsingException e) {
			throw e;
		} catch (Throwable th) {
			LOGGER.error("Cannot browse GLPI " + param, th);
			return failure(paths, th, "items list");
		}
		return OperationStatus.of(paths);
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return true;
	}

	/**
	 * The parent of a reference: the encoding is cumulative, so it is the same
	 * path without its last segment, the entity root (path null) above the
	 * sections
	 */
	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, GlpiBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		VFilesystemReference parent = new VFilesystemReference();
		parent.root = reference.root;
		if (reference.path == null) {
			return parent;
		}
		List<GlpiPathComponent> components = GlpiNavigationUtil.parsePath(reference.path.absolutePath);
		if (components.size() <= 1) {
			parent.path = null;
			return parent;
		}
		List<GlpiPathComponent> above = components.subList(0, components.size() - 1);
		PathInfo path = null;
		for (GlpiPathComponent component : above) {
			path = GlpiNavigationUtil.toPathInfo(path, component, null);
		}
		parent.path = path;
		return parent;
	}
}
