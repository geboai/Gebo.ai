package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import ai.gebo.architecture.agents.model.TargetAgentEnvelope;

public class ScheduleTargetAgentEnvelope {

	public static TreeMap<Integer, List<TargetAgentEnvelope<?>>> normalizeDeliveryPlan(
			List<TargetAgentEnvelope<?>> envelopes) {
		TreeMap<Integer, List<TargetAgentEnvelope<?>>> result = new TreeMap<>();

		if (envelopes == null || envelopes.isEmpty()) {
			return result;
		}

		envelopes.stream().sorted(Comparator.comparingInt(TargetAgentEnvelope::getDeliveryOrder))
				.forEach(envelope -> insertEnvelope(result, envelope));

		return result;
	}

	private static void insertEnvelope(TreeMap<Integer, List<TargetAgentEnvelope<?>>> result,
			TargetAgentEnvelope<?> envelope) {
		int targetOrder = envelope.getDeliveryOrder();

		if (envelope.getConcurrency() == TargetAgentEnvelope.DeliveryConcurrency.PARALLEL) {
			List<TargetAgentEnvelope<?>> sameLevel = result.computeIfAbsent(targetOrder, k -> new ArrayList<>());

			boolean levelContainsOnlyParallel = sameLevel.stream()
					.allMatch(e -> e.getConcurrency() == TargetAgentEnvelope.DeliveryConcurrency.PARALLEL);

			if (levelContainsOnlyParallel) {
				sameLevel.add(envelope);
				return;
			}
		}

		// SERIAL oppure collisione con livello non compatibile:
		// trova il primo livello libero o compatibile successivo
		int order = targetOrder;

		while (result.containsKey(order)) {
			order++;
		}

		envelope.setDeliveryOrder(order);
		result.put(order, new ArrayList<>(List.of(envelope)));
	}

}
