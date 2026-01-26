package ai.gebo.llms.deepsearch.model;

import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeepSearchUISettings {
	private final boolean externalSourcesEnabled;
	private final boolean deepSearchUIAllowChooseSources;

	public static DeepSearchUISettings of(DeepSearchDefaultConfig config) {
		return new DeepSearchUISettings(config.isExternalSourcesEnabled(), config.isDeepSearchUIAllowChooseSources());
	}
}
