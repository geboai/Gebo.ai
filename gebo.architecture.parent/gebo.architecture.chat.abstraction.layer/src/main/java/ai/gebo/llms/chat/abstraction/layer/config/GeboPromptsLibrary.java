package ai.gebo.llms.chat.abstraction.layer.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGStaticPromptsProvider;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.prompts")
@PropertySource(value = "classpath:/prompts-library/index.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class GeboPromptsLibrary implements IGStaticPromptsProvider {
	private List<GPromptLibraryReference> library = new ArrayList<GPromptLibraryReference>();
	public static final String DEFAULT_PIPELINE_CHAT_OUTPUT_PROMPT = "default-pipeline-chat-output-prompt";
	public static final String DEFAULT_PIPELINE_RAG_OUTPUT_PROMPT = "default-pipeline-rag-output-prompt";
	public static final String DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT = "default-pipeline-routing-decision-prompt";
	public static final String DEFAULT_PIPELINE_TOOLS_CALL_OUTPUT_PROMPT = "default-pipeline-tools-call-output-prompt";
	public static final String CHAT_HISTORY_DOCUMENTS_CONSOLIDATION = "chat-history-documents-consolidation";
	public static final String HISTORY_CONSOLIDATION_PROMPT = "history-consolidation-prompt";
	public static final String PROMPT_TEMPLATE_WIZARD_DEFAULT = "prompt-template-wizard-default";
	public static final String SUMMARIZE_CHAT_DESCRIPTION = "summarize-chat-description";
	private static Logger LOGGER = LoggerFactory.getLogger(GeboPromptsLibrary.class);

	@Override
	public List<GPromptConfig> promptsList() throws IOException {
		List<GPromptConfig> out = new ArrayList<GPromptConfig>();
		for (GPromptLibraryReference reference : library) {
			LOGGER.info("Loading prompt:" + reference);
			if (reference.getReference() == null || reference.getReference().trim().length() == 0)
				throw new RuntimeException("One entry of the prompts library has empty reference field");
			GPromptConfig prompt = new GPromptConfig();
			prompt.setPromptUse(reference.getPromptUse());
			prompt.setLangCode(reference.getLangCode());
			prompt.setModelProvider(reference.getModelProvider());
			prompt.setModelCode(reference.getModelCode());
			try (InputStream is = getClass().getResourceAsStream(reference.getReference())) {
				if (is == null) {
					throw new RuntimeException("This entry of the prompts library is impossible to load:" + reference);
				} else {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte buffer[] = new byte[4096];
					int read = is.read(buffer);
					while (read > 0) {
						bos.write(buffer, 0, read);
						try {
							read = is.read(buffer);
						} catch (IOException e) {
							break;
						}
					}
					prompt.setPrompt(bos.toString());
				}
				out.add(prompt);
			}

		}
		return out;
	}

}
