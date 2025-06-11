package ai.gebo.integration.content.handler.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobTicket {
	@NotNull
	String ticketId = null;
	@NotNull
	String contentCode = null;
}