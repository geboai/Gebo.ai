/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The keeper of a request's written files: safe for the tools of agents
 * running in parallel. Paths are kept with '/' separators.
 */
public class GConversationFilesKeeper implements IGConversationFilesKeeper {
	private final Map<String, GConversationFile> written = new LinkedHashMap<>();

	@Override
	public synchronized void written(GLibraryFile file) {
		if (file == null || file.path() == null || file.path().isBlank()) {
			return;
		}
		final GConversationFile kept = new GConversationFile(file.location(), file.path().trim().replace('\\', '/'),
				file.version());
		final GConversationFile known = written.get(kept.key());
		if (known != null) {
			known.setVersion(kept.getVersion());
		} else {
			written.put(kept.key(), kept);
		}
	}

	@Override
	public synchronized List<GConversationFile> getWritten() {
		final List<GConversationFile> copy = new ArrayList<>();
		for (GConversationFile file : written.values()) {
			copy.add(new GConversationFile(file.getLocation(), file.getPath(), file.getVersion()));
		}
		return copy;
	}
}
