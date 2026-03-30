package ai.gebo.knlowledgebase.model.contents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationObject {
	private String langCode = null;
	private String value = null;
}
