/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import ai.gebo.architecture.ai.model.ITokensCountable;

/**
 * The files the tools of a conversation wrote, as the agents read them: the
 * latest first, each with the request that last wrote it and its outline as
 * the file is now - so never stale - within a budget of tokens. A file changed
 * since by someone else says so; a file deleted since is left out; a file whose
 * outline finds no room is still named, so the agents always know it exists.
 */
public final class GConversationFilesText {
	/** Below this many tokens left, a file is only named. */
	static final int MIN_OUTLINE_TOKENS = 64;
	static final String HEADING = "Files the tools of this conversation wrote, the latest first. Each outline shows the file as it is now: "
			+ "read or change it with the library tools, by its location and path.";

	/** A file written in a request of the conversation, by the request's index, 0 the first. */
	public record Entry(int request, GConversationFile file) {
	}

	private GConversationFilesText() {
	}

	/**
	 * The text of the files written in a conversation, or null when none is
	 * left (none written, or all deleted since).
	 *
	 * @param entries          the files written, by request; a file written in
	 *                         several requests is shown once, at the last
	 * @param currentRequest   the index of the request being answered: its
	 *                         files are said written "in this request"
	 * @param locations        the files locations of the conversation by id; null
	 *                         for one it no longer holds
	 * @param outliners        the outliners of the files' kinds
	 * @param maxTokensPerFile the most tokens an outline takes
	 * @param maxTokens        the most tokens the whole text takes, but for the
	 *                         names of the files left without an outline
	 */
	public static String render(List<Entry> entries, int currentRequest, Function<String, IGFilesLocation> locations,
			List<? extends IGFilesOutliner> outliners, int maxTokensPerFile, int maxTokens) {
		if (entries == null || entries.isEmpty()) {
			return null;
		}
		final StringBuilder text = new StringBuilder(HEADING);
		int left = maxTokens - ITokensCountable.stringsTokensSize(HEADING);
		final List<String> onlyNamed = new ArrayList<>();
		boolean any = false;
		for (Entry entry : latestFirst(entries)) {
			final GConversationFile file = entry.file();
			final IGFilesLocation location = locations != null ? locations.apply(file.getLocation()) : null;
			if (location == null) {
				// the conversation no longer holds that location: nothing to read there
				continue;
			}
			final GLibraryFile now;
			try {
				now = location.describe(file.getPath());
			} catch (NoSuchFileException deleted) {
				continue;
			} catch (IOException | RuntimeException unreadable) {
				onlyNamed.add(name(file) + " (written " + when(entry, currentRequest) + ", not readable now: "
						+ unreadable.getMessage() + ")");
				any = true;
				continue;
			}
			if (now == null) {
				continue;
			}
			any = true;
			final String line = "\n- " + name(file) + " - written " + when(entry, currentRequest) + ", "
					+ size(now.size()) + (changed(file, now) ? "; changed since by something else than the tools" : "");
			final String outline = left - ITokensCountable.stringsTokensSize(line) >= MIN_OUTLINE_TOKENS
					? outline(outliners, location, file.getPath(),
							Math.min(maxTokensPerFile, left - ITokensCountable.stringsTokensSize(line)))
					: null;
			final String shown = outline != null ? line + "\n" + indented(outline) : line;
			final int cost = ITokensCountable.stringsTokensSize(shown);
			if (cost <= left) {
				text.append(shown);
				left -= cost;
			} else {
				onlyNamed.add(name(file) + " (written " + when(entry, currentRequest) + ")");
			}
		}
		if (!any) {
			return null;
		}
		if (!onlyNamed.isEmpty()) {
			text.append("\nAlso written, without an outline here: ").append(String.join(", ", onlyNamed));
		}
		return text.toString();
	}

	/** Each file once, at the last request that wrote it; the latest requests first. */
	static List<Entry> latestFirst(List<Entry> entries) {
		final Map<String, Entry> last = new LinkedHashMap<>();
		for (Entry entry : entries) {
			if (entry == null || entry.file() == null || entry.file().getPath() == null) {
				continue;
			}
			final Entry known = last.get(entry.file().key());
			if (known == null || entry.request() >= known.request()) {
				last.remove(entry.file().key());
				last.put(entry.file().key(), entry);
			}
		}
		// the latest request first; within a request, the last written first (the sort is stable)
		final List<Entry> sorted = new ArrayList<>(last.values());
		java.util.Collections.reverse(sorted);
		sorted.sort(Comparator.comparingInt(Entry::request).reversed());
		return sorted;
	}

	private static String outline(List<? extends IGFilesOutliner> outliners, IGFilesLocation location, String path,
			int maxTokens) {
		if (outliners == null) {
			return null;
		}
		for (IGFilesOutliner outliner : outliners) {
			if (outliner.outlines(path)) {
				try {
					final String outline = outliner.outline(location, path, maxTokens);
					if (outline != null && !outline.isBlank()) {
						return outline.strip();
					}
				} catch (IOException | RuntimeException notOutlined) {
					return "(its outline could not be read: " + notOutlined.getMessage() + ")";
				}
			}
		}
		return null;
	}

	/** When a file was written: in this request, or in a request as the user counts them, 1 the first. */
	private static String when(Entry entry, int currentRequest) {
		return entry.request() >= currentRequest ? "in this request" : "in request " + (entry.request() + 1);
	}

	private static boolean changed(GConversationFile written, GLibraryFile now) {
		return written.getVersion() != null && now.version() != null && !written.getVersion().equals(now.version());
	}

	private static String name(GConversationFile file) {
		return file.getLocation() + ": " + file.getPath();
	}

	private static String indented(String outline) {
		return "  " + outline.replace("\r\n", "\n").replace("\n", "\n  ");
	}

	static String size(long bytes) {
		if (bytes < 1024) {
			return bytes + " bytes";
		}
		if (bytes < 1024 * 1024) {
			return String.format(Locale.ROOT, "%.1f KB", bytes / 1024.0);
		}
		return String.format(Locale.ROOT, "%.1f MB", bytes / (1024.0 * 1024.0));
	}
}
