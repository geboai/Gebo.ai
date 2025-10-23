package ai.gebo.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.multilanguage.support.services.IExistingBundledTextResourcesDao;

@Service
public class ExistingBundledTextResourcesDaoImpl implements IExistingBundledTextResourcesDao {
	private final static String EN_FILE = "/static/assets/i18n/en.json";
	private final static Logger LOGGER = LoggerFactory.getLogger(ExistingBundledTextResourcesDaoImpl.class);

	public ExistingBundledTextResourcesDaoImpl() {

	}

	@Override
	public LinkedHashMap getAllEnglishResources() {
		LOGGER.info("Calling getAllEnglishResources()");
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream is = getClass().getResourceAsStream(EN_FILE);
		if (is == null)
			throw new RuntimeException("Cannot load " + EN_FILE);
		try {
			return objectMapper.readValue(is, LinkedHashMap.class);
		} catch (IOException e) {
			throw new RuntimeException("Cannot load " + EN_FILE, e);
		}
	}

}
