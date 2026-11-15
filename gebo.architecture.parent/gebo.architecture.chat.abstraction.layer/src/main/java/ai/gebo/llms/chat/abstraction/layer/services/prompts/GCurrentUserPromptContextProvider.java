/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.prompts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProvider;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.model.UserInfos;

/**
 * The person behind the request, for the prompts that name them:
 * {@code {currentUserName}} and {@code {currentUserGroups}}. Off unless the
 * installation turns it on ({@code ai.gebo.chat.prompts.user-context-enabled}):
 * what a prompt names goes to the model's provider, and a person's name and
 * groups are theirs to send or not.
 */
@Component
public class GCurrentUserPromptContextProvider implements IGPromptContextProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GCurrentUserPromptContextProvider.class);
	public static final String CURRENT_USER_NAME = "currentUserName";
	public static final String CURRENT_USER_GROUPS = "currentUserGroups";
	static final String NOBODY = "an unnamed user";

	private final IGSecurityService securityService;
	private final boolean enabled;

	public GCurrentUserPromptContextProvider(IGSecurityService securityService,
			@Value("${ai.gebo.chat.prompts.user-context-enabled:false}") boolean enabled) {
		this.securityService = securityService;
		this.enabled = enabled;
	}

	@Override
	public Set<String> getPlaceholders() {
		return Set.of(CURRENT_USER_NAME, CURRENT_USER_GROUPS);
	}

	@Override
	public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context) {
		final Map<String, Object> values = new HashMap<String, Object>();
		if (!enabled) {
			// the placeholders render, but say nothing of the person
			if (asked.contains(CURRENT_USER_NAME)) {
				values.put(CURRENT_USER_NAME, NOBODY);
			}
			if (asked.contains(CURRENT_USER_GROUPS)) {
				values.put(CURRENT_USER_GROUPS, "");
			}
			return values;
		}
		try {
			if (asked.contains(CURRENT_USER_NAME)) {
				final UserInfos user = securityService.getCurrentUser();
				values.put(CURRENT_USER_NAME, user != null ? nameOf(user) : NOBODY);
			}
			if (asked.contains(CURRENT_USER_GROUPS)) {
				final List<UsersGroup> groups = securityService.getCurrentUserGroups();
				final StringBuilder names = new StringBuilder();
				if (groups != null) {
					for (UsersGroup group : groups) {
						final String name = group.getDescription() != null && !group.getDescription().isBlank()
								? group.getDescription()
								: group.getCode();
						if (name != null) {
							names.append(names.length() > 0 ? ", " : "").append(name);
						}
					}
				}
				values.put(CURRENT_USER_GROUPS, names.toString());
			}
		} catch (RuntimeException notRead) {
			LOGGER.debug("The current user cannot be read for the prompt: nobody is named", notRead);
		}
		return values;
	}

	static String nameOf(UserInfos user) {
		final StringBuilder name = new StringBuilder();
		if (user.getName() != null && !user.getName().isBlank()) {
			name.append(user.getName().trim());
		}
		if (user.getSourname() != null && !user.getSourname().isBlank()) {
			name.append(name.length() > 0 ? " " : "").append(user.getSourname().trim());
		}
		if (name.length() == 0 && user.getUsername() != null) {
			name.append(user.getUsername());
		}
		return name.length() > 0 ? name.toString() : NOBODY;
	}
}
