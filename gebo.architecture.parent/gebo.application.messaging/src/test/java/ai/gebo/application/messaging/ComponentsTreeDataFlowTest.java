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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.model.MetaEndpointType;

/**
 * Pins the propagation of {@code getDataFlowMetaInfos()} through
 * {@link ComponentsTreeUtil}, which is where a report can be lost without any
 * error being raised.
 *
 * <p>
 * The case that matters is the dual-role identity: {@code joinModules} builds a
 * brand-new {@link ComponentMetaInfo} when a component both emits and receives,
 * and one identity can be served by two <em>distinct</em> beans - as
 * {@code jobs-master-module.end-of-workflow-compute-service} is by
 * {@code GComputeEndOfWorkflowReceiverFactory} and
 * {@code GWorkflowsConcentratorMessagesEmitterImpl}. Copying from a single side
 * would silently drop the other's endpoints.
 * </p>
 */
public class ComponentsTreeDataFlowTest {

	private static final String MODULE = "jobs-master-module";
	private static final String COMPONENT = "end-of-workflow-compute-service";

	private static GDataFlowMetaInfos flowWith(String endpointId, MetaEndpointType type) {
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId(endpointId);
		endpoint.setDescription(endpointId);
		endpoint.setProduct("test");
		endpoint.setEndpoint("mongodb://user:secret@mongo:27017/db");
		endpoint.setTypes(List.of(type));
		flow.getDataEndpoints().add(endpoint);
		return flow;
	}

	/** Minimal receiver stub; only the identity and the flow report matter here. */
	private static class StubReceiver implements IGMessageReceiver {
		private final String systemId;
		private final GDataFlowMetaInfos flow;

		StubReceiver(String systemId, GDataFlowMetaInfos flow) {
			this.systemId = systemId;
			this.flow = flow;
		}

		@Override
		public GDataFlowMetaInfos getDataFlowMetaInfos() {
			return flow;
		}

		@Override
		public String getMessagingModuleId() {
			return MODULE;
		}

		@Override
		public String getMessagingSystemId() {
			return systemId;
		}

		@Override
		public SystemComponentType getComponentType() {
			return SystemComponentType.APPLICATION_COMPONENT;
		}

		@Override
		public List<String> getAcceptedPayloadTypes() {
			return List.of();
		}

		@Override
		public boolean isAcceptEveryPayloadType() {
			return true;
		}

		@Override
		public void accept(GMessageEnvelope envelope) {
		}
	}

	/** Minimal emitter stub, deliberately a different class from the receiver. */
	private static class StubEmitter implements IGMessageEmitter {
		private final String systemId;
		private final GDataFlowMetaInfos flow;

		StubEmitter(String systemId, GDataFlowMetaInfos flow) {
			this.systemId = systemId;
			this.flow = flow;
		}

		@Override
		public GDataFlowMetaInfos getDataFlowMetaInfos() {
			return flow;
		}

		@Override
		public String getMessagingModuleId() {
			return MODULE;
		}

		@Override
		public String getMessagingSystemId() {
			return systemId;
		}

		@Override
		public SystemComponentType getComponentType() {
			return SystemComponentType.APPLICATION_COMPONENT;
		}

		@Override
		public List<String> getEmittedPayloadTypes() {
			return List.of();
		}
	}

	private static Map<String, Map<String, IGMessageReceiver>> receivers(IGMessageReceiver... items) {
		Map<String, Map<String, IGMessageReceiver>> out = new HashMap<>();
		Map<String, IGMessageReceiver> module = new HashMap<>();
		for (IGMessageReceiver item : items) {
			module.put(item.getMessagingSystemId(), item);
		}
		out.put(MODULE, module);
		return out;
	}

	private static Map<String, Map<String, IGMessageEmitter>> emitters(IGMessageEmitter... items) {
		Map<String, Map<String, IGMessageEmitter>> out = new HashMap<>();
		Map<String, IGMessageEmitter> module = new HashMap<>();
		for (IGMessageEmitter item : items) {
			module.put(item.getMessagingSystemId(), item);
		}
		out.put(MODULE, module);
		return out;
	}

