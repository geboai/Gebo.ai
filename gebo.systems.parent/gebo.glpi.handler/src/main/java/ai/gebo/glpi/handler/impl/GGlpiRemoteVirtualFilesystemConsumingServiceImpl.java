/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Entity;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.client.model.TicketTask;
import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.glpi.handler.GlpiResourceReference;
import ai.gebo.glpi.handler.IGGlpiVirtualFilesystemConsumingService;
import ai.gebo.glpi.handler.model.GlpiNativePositionObject;
import ai.gebo.glpi.handler.model.GlpiNavigationCoordinates;
import ai.gebo.glpi.handler.model.GlpiPathComponent;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * Walks a GLPI system for the ingestion: entity → Tickets / Knowledge base →
 * ticket folder (body, follow-ups, tasks, solutions, documents) / article.
 */
public class GGlpiRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GGlpiSystem, GGlpiProjectEndpoint, GlpiNativePositionObject, GlpiNavigationCoordinates, GlpiResourceReference>
		implements IGGlpiVirtualFilesystemConsumingService {
	static final String GLPI_ACCESS = "GLPI-ACCESS";
	static final String GLPI_ROOT_URL = "GLPI-ROOT-URL";
	/** Tickets read by a listing, kept until their folder is walked */
	static final String GLPI_LISTED_TICKETS = "GLPI-LISTED-TICKETS";
	/** Tickets state read by the end-of-job existence check */
	static final String GLPI_CHECKED_TICKETS = "GLPI-CHECKED-TICKETS";

	protected final GlpiApiClientFactory apiClientFactory;

	public GGlpiRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			GlpiApiClientFactory apiClientFactory) {
		super(documentFactory);
		this.apiClientFactory = apiClientFactory;
	}

	static GlpiRemoteAccess access(Map<String, Object> environment) {
		return (GlpiRemoteAccess) environment.get(GLPI_ACCESS);
	}

	static String rootUrl(Map<String, Object> environment) {
		return (String) environment.get(GLPI_ROOT_URL);
	}

	@SuppressWarnings("unchecked")
	private static Map<Long, Ticket> listedTickets(Map<String, Object> environment) {
		return (Map<Long, Ticket>) environment.computeIfAbsent(GLPI_LISTED_TICKETS, k -> new HashMap<Long, Ticket>());
	}

	@SuppressWarnings("unchecked")
	private static Map<Long, Ticket> checkedTickets(Map<String, Object> environment) {
		return (Map<Long, Ticket>) environment.computeIfAbsent(GLPI_CHECKED_TICKETS, k -> new HashMap<Long, Ticket>());
	}

	@Override
	protected Map<String, Object> createEnvironment(GGlpiSystem system, GGlpiProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		return createEnvironment(system);
	}

	@Override
	protected Map<String, Object> createEnvironment(GGlpiSystem system) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(GLPI_ACCESS, new GlpiRemoteAccess(apiClientFactory.getApiClient(system)));
			environment.put(GLPI_ROOT_URL, GlpiApiClientFactory.rootUrlOf(system.getBaseUri()));
		} catch (GeboCryptSecretException | RuntimeException e) {
			LOGGER.error("Cannot allocate the GLPI client of system " + system.getCode(), e);
			throw new GeboContentHandlerSystemException("Cannot access GLPI: " + e.getMessage(), e);
		}
		return environment;
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GGlpiSystem system,
			GGlpiProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		if (environment != null) {
			environment.clear();
		}
	}

	@Override
	protected GlpiNavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		GlpiNavigationCoordinates coordinates = GlpiNavigationUtil.toNavigationCoordinates(path);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI selected path " + path + " decoded as " + coordinates);
		}
		return coordinates;
	}

	@Override
	protected List<GlpiNativePositionObject> toNativeCoordinates(GlpiNavigationCoordinates position,
			GGlpiSystem system, GGlpiProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RuntimeException e) {
			LOGGER.error("Cannot load the GLPI item " + position, e);
			errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, position.toString()));
			throw new GeboContentHandlerSystemException("Cannot load the GLPI item " + position, e);
		}
	}

	@Override
	protected List<GlpiNativePositionObject> toResourcesNativeCoordinates(GlpiNavigationCoordinates position,
			GGlpiSystem system, Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return resolve(position, environment);
		} catch (RuntimeException e) {
			LOGGER.error("Cannot load the GLPI item " + position, e);
			throw new GeboContentHandlerSystemException("Cannot load the GLPI item " + position, e);
		}
	}

	/**
	 * The native path of coordinates, root entity first: empty when the entity
	 * or any item of the path no longer exists
	 */
	List<GlpiNativePositionObject> resolve(GlpiNavigationCoordinates position, Map<String, Object> environment) {
		List<GlpiNativePositionObject> natives = new ArrayList<GlpiNativePositionObject>();
		final GlpiRemoteAccess access = access(environment);
		final String rootUrl = rootUrl(environment);
		final Long entityId = GlpiNavigationUtil.entityIdOf(position.getRoot());
		if (entityId == null) {
			LOGGER.error("The GLPI root " + (position.getRoot() != null ? position.getRoot().getCode() : null)
					+ " is not an entity");
			return natives;
		}
		Entity entity = access.entity(entityId);
		if (entity == null) {
			LOGGER.warn("The GLPI entity " + entityId + " no longer exists or is no longer visible");
			return natives;
		}
		GlpiNativePositionObject entityNode = GlpiNativeObjects.entity(entity, rootUrl);
		PathInfo entityPath = new PathInfo();
		entityPath.folder = true;
		entityPath.name = entityNode.getName();
		entityPath.metaType = GlpiNavigationUtil.metaTypeOf(GlpiPathNodeType.ENTITY);
		entityNode.setPath(entityPath);
		natives.add(entityNode);
		Ticket ticket = null;
		List<GlpiPathComponent> steps = position.getBrowsingStepsCustom();
		for (int i = 0; i < steps.size(); i++) {
			GlpiPathComponent step = steps.get(i);
			GlpiNativePositionObject node = null;
			switch (step.type) {
			case TICKETS_SECTION:
				node = GlpiNativeObjects.ticketsSection(entityId);
				break;
			case KB_SECTION:
				node = GlpiNativeObjects.kbSection(entityId);
				break;
			case TICKET:
				ticket = access.ticket(step.id);
				node = ticket != null && !Boolean.TRUE.equals(ticket.getIsDeleted())
						? GlpiNativeObjects.ticketFolder(ticket, rootUrl)
						: null;
				break;
			case TICKET_BODY: {
				Ticket body = ticket != null && Objects.equals(ticket.getId(), step.id) ? ticket : access.ticket(step.id);
				node = body != null ? GlpiNativeObjects.ticketBody(body, rootUrl) : null;
				break;
			}
			case FOLLOWUP: {
				Long ticketId = ticketIdOf(steps, i);
				Followup followup = ticketId != null ? access.followup(ticketId, step.id) : null;
				node = followup != null ? GlpiNativeObjects.followup(ticketId, followup, rootUrl) : null;
				break;
			}
			case TASK: {
				Long ticketId = ticketIdOf(steps, i);
				TicketTask task = ticketId != null ? access.task(ticketId, step.id) : null;
				node = task != null ? GlpiNativeObjects.task(ticketId, task, rootUrl) : null;
				break;
			}
			case SOLUTION: {
				Long ticketId = ticketIdOf(steps, i);
				Solution solution = ticketId != null ? access.solution(ticketId, step.id) : null;
				node = solution != null ? GlpiNativeObjects.solution(ticketId, solution, rootUrl) : null;
				break;
			}
			case DOCUMENT: {
				Long ticketId = ticketIdOf(steps, i);
				Document document = ticketId != null ? access.ticketDocument(ticketId, step.id) : null;
				node = document != null ? GlpiNativeObjects.document(ticketId, document, rootUrl) : null;
				break;
			}
			case KB_ARTICLE: {
				KBArticle article = access.kbArticle(step.id);
				node = article != null ? GlpiNativeObjects.kbArticle(article, rootUrl) : null;
				break;
			}
			default:
				break;
			}
			if (node == null) {
				LOGGER.warn("The GLPI item " + step + " of " + position + " no longer exists or is no longer visible");
				return new ArrayList<GlpiNativePositionObject>();
			}
			if (ticket != null && step.type != GlpiPathNodeType.TICKET) {
				node.meta(GlpiNativePositionObject.GLPI_TICKET_NAME, ticket.getName());
			}
			node.setPath(position.getBrowsingSteps().get(i));
			natives.add(node);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("GLPI native path of " + position + " => " + natives);
		}
		return natives;
	}

	/** The id of the ticket a timeline step belongs to: the nearest TICKET step above it */
	private static Long ticketIdOf(List<GlpiPathComponent> steps, int index) {
		for (int i = index - 1; i >= 0; i--) {
			if (steps.get(i).type == GlpiPathNodeType.TICKET) {
				return steps.get(i).id;
			}
		}
		return null;
	}

	private NativeCoordinatePointer pointer(List<GlpiNativePositionObject> parent, GlpiNativePositionObject child) {
		NativeCoordinatePointer pointer = new NativeCoordinatePointer();
		pointer.parentCoordinates = new ArrayList<GlpiNativePositionObject>(parent);
		pointer.child = child;
		return pointer;
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<GlpiNativePositionObject> nativeCoordinates,
			GlpiNavigationCoordinates position, GGlpiSystem system, GGlpiProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		if (nativeCoordinates.isEmpty()) {
			return childs;
		}
		final GlpiRemoteAccess access = access(environment);
		final String rootUrl = rootUrl(environment);
		final GlpiNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		final Long entityId = nativeCoordinates.get(0).getId();
		switch (last.getType()) {
		case ENTITY:
			childs.add(pointer(nativeCoordinates, GlpiNativeObjects.ticketsSection(entityId)));
			childs.add(pointer(nativeCoordinates, GlpiNativeObjects.kbSection(entityId)));
			break;
		case TICKETS_SECTION: {
			Map<Long, Ticket> listed = listedTickets(environment);
			for (Ticket ticket : access.tickets(entityId)) {
				listed.put(ticket.getId(), ticket);
				childs.add(pointer(nativeCoordinates, GlpiNativeObjects.ticketFolder(ticket, rootUrl)));
			}
			break;
		}
		case KB_SECTION:
			for (KBArticle article : access.kbArticles(entityId)) {
				childs.add(pointer(nativeCoordinates, GlpiNativeObjects.kbArticle(article, rootUrl)));
			}
			break;
		case TICKET: {
			final long ticketId = last.getId();
			Ticket ticket = listedTickets(environment).remove(ticketId);
			if (ticket == null) {
				ticket = access.ticket(ticketId);
			}
			if (ticket == null) {
				LOGGER.warn("The GLPI ticket " + ticketId + " no longer exists or is no longer visible");
				break;
			}
			List<GlpiNativePositionObject> items = new ArrayList<GlpiNativePositionObject>();
			items.add(GlpiNativeObjects.ticketBody(ticket, rootUrl));
			for (Followup followup : access.followups(ticketId)) {
				items.add(GlpiNativeObjects.followup(ticketId, followup, rootUrl));
			}
			for (TicketTask task : access.tasks(ticketId)) {
				items.add(GlpiNativeObjects.task(ticketId, task, rootUrl));
			}
			for (Solution solution : access.solutions(ticketId)) {
				items.add(GlpiNativeObjects.solution(ticketId, solution, rootUrl));
			}
			for (Document document : access.ticketDocuments(ticketId)) {
				items.add(GlpiNativeObjects.document(ticketId, document, rootUrl));
			}
			for (GlpiNativePositionObject item : items) {
				item.meta(GlpiNativePositionObject.GLPI_TICKET_NAME, ticket.getName());
				childs.add(pointer(nativeCoordinates, item));
			}
			break;
		}
		default:
			// resources have no children
			break;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI " + last.getType() + " " + last.getCode() + " has " + childs.size() + " children");
		}
		return childs;
	}

	@Override
	protected GlpiNavigationCoordinates getPositionCoordinate(List<GlpiNativePositionObject> childCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return GlpiNavigationUtil.toNavigationCoordinates(childCoordinates);
	}

	private static String string(Map<String, Object> metainfos, String key) {
		Object value = metainfos.get(key);
		return value != null ? value.toString() : null;
	}

	static GlpiResourceReference toResourceReference(Map<String, Object> metainfos) {
		GlpiResourceReference reference = new GlpiResourceReference();
		String type = string(metainfos, GlpiNativePositionObject.GLPI_OBJECT_TYPE);
		reference.objectType = type != null ? GlpiPathNodeType.valueOf(type) : null;
		reference.entityId = GlpiNativePositionObject.asLong(metainfos.get(GlpiNativePositionObject.GLPI_ENTITY_ID));
		reference.ticketId = GlpiNativePositionObject.asLong(metainfos.get(GlpiNativePositionObject.GLPI_TICKET_ID));
		reference.itemId = GlpiNativePositionObject.asLong(metainfos.get(GlpiNativePositionObject.GLPI_ITEM_ID));
		reference.ticketName = string(metainfos, GlpiNativePositionObject.GLPI_TICKET_NAME);
		reference.documentFileName = string(metainfos, GlpiNativePositionObject.GLPI_DOCUMENT_FILENAME);
		reference.documentMimeType = string(metainfos, GlpiNativePositionObject.GLPI_DOCUMENT_MIME);
		return reference;
	}

	@Override
	public GlpiResourceReference getResourceHandle(GGlpiSystem system, GGlpiProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		Map<String, Object> metainfos = reference.getCustomMetaInfos();
		if (metainfos == null) {
			throw new GeboContentHandlerSystemException("customMetaInfos not present in " + reference.getCode());
		}
		GlpiResourceReference handle = toResourceReference(metainfos);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("GLPI handle of " + reference.getCode() + " => " + handle);
		}
		return handle;
	}

	@Override
	protected GlpiResourceReference getResourceHandle(SearchableSystemMetaData system,
			GlpiNavigationCoordinates navigationPosition, List<GlpiNativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (nativeCoordinates.isEmpty()) {
			return null;
		}
		return toResourceReference(nativeCoordinates.get(nativeCoordinates.size() - 1).getResourceReferenceMetaInfos());
	}

	@Override
	public InputStream streamResource(GGlpiSystem system, GGlpiProjectEndpoint endpoint,
			GlpiResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		return streamResource(system, reference, cache);
	}

	@Override
	protected InputStream streamResource(GGlpiSystem system, GlpiResourceReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException, IOException {
		if (!cache.containsKey(GLPI_ACCESS)) {
			cache.putAll(createEnvironment(system));
		}
		if (reference.objectType == null || reference.objectType.isFolder() || reference.itemId == null) {
			throw new GeboContentHandlerSystemException("The GLPI object " + reference + " is not streamable");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Streaming GLPI " + reference);
		}
		final GlpiRemoteAccess access = access(cache);
		final long id = reference.itemId;
		InputStream stream = null;
		switch (reference.objectType) {
		case TICKET_BODY: {
			Ticket ticket = access.ticket(id);
			stream = ticket != null ? GlpiContentRenderer.ticket(ticket) : null;
			break;
		}
		case FOLLOWUP: {
			Followup followup = reference.ticketId != null ? access.followup(reference.ticketId, id) : null;
			stream = followup != null ? GlpiContentRenderer.followup(reference.ticketName, reference.ticketId, followup)
					: null;
			break;
		}
		case TASK: {
			TicketTask task = reference.ticketId != null ? access.task(reference.ticketId, id) : null;
			stream = task != null ? GlpiContentRenderer.task(reference.ticketName, reference.ticketId, task) : null;
			break;
		}
		case SOLUTION: {
			Solution solution = reference.ticketId != null ? access.solution(reference.ticketId, id) : null;
			stream = solution != null ? GlpiContentRenderer.solution(reference.ticketName, reference.ticketId, solution)
					: null;
			break;
		}
		case DOCUMENT:
			stream = access.download(id);
			break;
		case KB_ARTICLE: {
			KBArticle article = access.kbArticle(id);
			stream = article != null ? GlpiContentRenderer.kbArticle(article) : null;
			break;
		}
		default:
			break;
		}
		if (stream == null) {
			throw new GeboContentHandlerSystemException("The GLPI object " + reference + " no longer exists");
		}
		return stream;
	}

	/**
	 * The ticket as GLPI has it now, read once per check: null when it no longer
	 * exists
	 */
	private static Ticket checkedTicket(long ticketId, Map<String, Object> environment) {
		Map<Long, Ticket> checked = checkedTickets(environment);
		if (checked.containsKey(ticketId)) {
			return checked.get(ticketId);
		}
		Ticket ticket = access(environment).ticket(ticketId);
		checked.put(ticketId, ticket);
		return ticket;
	}

	/** True when a ticket is gone: deleted, in the trash, or moved to another entity */
	private static boolean ticketGone(Ticket ticket, Long entityId) {
		return ticket == null || Boolean.TRUE.equals(ticket.getIsDeleted()) || movedFrom(entityId,
				ticket.getEntity() != null ? ticket.getEntity().getId() : null);
	}

	private static boolean movedFrom(Long knownEntityId, Long actualEntityId) {
		return knownEntityId != null && actualEntityId != null && !knownEntityId.equals(actualEntityId);
	}

	/**
	 * Called at the end of a job for the items the job did not meet: a GLPI item
	 * is gone when GLPI answers 404 on it, when its ticket is gone, or when it
	 * moved to another entity (it is then read under that entity, if selected).
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GGlpiSystem system,
			GGlpiProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, GlpiResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		if (reference == null || reference.objectType == null) {
			return null;
		}
		try {
			final GlpiRemoteAccess access = access(environment);
			boolean gone;
			switch (reference.objectType) {
			case ENTITY:
			case TICKETS_SECTION:
			case KB_SECTION:
				gone = reference.entityId != null && access.entity(reference.entityId) == null;
				break;
			case TICKET:
			case TICKET_BODY:
				if (reference.ticketId == null) {
					return null;
				}
				gone = ticketGone(checkedTicket(reference.ticketId, environment), reference.entityId);
				break;
			case FOLLOWUP:
			case TASK:
			case SOLUTION:
			case DOCUMENT: {
				if (reference.ticketId == null || reference.itemId == null) {
					return null;
				}
				gone = ticketGone(checkedTicket(reference.ticketId, environment), reference.entityId);
				if (!gone) {
					Object item = switch (reference.objectType) {
					case FOLLOWUP -> access.followup(reference.ticketId, reference.itemId);
					case TASK -> access.task(reference.ticketId, reference.itemId);
					case SOLUTION -> access.solution(reference.ticketId, reference.itemId);
					default -> access.ticketDocument(reference.ticketId, reference.itemId);
					};
					gone = item == null;
				}
				break;
			}
			case KB_ARTICLE: {
				if (reference.itemId == null) {
					return null;
				}
				KBArticle article = access.kbArticle(reference.itemId);
				gone = article == null || movedFrom(reference.entityId,
						article.getEntity() != null ? article.getEntity().getId() : null);
				break;
			}
			default:
				return null;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GLPI existence check of " + doc.getCode() + " (" + reference + "): "
						+ (gone ? "gone" : "present"));
			}
			if (gone) {
				doc.setDeleted(true);
			}
			return doc;
		} catch (Throwable th) {
			// No information: the item is left as it is, the check goes on with the others
			LOGGER.error("Cannot check on GLPI the existence of " + doc.getCode(), th);
			return null;
		}
	}

	@Override
	protected String describeObject(List<GlpiNativePositionObject> references, GGlpiSystem system,
			GGlpiProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references.isEmpty()) {
			return "<<Incoherent hierarchy>>";
		}
		GlpiNativePositionObject last = references.get(references.size() - 1);
		return "GLPI " + last.getType().name().toLowerCase().replace('_', ' ') + " " + last.getName() + " ("
				+ last.getCode() + ")";
	}

	@Override
	protected String describeSystem(GGlpiSystem system) {
		return "GLPI " + system.getDescription();
	}

	@Override
	protected String describeProjectEndpoint(GGlpiSystem system, GGlpiProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "GLPI data source " + endpoint.getDescription();
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GLPI_MODULE;
	}
}
