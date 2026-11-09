package ai.gebo.architecture.agents.conditionevents.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;

/**
 * Pins which source an event is kept with: a claimed one as claimed, verified
 * only by who delivered it; none claimed, the deliverer itself.
 */
class ConditionSourcesTest {
	private static final String APPLICATION = "ROLE_APPLICATION";

	private static ConditionSource person(String id) {
		final ConditionSource source = ConditionSource.of(Kind.PERSON, "ERP", id, "Anna Rossi");
		source.setSessionCode("erp-session-7");
		final LinkedHashMap<String, Object> details = new LinkedHashMap<String, Object>();
		details.put("role", "manager");
		source.setDetails(details);
		return source;
	}

	@Test
	void theChatsUserIsAVerifiedPersonInItsConversation() {
		final ConditionSource source = ConditionSources.forChatUser("paolo", "Paolo", "conv-1");

		assertEquals(Kind.PERSON, source.getKind());
		assertEquals(ConditionSources.CHAT_SYSTEM, source.getSystem());
		assertEquals("conv-1", source.getSessionCode());
		assertTrue(source.isConversationUser());
		assertEquals("Paolo (GEBO-CHAT)", source.label());
	}

	@Test
	void aClaimedSourceIsKeptAsClaimedButVerifiedOnlyByItsOwnAccount() {
		final ConditionEventCaller other = ConditionEventCaller.of("erp-integration", Set.of("ROLE_USER"), "REST");
		final ConditionSource claimed = person("arossi");
		claimed.setVerified(true);
		claimed.setChannel(ConditionSource.CHAT_CHANNEL);

		final ConditionSource kept = ConditionSources.ofDelivered(claimed, null, other, APPLICATION);

		assertNotSame(claimed, kept);
		assertEquals("arossi", kept.getId());
		assertEquals("erp-session-7", kept.getSessionCode());
		assertEquals("manager", kept.getDetails().get("role"));
		assertFalse(kept.isVerified(), "what it says of itself is not believed");
		assertEquals("REST", kept.getChannel(), "the channel is the one it came through");
		assertFalse(kept.isConversationUser());

		final ConditionEventCaller itself = ConditionEventCaller.of("arossi", Set.of("ROLE_USER"), "REST");
		assertTrue(ConditionSources.ofDelivered(claimed, null, itself, APPLICATION).isVerified());
	}

	@Test
	void anApplicationOrATrustedChannelVerifiesWhatItDeliversForOthers() {
		final ConditionEventCaller application = ConditionEventCaller.of("erp-integration",
				Set.of("ROLE_USER", APPLICATION), "REST");
		assertTrue(ConditionSources.ofDelivered(person("arossi"), null, application, APPLICATION).isVerified());

		final ConditionEventCaller broker = ConditionEventCaller.trustedChannel("workflow-engine", "MESSAGE",
				"WORKFLOW.STEP");
		assertTrue(ConditionSources.ofDelivered(person("arossi"), null, broker, APPLICATION).isVerified());
	}

	@Test
	void anAgentKeepsItsCodeNetworkAndSession() {
		final ConditionSource agent = ConditionSource.of(Kind.AGENT, "ACCOUNTING", "invoice-checker", null);
		agent.setAgentCode("invoice-checker");
		agent.setNetworkCode("accounting-network");
		agent.setSessionCode("acc-session-3");

		final ConditionSource kept = ConditionSources.ofDelivered(agent, null,
				ConditionEventCaller.trustedChannel(null, "MESSAGE", "ACCOUNTING.AGENTS"), APPLICATION);

		assertEquals("invoice-checker", kept.getAgentCode());
		assertEquals("accounting-network", kept.getNetworkCode());
		assertEquals("acc-session-3", kept.getSessionCode());
		assertEquals("agent invoice-checker (ACCOUNTING)", kept.label());
	}

	@Test
	void anEventClaimingNoSourceIsToldByItsDeliverer() {
		final ConditionEventCaller caller = ConditionEventCaller.of("erp-integration", Set.of("ROLE_USER"), "REST");

		final ConditionSource kept = ConditionSources.ofDelivered(null, "ERP", caller, APPLICATION);

		assertEquals(Kind.UNKNOWN, kept.getKind());
		assertEquals("ERP", kept.getSystem());
		assertEquals("erp-integration", kept.getId());
		assertTrue(kept.isVerified(), "it is the delivering account");

		final ConditionSource fromBroker = ConditionSources.ofDelivered(null, null,
				ConditionEventCaller.trustedChannel("robot-1", "MESSAGE", "WORKFLOW.STEP"), APPLICATION);
		assertEquals("WORKFLOW.STEP", fromBroker.getSystem(), "the channel's component, when it names no system");
	}

	@Test
	void theSystemNamedTheOlderWayFillsAClaimedSourceWithoutOne() {
		final ConditionSource claimed = ConditionSource.of(Kind.PERSON, null, "arossi", null);
		assertEquals("ERP", ConditionSources.ofDelivered(claimed, "ERP", null, APPLICATION).getSystem());
		assertFalse(ConditionSources.ofDelivered(claimed, "ERP", null, APPLICATION).isVerified(), "nobody delivered it");
	}

	@Test
	void anEventKeptBeforeSourcesIsToldByWhatItKept() {
		final GConditionEvent event = new GConditionEvent();
		event.setSourceSystem("WORKFLOW");
		event.setActor("erp-integration");
		event.setChannel("REST");

		final ConditionSource source = ConditionSources.ofKept(event);

		assertEquals("erp-integration (WORKFLOW)", source.label());
		assertEquals("REST", source.getChannel());
		assertTrue(source.isVerified());
		assertNull(ConditionSources.ofKept(new GConditionEvent()).getId());
	}
}
