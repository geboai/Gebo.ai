package ai.gebo.architecture.agents.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Agent command or message to be executed wrapper")
public class TargetAgentEnvelope<T> {
	@JsonClassDescription("Agent execution concurrency specification")
	public static enum DeliveryConcurrency {
		PARALLEL, SERIAL
	}

	@JsonPropertyDescription("The order the command will be executed by the agent")
	private int deliveryOrder = 0;
	@JsonPropertyDescription("The concurrency that agent will execute, agents with concurrency=PARALLEL and same deliveryOrder will be run in parallel")
	private DeliveryConcurrency concurrency = DeliveryConcurrency.SERIAL;
	@JsonPropertyDescription("The message content that will be delivered")
	private T commandData = null;
	@JsonIgnore
	private String agentId = null;
}