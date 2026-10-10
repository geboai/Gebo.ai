/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointAccess;
import ai.gebo.application.messaging.model.DataFlowAccessResolution;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GDataFlowReport;
import ai.gebo.application.messaging.model.GModuleMetaInfo;

/**
 * Pins how a collected register's access rules are completed: the access model in
 * force set on the report, the ACL aliases decoded into entries (unknown ones left
 * out), and the groups the rules name - in their lists or as ACL principals -
 * described.
 */
class DataFlowAccessResolutionTest {

	private static GDataFlowReport reportWith(DataEndpointAccess access) {
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("source-a");
		endpoint.setAccess(new ArrayList<>(List.of(access)));
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.getDataEndpoints().add(endpoint);
		ComponentMetaInfo component = new ComponentMetaInfo();
		component.setDataFlowMetaInfos(flow);
		return new GDataFlowReport("node", new Date(),
				new ArrayList<>(List.of(new GModuleMetaInfo("module", new ArrayList<>(List.of(component))))));
	}

	@Test
	void aliasesAreDecodedAndTheNamedGroupsDescribed() {
		DataEndpointAccess access = new DataEndpointAccess();
		access.getGroups().add("hr");
		access.withAclAliases(List.of(1, 2, 3));
		GDataFlowReport report = reportWith(access);

		DataFlowAccessResolution.apply(report, "ACL_BASED", alias -> switch (alias) {
		case 1 -> new DataEndpointAccess.AclEntry("group:legal", "READ");
		case 2 -> new DataEndpointAccess.AclEntry("user:anna@example.com", "EXECUTE");
		default -> null;
		}, Map.of("hr", "Human resources", "legal", "Legal office", "sales", "Sales"));

		assertEquals("ACL_BASED", report.getContentAccessPolicy());
		assertEquals(List.of(new DataEndpointAccess.AclEntry("group:legal", "READ"),
				new DataEndpointAccess.AclEntry("user:anna@example.com", "EXECUTE")), access.getAclEntries(),
				"the unknown alias 3 is left out");
		assertEquals(Map.of("hr", "Human resources", "legal", "Legal office"), report.getGroupDescriptions(),
				"only the groups the rules name");
	}

	@Test
	void withoutAnAclStoreTheAliasesStayUndecoded() {
		DataEndpointAccess access = new DataEndpointAccess();
		access.withAclAliases(List.of(1));
		GDataFlowReport report = reportWith(access);

		DataFlowAccessResolution.apply(report, "GROUP_BASED", null, null);

		assertEquals("GROUP_BASED", report.getContentAccessPolicy());
		assertTrue(access.getAclEntries().isEmpty());
		assertEquals(List.of(1), access.getAclAliases());
	}
}
