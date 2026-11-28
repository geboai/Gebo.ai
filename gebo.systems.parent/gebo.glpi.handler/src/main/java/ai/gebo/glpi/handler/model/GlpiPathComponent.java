/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.model;

/**
 * One typed step of a GLPI navigation path: its kind and, for the nodes that
 * have one, the GLPI id.
 */
public class GlpiPathComponent {
	public GlpiPathNodeType type = null;
	public Long id = null;

	public GlpiPathComponent() {
	}

	public GlpiPathComponent(GlpiPathNodeType type, Long id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public String toString() {
		return "{type=" + type + ", id=" + id + "}";
	}
}
