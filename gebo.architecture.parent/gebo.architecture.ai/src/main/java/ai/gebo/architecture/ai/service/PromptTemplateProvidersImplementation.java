package ai.gebo.architecture.ai.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap.ReferenceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.GPromptTemplateLibraryReference;
import ai.gebo.architecture.ai.model.GPromptUseInfo;
import lombok.AllArgsConstructor;

public class PromptTemplateProvidersImplementation implements IGStaticPromptsProvider, IGStaticPromptUseInfoProvider {
	private final static Logger LOGGER = LoggerFactory.getLogger(PromptTemplateProvidersImplementation.class);
	final Object objectFromActualClassLoader;
	final List<GPromptTemplateLibraryReference> library;
	final List<GPromptTemplateLibraryReference> override;

	public PromptTemplateProvidersImplementation(Object objectFromActualClassLoader,
			List<GPromptTemplateLibraryReference> library) {
		this.objectFromActualClassLoader = objectFromActualClassLoader;
		this.library = library;
		this.override = List.of();
	}

	public PromptTemplateProvidersImplementation(Object objectFromActualClassLoader,
			List<GPromptTemplateLibraryReference> library, List<GPromptTemplateLibraryReference> override) {
		this.objectFromActualClassLoader = objectFromActualClassLoader;
		this.library = library;
		this.override = override;
	}

	@Override
	public List<GPromptTemplateConfig> promptsList() throws IOException {

		List<GPromptTemplateConfig> out = new ArrayList<GPromptTemplateConfig>();
		Map<String, GPromptTemplateLibraryReference> uniqueEntries = new HashMap<>();
		if (library != null) {
			for (GPromptTemplateLibraryReference reference : library) {
				uniqueEntries.put(reference.getPromptUse().toLowerCase() + "-" + reference.getLangCode().toLowerCase(),
						reference);
			}
		}
		if (override != null) {
			for (GPromptTemplateLibraryReference reference : override) {
				uniqueEntries.put(reference.getPromptUse().toLowerCase() + "-" + reference.getLangCode().toLowerCase(),
						reference);
			}
		}
		List<GPromptTemplateLibraryReference> overriddenList = new ArrayList<>(uniqueEntries.values());

		for (GPromptTemplateLibraryReference reference : overriddenList) {
			LOGGER.info("Loading prompt:" + reference);

			GPromptTemplateConfig prompt = this.loadReference(reference, objectFromActualClassLoader);

			out.add(prompt);

		}

		return out;
	}

	private GPromptTemplateConfig loadReference(GPromptTemplateLibraryReference reference,
			Object objectFromActualClassLoader) throws IOException {
		if (reference.getSystemReference() == null || reference.getSystemReference().trim().length() == 0)
			throw new RuntimeException("One entry of the prompts library has empty systemReference field");
		if (reference.getUserReference() == null || reference.getUserReference().trim().length() == 0)
			throw new RuntimeException("One entry of the prompts library has empty userReference field");
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse(reference.getPromptUse());
		prompt.setLangCode(reference.getLangCode());
		prompt.setModelProvider(reference.getModelProvider());
		prompt.setModelCode(reference.getModelCode());
		prompt.setSystemPromptTemplate(tryLoadString(reference.getSystemReference(), objectFromActualClassLoader));
		prompt.setUserPromptTemplate(tryLoadString(reference.getUserReference(), objectFromActualClassLoader));
		prompt.setChatHistory(reference.getChatHistory());
		prompt.setContextDocuments(reference.getContextDocuments());
		prompt.setToolsCalling(reference.getToolsCalling());
		prompt.setAgentId(reference.getAgentId());
		prompt.setAgentPrompt(reference.getAgentPrompt());
		prompt.setDescription(reference.getDescription());
		return prompt;
	}

	private String tryLoadString(String reference, Object objectFromActualClassLoader) throws IOException {
		InputStream is = null;
		try {
			is = objectFromActualClassLoader.getClass().getResourceAsStream(reference);
			if (is == null) {
				Path path = Path.of(reference);
				if (Files.exists(path)) {
					is = Files.newInputStream(path);
				}
			}
			if (is == null) {
				throw new IOException("Cannot find resource:" + reference);
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			FileCopyUtils.copy(is, bos);
			bos.flush();
			return bos.toString();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Throwable th) {
			}
		}
	}

	@Override
	public List<GPromptUseInfo> uses() {

		return List.of();
	}
}