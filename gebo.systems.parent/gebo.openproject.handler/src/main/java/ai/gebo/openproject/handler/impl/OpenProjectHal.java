/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Null safe access to the untyped HAL resources of the OpenProject API v3:
 * dotted paths into the maps, the "_embedded.elements" of a collection, the
 * titles and the ids of the "_links".
 */
public final class OpenProjectHal {

	private OpenProjectHal() {
	}

	/** The value at a dotted path, e.g. "description.raw" or "_links.status.title". */
	public static Object value(Map<String, Object> resource, String path) {
		Object current = resource;
		for (String key : path.split("\\.")) {
			if (!(current instanceof Map<?, ?> map)) {
				return null;
			}
			current = map.get(key);
		}
		return current;
	}

	public static String string(Map<String, Object> resource, String path) {
		Object value = value(resource, path);
		return value != null ? value.toString() : null;
	}

	/** A string that is null when blank. */
	public static String text(Map<String, Object> resource, String path) {
		String value = string(resource, path);
		return value != null && !value.isBlank() ? value : null;
	}

	public static Integer integer(Map<String, Object> resource, String path) {
		Object value = value(resource, path);
		if (value instanceof Number n) {
			return n.intValue();
		}
		try {
			return value != null ? Integer.valueOf(value.toString()) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Long longValue(Map<String, Object> resource, String path) {
		Object value = value(resource, path);
		if (value instanceof Number n) {
			return n.longValue();
		}
		try {
			return value != null ? Long.valueOf(value.toString()) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean bool(Map<String, Object> resource, String path) {
		Object value = value(resource, path);
		return value instanceof Boolean b ? b : value != null && Boolean.parseBoolean(value.toString());
	}

	public static Date date(Map<String, Object> resource, String path) {
		String value = string(resource, path);
		if (value == null) {
			return null;
		}
		try {
			return Date.from(OffsetDateTime.parse(value).toInstant());
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> list(Map<String, Object> resource, String path) {
		Object value = value(resource, path);
		List<Map<String, Object>> out = new ArrayList<Map<String, Object>>();
		if (value instanceof List<?> list) {
			for (Object item : list) {
				if (item instanceof Map<?, ?> map) {
					out.add((Map<String, Object>) map);
				}
			}
		}
		return out;
	}

	public static List<Map<String, Object>> elements(Map<String, Object> collection) {
		return list(collection, "_embedded.elements");
	}

	/** The title of a link, e.g. linkTitle(wp, "status") for "_links.status.title". */
	public static String linkTitle(Map<String, Object> resource, String link) {
		return text(resource, "_links." + link + ".title");
	}

	/** The last segment of a link's href: the id of the linked resource. */
	public static String linkId(Map<String, Object> resource, String link) {
		String href = text(resource, "_links." + link + ".href");
		if (href == null) {
			return null;
		}
		int idx = href.lastIndexOf('/');
		return idx >= 0 ? href.substring(idx + 1) : href;
	}
}
