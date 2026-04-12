package ai.gebo.systems.abstraction.layer;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public abstract class AbstractContentConsumingSessionParam implements Serializable {
	@Id
	private String sessionId = UUID.randomUUID().toString();
}
