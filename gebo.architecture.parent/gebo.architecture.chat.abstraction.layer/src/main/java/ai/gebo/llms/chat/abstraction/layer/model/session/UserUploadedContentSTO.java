package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UserUploadedContentSTO {
	@NotNull
	protected String code = null;
	@NotNull
	protected String description = null;
	@NotNull
	protected String fileName = null;
	@NotNull
	protected String extension = null;
	@NotNull
	protected String contentType = null;
	@NotNull
	protected Long fileSize = null;
	@NotNull
	protected Long tokensCount = null;
	@NotNull
	protected String userContextCode = null;
	public static UserUploadedContentSTO of(UserUploadedContent reference) {
		UserUploadedContentSTO out = new UserUploadedContentSTO(); 
		try {
			BeanUtils.copyProperties(out, reference);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(
					"Exception copying properties, let's refactor this point with old fashioned set/get", e);
		}
		return out;
	}
}
