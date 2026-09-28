package ai.gebo.openrouter.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/** Envelope of the {@code GET /key} response. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouterKeyResponse {
	private OpenRouterKeyInfo data;
}
