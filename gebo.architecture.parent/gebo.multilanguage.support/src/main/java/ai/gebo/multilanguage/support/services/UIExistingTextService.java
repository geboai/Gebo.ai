package ai.gebo.multilanguage.support.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.multilanguage.support.model.UIExistingText;
import ai.gebo.multilanguage.support.repository.UIExistingTextRepository;
import lombok.AllArgsConstructor;

@Service

public class UIExistingTextService {
	private final UIExistingTextRepository repository;
	private final IExistingBundledTextResourcesDao existingBundleDao;

	public UIExistingTextService(@Autowired(required = false) IExistingBundledTextResourcesDao existingBundleDao,
			UIExistingTextRepository repository) {
		this.repository = repository;
		this.existingBundleDao = existingBundleDao;
	}

	public LinkedHashMap getCurrentLanguageResources() {
		final LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>> data = new LinkedHashMap<>();
		if (this.existingBundleDao != null) {
			LinkedHashMap bundle = this.existingBundleDao.getAllEnglishResources();
			if (bundle != null) {
				data.putAll(bundle);
			}
		}
		Stream<UIExistingText> stream = repository.findAll().stream();
		stream.forEach(text -> {
			data.computeIfAbsent(text.getModuleId(), (String moduleId) -> new LinkedHashMap<>());
			data.get(text.getModuleId()).computeIfAbsent(text.getEntityId(),
					(String entityId) -> new LinkedHashMap<>());
			data.get(text.getModuleId()).get(text.getEntityId()).computeIfAbsent(text.getComponentId(),
					(String componentId) -> new LinkedHashMap<>());
			data.get(text.getModuleId()).get(text.getEntityId()).get(text.getComponentId()).put(text.getFieldId(),
					text.getText());
		});
		return data;
	}

	public InputStream getCurrentLanguageResourcesStream() throws StreamWriteException, DatabindException, IOException {

		LinkedHashMap objects = getCurrentLanguageResources();
		ObjectMapper objectMapper = new ObjectMapper();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		objectMapper.writeValue(bos, objects);
		return new ByteArrayInputStream(bos.toByteArray());

	}

	public void update(List<UIExistingText> texts) {
		repository.saveAll(texts);
	}

}
