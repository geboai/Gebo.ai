package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMGeneratedResourceSTO {
	@NotNull
	protected String code = null;
	@NotNull
	protected String description = null;
	@NotNull
	protected String fileName = null;
	protected String extension = null;
	protected String contentType = null;
	protected Long fileSize = null;
	protected Long tokensCount = null;
	@NotNull
	protected String userContextCode = null;

	public static LLMGeneratedResourceSTO of(LLMGeneratedResource reference) {
		LLMGeneratedResourceSTO out = new LLMGeneratedResourceSTO();
		try {
			BeanUtils.copyProperties(out, reference);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(
					"Exception copying properties, let's refactor this point with old fashioned set/get", e);
		}
		return out;
	}
}