	private static ComponentMetaInfo single(List<GModuleMetaInfo> tree) {
		assertEquals(1, tree.size(), "expected exactly one module");
		assertEquals(1, tree.get(0).getComponents().size(), "expected exactly one component");
		return tree.get(0).getComponents().get(0);
	}

	@Test
	public void aReceiverOnlyComponentCarriesItsFlowThrough() {
		List<GModuleMetaInfo> tree = ComponentsTreeUtil.componentsTree(
				receivers(new StubReceiver(COMPONENT, flowWith("mongo", MetaEndpointType.DATABASE))), new HashMap<>());

		GDataFlowMetaInfos flow = single(tree).getDataFlowMetaInfos();
		assertNotNull(flow);
		assertEquals("mongo", flow.getDataEndpoints().get(0).getId());
	}

	@Test
	public void anEmitterOnlyComponentCarriesItsFlowThrough() {
		List<GModuleMetaInfo> tree = ComponentsTreeUtil.componentsTree(new HashMap<>(),
				emitters(new StubEmitter(COMPONENT, flowWith("qdrant", MetaEndpointType.VECTORIAL_DATABASE))));

		GDataFlowMetaInfos flow = single(tree).getDataFlowMetaInfos();
		assertNotNull(flow);
		assertEquals("qdrant", flow.getDataEndpoints().get(0).getId());
	}

	@Test
	public void aDualRoleIdentityServedByTwoBeansKeepsBothReports() {
		// The regression this guards: joinModules() builds a NEW ComponentMetaInfo,
		// so a naive implementation keeps one side's endpoints and loses the other's.
		List<GModuleMetaInfo> tree = ComponentsTreeUtil.componentsTree(
				receivers(new StubReceiver(COMPONENT, flowWith("mongo", MetaEndpointType.DATABASE))),
				emitters(new StubEmitter(COMPONENT, flowWith("rabbit", MetaEndpointType.MESSAGE_BROKER))));

		ComponentMetaInfo component = single(tree);
		assertTrue(component.isReceiver() && component.isEmitter(), "expected the joined dual-role component");

		GDataFlowMetaInfos flow = component.getDataFlowMetaInfos();
		assertNotNull(flow, "the merged component must keep a flow report");
		List<String> ids = flow.getDataEndpoints().stream().map(DataEndpoint::getId).toList();
		assertEquals(2, ids.size(), "both sides' endpoints must survive the join");
		assertTrue(ids.contains("mongo"), "the receiver side's endpoint must survive");
		assertTrue(ids.contains("rabbit"), "the emitter side's endpoint must survive");
	}

	@Test
	public void theSameInstanceInBothMapsIsNotDoubleCounted() {
		// The ordinary dual-role case: one bean implementing both interfaces is
		// registered in both of GBaseMessageBroker's maps and reports twice.
		DualRole both = new DualRole(flowWith("mongo", MetaEndpointType.DATABASE));

		List<GModuleMetaInfo> tree = ComponentsTreeUtil.componentsTree(receivers(both), emitters(both));

		GDataFlowMetaInfos flow = single(tree).getDataFlowMetaInfos();
		assertEquals(1, flow.getDataEndpoints().size(), "de-duplicated by endpoint id");
	}

	@Test
	public void aComponentReportingNothingStaysNull() {
		List<GModuleMetaInfo> tree = ComponentsTreeUtil.componentsTree(receivers(new StubReceiver(COMPONENT, null)),
				new HashMap<>());

		assertNull(single(tree).getDataFlowMetaInfos());
	}

	@Test
	public void theEndpointIsSanitizedBeforeItEverReachesTheTree() {
		List<GModuleMetaInfo> tree = ComponentsTreeUtil.componentsTree(
				receivers(new StubReceiver(COMPONENT, flowWith("mongo", MetaEndpointType.DATABASE))), new HashMap<>());

		String endpoint = single(tree).getDataFlowMetaInfos().getDataEndpoints().get(0).getEndpoint();
		assertEquals("mongodb://mongo:27017/db", endpoint);
	}

	/** One bean serving both roles, as most dual-role components actually are. */
	private static class DualRole extends StubReceiver implements IGMessageEmitter {
		DualRole(GDataFlowMetaInfos flow) {
			super(COMPONENT, flow);
		}

		@Override
		public List<String> getEmittedPayloadTypes() {
			return List.of();
		}
	}
}
