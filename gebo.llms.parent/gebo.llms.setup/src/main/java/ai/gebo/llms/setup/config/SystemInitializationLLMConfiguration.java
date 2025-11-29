package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.sysinit.llms.config")
@Data
public class SystemInitializationLLMConfiguration {
	@Data
	public static class ChatModelConfiguration {
		private String modelCode = null;
		private boolean toolsEnabled = true;
		private boolean defaultModel = true;
		private Integer contextLength = null;
		private Double temperature = null;
	}

	@Data
	public static class EmbeddingModelConfiguration {
		private String modelCode = null;
		private boolean defaultModel = true;
		private Integer contextLength = null;
	}

	@Data
	public static class VendorConfiguration {
		private String providerId = null;
		private String username = null;
		private String apiKey = null;
		private String url = null;
		private ChatModelConfiguration chatModel = null;
		private EmbeddingModelConfiguration embeddingModel = null;
	}

	private List<VendorConfiguration> providers = new ArrayList<>();
}
