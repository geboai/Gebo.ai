/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import ai.gebo.glpi.client.model.Ticket;
import tools.jackson.databind.json.JsonMapper;

class GlpiApiClientFactoryTest {

	@Test
	void theRootUrlIsTheWebInterfaceOne() {
		assertThat(GlpiApiClientFactory.rootUrlOf("https://glpi.example.com")).isEqualTo("https://glpi.example.com");
		assertThat(GlpiApiClientFactory.rootUrlOf(" https://glpi.example.com/ ")).isEqualTo("https://glpi.example.com");
		assertThat(GlpiApiClientFactory.rootUrlOf("https://example.com/glpi/api.php/v2.3/"))
				.isEqualTo("https://example.com/glpi");
		assertThat(GlpiApiClientFactory.rootUrlOf("https://example.com/glpi/api.php")).isEqualTo("https://example.com/glpi");
		assertThat(GlpiApiClientFactory.rootUrlOf("  ")).isNull();
		assertThat(GlpiApiClientFactory.rootUrlOf(null)).isNull();
	}

	@Test
	void anEnumValueTheSpecificationDoesNotListIsReadAsNull() {
		// GLPI priority 6 ("major") is not in the generated PriorityEnum
		JsonMapper mapper = GlpiApiClientFactory.createMapper();
		Ticket ticket = mapper.readValue("{\"id\":1042,\"name\":\"Printer offline\",\"priority\":6,\"urgency\":3,"
				+ "\"date_mod\":\"2026-05-05T10:00:00+02:00\",\"status\":{\"id\":2,\"name\":\"Processing (assigned)\"},"
				+ "\"entity\":{\"id\":3,\"name\":\"Sales\",\"completename\":\"Root entity > Sales\"}}", Ticket.class);
		assertThat(ticket.getId()).isEqualTo(1042L);
		assertThat(ticket.getPriority()).isNull();
		assertThat(ticket.getUrgency().getValue()).isEqualTo(3);
		assertThat(ticket.getDateMod()).isEqualTo(OffsetDateTime.parse("2026-05-05T10:00:00+02:00"));
		assertThat(ticket.getStatus().getName()).isEqualTo("Processing (assigned)");
		assertThat(ticket.getEntity().getCompletename()).isEqualTo("Root entity > Sales");
	}
}
