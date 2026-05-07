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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.model.GPromptLibraryReference;
import ai.gebo.architecture.ai.model.GPromptUseInfo;
import lombok.AllArgsConstructor;

public class PromptProvidersImplementation implements IGStaticPromptsProvider, IGStaticPromptUseInfoProvider {
	private final static Logger LOGGER = LoggerFactory.getLogger(PromptProvidersImplementation.class);
	final Object objectFromActualClassLoader;
	final List<GPromptLibraryReference> library;
	final List<GPromptLibraryReference> override;

	public PromptProvidersImplementation(Object objectFromActualClassLoader, List<GPromptLibraryReference> library) {
		this.objectFromActualClassLoader = objectFromActualClassLoader;
		this.library = library;
		this.override = List.of();
	}

	public PromptProvidersImplementation(Object objectFromActualClassLoader, List<GPromptLibraryReference> library,
			List<GPromptLibraryReference> override) {
		this.objectFromActualClassLoader = objectFromActualClassLoader;
		this.library = library;
		this.override = override;
	}

	@Override
	public List<GPromptConfig> promptsList() {

		List<GPromptConfig> out = new ArrayList<GPromptConfig>();
		Map<String, GPromptLibraryReference> uniqueEntries = new HashMap<>();
		if (library != null) {
			for (GPromptLibraryReference reference : library) {
				uniqueEntries.put(reference.getPromptUse().toLowerCase() + "-" + reference.getLangCode().toLowerCase(),
						reference);
			}
		}
		if (override != null) {
			for (GPromptLibraryReference reference : override) {
				uniqueEntries.put(reference.getPromptUse().toLowerCase() + "-" + reference.getLangCode().toLowerCase(),
						reference);
			}
		}
		List<GPromptLibraryReference> overriddenList = new ArrayList<>(uniqueEntries.values());

		for (GPromptLibraryReference reference : overriddenList) {
			LOGGER.info("Loading prompt:" + reference);
			if (reference.getReference() == null || reference.getReference().trim().length() == 0)
				throw new RuntimeException("One entry of the prompts library has empty reference field");
			GPromptConfig prompt = new GPromptConfig();
			prompt.setPromptUse(reference.getPromptUse());
			prompt.setLangCode(reference.getLangCode());
			prompt.setModelProvider(reference.getModelProvider());
			prompt.setModelCode(reference.getModelCode());
			Path _path = Path.of(reference.getReference());
			boolean readFromExternal=Files.exists(_path);
			try (InputStream is =readFromExternal? Files.newInputStream(_path): objectFromActualClassLoader.getClass()
					.getResourceAsStream(reference.getReference())) {
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
			} catch (IOException e1) {
				throw new IllegalStateException("Incoherent state, cannot access part of prompts lybrary", e1);
			}

		}

		return out;
	}

	@Override
	public List<GPromptUseInfo> uses() {

		return List.of();
	}
}