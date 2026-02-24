package ai.gebo.architecture.ai.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.model.GPromptLibraryReference;
import ai.gebo.architecture.ai.model.GPromptUseInfo;

public abstract class AbstractStaticPromptsLibraryProvider
		implements IGStaticPromptsProvider, IGStaticPromptUseInfoProvider {
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected abstract List<GPromptLibraryReference> getReferences();

	@Override
	public List<GPromptConfig> promptsList() throws IOException {
		List<GPromptLibraryReference> library = getReferences();
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

	@Override
	public List<GPromptUseInfo> uses() {

		return List.of();
	}
}
