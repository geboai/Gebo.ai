/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.repositories.uniqueid;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;

/**
 * Gives the document references and the virtual folders their uniqueId when they
 * are saved without one (see {@link VirtualFilesystemUniqueIds}): every repository
 * save/insert and every MongoTemplate save/insert goes through it. The objects saved
 * before it existed are numbered at startup (see
 * {@link VirtualFilesystemUniqueIdBackfill}).
 */
@Component
public class VirtualFilesystemUniqueIdCallback
		implements BeforeConvertCallback<GAbstractVirtualFilesystemObject>, Ordered {

	private final ObjectProvider<VirtualFilesystemUniqueIds> uniqueIds;

	public VirtualFilesystemUniqueIdCallback(ObjectProvider<VirtualFilesystemUniqueIds> uniqueIds) {
		this.uniqueIds = uniqueIds;
	}

	@Override
	public GAbstractVirtualFilesystemObject onBeforeConvert(GAbstractVirtualFilesystemObject entity,
			String collection) {
		if (VirtualFilesystemUniqueIds.isNumbered(entity) && entity.getUniqueId() == null) {
			uniqueIds.getObject().ensureUniqueId(entity);
		}
		return entity;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
