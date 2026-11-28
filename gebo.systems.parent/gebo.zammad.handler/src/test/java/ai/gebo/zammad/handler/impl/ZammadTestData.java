/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import ai.gebo.zammad.client.invoker.ApiClient;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;

/**
 * Client models read from JSON shaped like the answers of Zammad 7.3, as the
 * handler gets them: read only fields (ids, numbers) have no setters.
 */
final class ZammadTestData {
	private ZammadTestData() {
	}

	static <T> T read(String json, Class<T> type) {
		return ApiClient.createDefaultMapper(null).readValue(json.replace('\'', '"'), type);
	}

	/** An expanded ticket of the group "Gebo Support". */
	static Ticket ticket() {
		return read("{'id':2,'number':'97002','title':'SMTP relay rejects large mails','group_id':2,"
				+ "'state_id':2,'priority_id':3,'group':'Gebo Support','state':'open','priority':'3 high',"
				+ "'owner':'admin@gebo.test','customer':'carla@acme.test','organization':'Acme Mail',"
				+ "'created_at':'2026-11-27T10:00:00Z','updated_at':'2026-11-27T11:00:00Z'}", Ticket.class);
	}

	static TicketArticle customerMessage() {
		return read("{'id':5,'ticket_id':2,'type':'web','sender':'Customer','from':'Carla <carla@acme.test>',"
				+ "'subject':'SMTP relay rejects large mails','content_type':'text/plain','internal':false,"
				+ "'body':'The relay rejects my mails:\\nmessage too large (12MB)','created_at':'2026-11-27T10:00:00Z',"
				+ "'attachments':[{'id':9,'filename':'relay.log','size':'58','preferences':{'Content-Type':'text/plain'}}]}",
				TicketArticle.class);
	}

	static TicketArticle internalNote() {
		return read("{'id':6,'ticket_id':2,'type':'note','sender':'Agent','from':'Admin Gebo',"
				+ "'subject':'','content_type':'text/html','internal':true,"
				+ "'body':'<p>Confirmed on production too, the relay limit is <b>10MB</b>.</p><script>alert(1)</script>',"
				+ "'created_at':'2026-11-27T10:30:00Z','attachments':[]}", TicketArticle.class);
	}
}
