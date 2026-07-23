/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl.model;

import ai.gebo.systems.abstraction.layer.model.AbstractNavigationCoordinates;

/**
 * MCP specialization of the intermediate navigation coordinates: a virtual
 * filesystem root (the MCP server) plus the ordered {@link MCPClientPathComponent}
 * steps that lead to a resource.
 */
public class MCPClientNavigationCoordinates extends AbstractNavigationCoordinates<MCPClientPathComponent> {

}
