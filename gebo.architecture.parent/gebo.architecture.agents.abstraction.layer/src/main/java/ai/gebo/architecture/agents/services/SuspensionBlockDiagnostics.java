/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Says what in a block of a suspended run the document mapping may not be able
 * to write: the values whose class is neither a simple value nor a collection or
 * map of them nor a class of the platform, walked from the block through its
 * fields. For the error that names no class when a block cannot be written.
 */
public final class SuspensionBlockDiagnostics {
	private static final int MAX_FINDINGS = 12;
	private static final int MAX_DEPTH = 10;

	private SuspensionBlockDiagnostics() {
	}

	/** The paths of the values that may not be mappable, with their classes; empty when none is found. */
	public static List<String> foreignValues(Object root) {
		final List<String> findings = new ArrayList<String>();
		walk(root, "block", 0, java.util.Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>()), findings);
		return findings;
	}

	private static void walk(Object value, String path, int depth, Set<Object> seen, List<String> findings) {
		if (value == null || findings.size() >= MAX_FINDINGS || depth > MAX_DEPTH || isSimple(value)) {
			return;
		}
		if (!seen.add(value)) {
			return;
		}
		if (value instanceof Collection<?> collection) {
			int i = 0;
			for (Object item : collection) {
				walk(item, path + "[" + i++ + "]", depth + 1, seen, findings);
			}
			return;
		}
		if (value instanceof Map<?, ?> map) {
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				walk(entry.getValue(), path + "[" + entry.getKey() + "]", depth + 1, seen, findings);
			}
			return;
		}
		if (value.getClass().isArray()) {
			if (value instanceof Object[] items) {
				int i = 0;
				for (Object item : items) {
					walk(item, path + "[" + i++ + "]", depth + 1, seen, findings);
				}
			}
			return;
		}
		final Class<?> type = value.getClass();
		final boolean platform = type.getName().startsWith("ai.gebo.");
		if (!platform || !Modifier.isPublic(type.getModifiers())) {
			findings.add(path + " : " + type.getName() + (platform ? " (not public)" : ""));
			return;
		}
		for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
					continue;
				}
				try {
					field.setAccessible(true);
					walk(field.get(value), path + "." + field.getName(), depth + 1, seen, findings);
				} catch (ReflectiveOperationException | RuntimeException unreadable) {
					findings.add(path + "." + field.getName() + " : unreadable " + unreadable.getClass().getSimpleName());
				}
			}
		}
	}

	private static boolean isSimple(Object value) {
		return value instanceof String || value instanceof Number || value instanceof Boolean
				|| value instanceof Character || value instanceof Date || value instanceof Enum<?>
				|| value instanceof java.util.UUID || value instanceof java.time.temporal.Temporal;
	}
}
