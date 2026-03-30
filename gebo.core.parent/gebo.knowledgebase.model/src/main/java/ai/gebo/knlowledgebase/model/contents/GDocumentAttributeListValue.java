package ai.gebo.knlowledgebase.model.contents;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class GDocumentAttributeListValue extends GBaseObject {
	@HashIndexed
	private String documentAttributeTypeCode = null;
	private TranslatedAttribute multilanguageDescription = null;
	private Map<String, Object> customAttributes = new HashMap<>();
}
