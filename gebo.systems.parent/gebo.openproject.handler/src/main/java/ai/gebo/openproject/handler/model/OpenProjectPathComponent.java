/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.model;

/**
 * One typed step of an OpenProject path: its node type and its id (a project id, a
 * section name, or the number of a work package, document, meeting, news or
 * attachment).
 */
public class OpenProjectPathComponent {
	public OpenProjectPathNodeType type = null;
	public String id = null;

	public OpenProjectPathComponent() {
	}

	public OpenProjectPathComponent(OpenProjectPathNodeType type, String id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public String toString() {
		return type + ":" + id;
	}
}
