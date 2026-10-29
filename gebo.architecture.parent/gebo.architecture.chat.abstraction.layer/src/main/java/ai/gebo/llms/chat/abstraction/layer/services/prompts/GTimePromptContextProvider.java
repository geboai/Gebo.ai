/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.prompts;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProvider;

/**
 * The time, for the prompts that name it: {@code {actualTimestamp}} as the
 * pipelines always gave it, {@code {currentDate}}, {@code {currentTime}} and
 * {@code {timeZone}} of the server.
 */
@Component
public class GTimePromptContextProvider implements IGPromptContextProvider {
	public static final String ACTUAL_TIMESTAMP = "actualTimestamp";
	public static final String CURRENT_DATE = "currentDate";
	public static final String CURRENT_TIME = "currentTime";
	public static final String TIME_ZONE = "timeZone";

	@Override
	public Set<String> getPlaceholders() {
		return Set.of(ACTUAL_TIMESTAMP, CURRENT_DATE, CURRENT_TIME, TIME_ZONE);
	}

	@Override
	public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context) {
		final Map<String, Object> values = new HashMap<String, Object>();
		final ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
		if (asked.contains(ACTUAL_TIMESTAMP)) {
			values.put(ACTUAL_TIMESTAMP, new Timestamp(System.currentTimeMillis()).toString());
		}
		if (asked.contains(CURRENT_DATE)) {
			values.put(CURRENT_DATE, now.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
		if (asked.contains(CURRENT_TIME)) {
			values.put(CURRENT_TIME, now.format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		if (asked.contains(TIME_ZONE)) {
			values.put(TIME_ZONE, now.getZone().getId());
		}
		return values;
	}
}
