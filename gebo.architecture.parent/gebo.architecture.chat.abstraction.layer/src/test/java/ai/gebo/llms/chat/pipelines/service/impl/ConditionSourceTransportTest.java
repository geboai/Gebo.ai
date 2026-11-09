package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.pipelines.service.SolvedCondition;

/**
 * Pins that a client can never say who answered, and how the person answering
 * is shown who solved a condition.
 */
class ConditionSourceTransportTest {

	@Test
	void aClientCannotSayWhoAnswered() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final AgentSuspendedWaitingConditionReactionDto read = mapper.readValue("{\"conditionType\":\"default\","
				+ "\"conditionId\":\"approval\",\"attributes\":{\"value\":\"yes\"},"
				+ "\"source\":{\"kind\":\"PERSON\",\"id\":\"boss\",\"verified\":true}}",
				AgentSuspendedWaitingConditionReactionDto.class);

		assertNull(read.getSource(), "a source sent by a client is not read");

		final AgentSuspendedWaitingConditionReactionDto kept = new AgentSuspendedWaitingConditionReactionDto();
		kept.setConditionId("approval");
		kept.setAttributes(new LinkedHashMap<String, Object>());
		kept.setSource(ConditionSource.of(Kind.PERSON, "ERP", "arossi", null));
		assertFalse(mapper.writeValueAsString(kept).contains("arossi"), "nor written to one");
	}

	@Test
	void theFormShowsYouTheSourceOrTheSystem() {
		final ConditionSource chat = ConditionSource.of(Kind.PERSON, "CHAT", "paolo", null);
		chat.setChannel(ConditionSource.CHAT_CHANNEL);
		chat.setVerified(true);
		assertEquals(SolvedCondition.YOU, new SolvedCondition("year", "CHAT", "paolo", new Date(), chat).solvedByLabel());

		final ConditionSource anna = ConditionSource.of(Kind.PERSON, "ERP", "arossi", "Anna Rossi");
		anna.setVerified(true);
		assertEquals("Anna Rossi (ERP)",
				new SolvedCondition("approval", "ERP", "erp-integration", new Date(), anna).solvedByLabel());

		assertEquals("WORKFLOW", new SolvedCondition("approval", "WORKFLOW", null, new Date()).solvedByLabel());
	}
}
