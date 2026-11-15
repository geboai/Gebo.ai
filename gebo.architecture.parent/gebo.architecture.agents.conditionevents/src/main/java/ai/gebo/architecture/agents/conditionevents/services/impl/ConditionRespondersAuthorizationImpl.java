/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.model.ConditionResponder;
import ai.gebo.architecture.agents.conditionevents.services.IConditionRespondersAuthorization;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;
import ai.gebo.architecture.agents.model.ConditionTarget;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityDirectory;

/**
 * Who may answer a condition. An administrator always may. A condition with a
 * target is answered only by who or what it lists: a user it lists or a member
 * of a group it lists, the source of the answer being that user, verified; a
 * system it lists, the answer delivered for that system by an account that
 * delivers for others. A condition with no target is answered as before by the
 * conversation's owner or by an account delivering for others, and now also by
 * anyone with access to the conversation.
 */
@Service
public class ConditionRespondersAuthorizationImpl implements IConditionRespondersAuthorization {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionRespondersAuthorizationImpl.class);
	static final String NOBODY = "Nobody is behind the answer";
	static final String NOT_TARGETED = "The condition is waited from %s only";
	static final String NO_ACCESS = "The answer is not from the conversation's owner, from anyone with access to the conversation, nor from an account delivering for others";
	private final ObjectProvider<IGSecurityDirectory> directory;

	public ConditionRespondersAuthorizationImpl(ObjectProvider<IGSecurityDirectory> directory) {
		this.directory = directory;
	}

	@Override
	public String refusal(ConditionTarget target, GUserChatSession session, ConditionResponder responder) {
		if (responder == null || (isBlank(responder.account()) && !responder.standsForOthers())) {
			return NOBODY;
		}
		if (responder.administrator()) {
			return null;
		}
		return target != null && target.isSet() ? targetRefusal(target, responder)
				: usualRefusal(session, responder);
	}

	/** Only who or what the target lists. */
	private String targetRefusal(ConditionTarget target, ConditionResponder responder) {
		final ConditionSource source = responder.source();
		final String person = personOf(responder);
		if (person != null && (target.listsUser(person) || target.listsAnyGroup(groupsOf(person)))) {
			return null;
		}
		// a system is spoken for only by an account that delivers for others
		if (responder.standsForOthers() && source != null && source.isVerified()
				&& target.listsSystem(source.getSystem())) {
			return null;
		}
		return String.format(NOT_TARGETED, target.label());
	}

	/** The owner, anyone with access to the conversation, an account delivering for others. */
	private String usualRefusal(GUserChatSession session, ConditionResponder responder) {
		if (responder.standsForOthers()) {
			return null;
		}
		final String account = responder.account().strip();
		if (session != null && (account.equals(session.getUsername()) || hasAccess(session, account))) {
			return null;
		}
		return NO_ACCESS;
	}

	/**
	 * The user the answer is from: its source, verified, when it names a person
	 * (or does not say what it names); the account itself when no source is
	 * known.
	 */
	private static String personOf(ConditionResponder responder) {
		final ConditionSource source = responder.source();
		if (source == null) {
			return isBlank(responder.account()) ? null : responder.account().strip();
		}
		if (!source.isVerified() || isBlank(source.getId())
				|| (source.getKind() != Kind.PERSON && source.getKind() != Kind.UNKNOWN)) {
			return null;
		}
		return source.getId().strip();
	}

	/** Whether the conversation is shared with the user: with everyone, with them, or with a group of theirs. */
	private boolean hasAccess(GUserChatSession session, String username) {
		if (Boolean.TRUE.equals(session.getAccessibleToAll())) {
			return true;
		}
		if (session.getAccessibleUsers() != null && session.getAccessibleUsers().contains(username)) {
			return true;
		}
		if (session.getAccessibleGroups() == null || session.getAccessibleGroups().isEmpty()) {
			return false;
		}
		for (String group : groupsOf(username)) {
			if (session.getAccessibleGroups().contains(group)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAdministrator(String username) {
		final IGSecurityDirectory users = directory != null ? directory.getIfAvailable() : null;
		if (users == null || isBlank(username)) {
			return false;
		}
		try {
			final UserInfos user = users.findUserByUsername(username.strip());
			return user != null && !Boolean.TRUE.equals(user.getDisabled()) && user.getRoles() != null
					&& user.getRoles().contains("ADMIN");
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the roles of " + username + ": not taken as an administrator", e);
			return false;
		}
	}

	/** The codes of the user's groups; none when the directory cannot tell. */
	private List<String> groupsOf(String username) {
		final List<String> codes = new ArrayList<String>();
		final IGSecurityDirectory users = directory != null ? directory.getIfAvailable() : null;
		if (users == null || isBlank(username)) {
			return codes;
		}
		try {
			final List<UsersGroup> groups = users.findGroupsOfUser(username);
			if (groups != null) {
				for (UsersGroup group : groups) {
					if (group != null && group.getCode() != null) {
						codes.add(group.getCode());
					}
				}
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the groups of " + username + ": they are not taken into account", e);
		}
		return codes;
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
