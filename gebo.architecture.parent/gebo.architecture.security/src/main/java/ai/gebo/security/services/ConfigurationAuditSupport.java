/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;

/*****************************************************************************
 * Writes the security events of the configuration changes that decide what the AI
 * may do and for whom - agents and networks of agents, MCP and A2A, chat profiles
 * and knowledge bases, external search - to the security audit trail, the same way
 * for every admin endpoint.
 *
 * <p>
 * Every method takes a {@link SecurityEvent} the CALLER has already created with
 * {@link IGSecurityAuditLoggerService#newSecurityEvent()}: the event captures its
 * caller stack when it is created, so creating it here would make every
 * configuration event point at this helper instead of at the endpoint that was
 * called (the same reason the other audit helpers take their event as a
 * parameter).
 * </p>
 *
 * <p>
 * Only metadata is written: the action, the kind and code of the configuration,
 * the outcome and, for the objects with access settings, who may use them - the
 * groups by code and the users only as a count, since user ids are e-mail
 * addresses. Secrets, tokens, prompts and contents never go into an event.
 * </p>
 */
public final class ConfigurationAuditSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationAuditSupport.class);

	public static final String DETAIL_ACCESSIBLE_TO_ALL = "accessibleToAll";
	public static final String DETAIL_ACCESSIBLE_GROUPS = "accessibleGroups";
	public static final String DETAIL_ACCESSIBLE_USERS_COUNT = "accessibleUsersCount";
	public static final String DETAIL_ERROR = "error";
	public static final String DETAIL_MESSAGES_COUNT = "messagesCount";

	/**
	 * A configuration change, with the checked exception it may throw.
	 */
	@FunctionalInterface
	public static interface AuditedOperation<T, E extends Exception> {
		T run() throws E;
	}

	/**
	 * A configuration change with no result, with the checked exception it may throw.
	 */
	@FunctionalInterface
	public static interface AuditedVoidOperation<E extends Exception> {
		void run() throws E;
	}

	/**
	 * Adds what only the result of a successful change tells (e.g. the code of a copy)
	 * to the event, before it is written.
	 */
	@FunctionalInterface
	public static interface ResultDescriber<T> {
		void describe(SecurityEvent event, T result);
	}

	private ConfigurationAuditSupport() {
	}

	/**
	 * Runs the change and writes its event: success when it returns, failure when it
	 * returns an {@link OperationStatus} carrying error messages or when it throws -
	 * the exception is then rethrown unchanged, the audit never alters what the caller
	 * gets.
	 *
	 * @param auditLogger  the security audit logger
	 * @param event        the event the endpoint created on entry
	 * @param eventType    one of the configuration event types of
	 *                     {@link SecurityAuditTaxonomy.EventType}
	 * @param action       the {@link SecurityAuditTaxonomy.Action}
	 * @param resourceType the kind of configuration (e.g. "agentsNetwork")
	 * @param resourceId   the code of the configuration
	 * @param subject      the configuration as sent, whose access settings are
	 *                     described; may be null
	 * @param operation    the change
	 */
	public static <T, E extends Exception> T audited(IGSecurityAuditLoggerService auditLogger, SecurityEvent event,
			String eventType, String action, String resourceType, String resourceId, Object subject,
			AuditedOperation<T, E> operation) throws E {
		return audited(auditLogger, event, eventType, action, resourceType, resourceId, subject, operation, null);
	}

	/**
	 * As {@link #audited(IGSecurityAuditLoggerService, SecurityEvent, String, String, String, String, Object, AuditedOperation)},
	 * letting the result of a successful change add its own details.
	 */
	public static <T, E extends Exception> T audited(IGSecurityAuditLoggerService auditLogger, SecurityEvent event,
			String eventType, String action, String resourceType, String resourceId, Object subject,
			AuditedOperation<T, E> operation, ResultDescriber<T> resultDescriber) throws E {
		describeAccess(event, subject);
		final T result;
		try {
			result = operation.run();
		} catch (Throwable th) {
			// the message only: a stack trace would make the line unreadable for a SIEM,
			// and the application log already has it
			event.getDetails().put(DETAIL_ERROR, th.getMessage());
			log(auditLogger, event, eventType, action, resourceType, resourceId, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw ConfigurationAuditSupport.<RuntimeException>rethrow(th);
		}
		String outcome = SecurityAuditTaxonomy.Outcome.SUCCESS;
		if (result instanceof OperationStatus<?> status) {
			if (status.getMessages() != null) {
				event.getDetails().put(DETAIL_MESSAGES_COUNT, status.getMessages().size());
			}
			if (status.isHasErrorMessages()) {
				outcome = SecurityAuditTaxonomy.Outcome.FAILURE;
			}
		}
		if (resultDescriber != null && SecurityAuditTaxonomy.Outcome.SUCCESS.equals(outcome)) {
			try {
				resultDescriber.describe(event, result);
			} catch (RuntimeException e) {
				// describing the result is a nicety: it must never fail a change that worked
				LOGGER.warn("Could not describe the result of " + action + " on " + resourceType + ":" + resourceId
						+ " in its security event", e);
			}
		}
		// a code generated on insert is known only from what was saved
		log(auditLogger, event, eventType, action, resourceType, resourceId != null ? resourceId : codeOf(result),
				outcome);
		return result;
	}

	/**
	 * The code of the configuration a change returned, directly or inside an
	 * {@link OperationStatus}; null when it returned none.
	 */
	static String codeOf(Object result) {
		Object value = result instanceof OperationStatus<?> status ? status.getResult() : result;
		return value instanceof GBaseObject object ? object.getCode() : null;
	}

	/**
	 * As {@link #audited(IGSecurityAuditLoggerService, SecurityEvent, String, String, String, String, Object, AuditedOperation)}
	 * for a change with no result.
	 */
	public static <E extends Exception> void auditedVoid(IGSecurityAuditLoggerService auditLogger, SecurityEvent event,
			String eventType, String action, String resourceType, String resourceId, Object subject,
			AuditedVoidOperation<E> operation) throws E {
		ConfigurationAuditSupport.<Object, E>audited(auditLogger, event, eventType, action, resourceType, resourceId,
				subject, () -> {
					operation.run();
					return null;
				});
	}

	/**
	 * Writes the event of a configuration change whose outcome the caller decided.
	 */
	public static void log(IGSecurityAuditLoggerService auditLogger, SecurityEvent event, String eventType,
			String action, String resourceType, String resourceId, String outcome) {
		event.setEventType(eventType);
		// the category of every configuration event type is the event type itself, as
		// for the other event types of the taxonomy
		event.setCategory(eventType);
		event.setAction(action);
		event.setResourceType(resourceType);
		event.setResourceId(resourceId);
		event.setOutcome(outcome);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Security event " + eventType + "/" + action + " on " + resourceType + ":" + resourceId
					+ " outcome:" + outcome);
		}
		auditLogger.log(event);
	}

	/**
	 * Describes who may use the configuration, when it has access settings: whether
	 * everyone may, the groups by code, the users only as a count (their ids are
	 * e-mail addresses, personal data a security log has no need for).
	 */
	public static void describeAccess(SecurityEvent event, Object subject) {
		if (!(subject instanceof IGObjectWithSecurity secured)) {
			return;
		}
		event.getDetails().put(DETAIL_ACCESSIBLE_TO_ALL, secured.getAccessibleToAll());
		List<String> groups = secured.getAccessibleGroups();
		event.getDetails().put(DETAIL_ACCESSIBLE_GROUPS, groups != null ? new ArrayList<>(groups) : List.of());
		List<String> users = secured.getAccessibleUsers();
		event.getDetails().put(DETAIL_ACCESSIBLE_USERS_COUNT, users != null ? users.size() : 0);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Access of the audited configuration: toAll:" + secured.getAccessibleToAll() + " groups:"
					+ groups + " users:" + (users != null ? users.size() : 0));
		}
	}

	/**
	 * Throws the given throwable as it is, checked or not: the operation declared it,
	 * so the caller of {@link #audited} - which declares the same exception type -
	 * receives exactly what the operation threw.
	 */
	@SuppressWarnings("unchecked")
	private static <X extends Throwable> RuntimeException rethrow(Throwable th) throws X {
		throw (X) th;
	}
}
