package ai.gebo.integration.content.handler.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobTicket {
	public static enum JobTicketType {
		CONTENT, JOB
	}

	@NotNull
	JobTicketType ticketType = null;
	@NotNull
	String ticketId = null;
	@NotNull
	String contentCode = null;
}