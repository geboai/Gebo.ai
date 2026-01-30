package ai.gebo.llms.openai_compat.services;

import org.springframework.http.HttpHeaders;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;

public class ModelsListCommonUtils {

	/**
	 * Creates HTTP headers with the provided API key for authenticating with X.ai
	 * API.
	 * 
	 * @param clearApiKey The API key to use for authentication
	 * @return HttpHeaders with the authorization header set
	 */
	static HttpHeaders getHeaders(String clearApiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + clearApiKey);
		return headers;
	}

	/**
	 * Creates a new instance of the specified model choice type.
	 * 
	 * @param <ModelChoice> The type of model choice to instantiate
	 * @param choiceType    The class of the model choice
	 * @return A new instance of the specified model choice type, or null if
	 *         instantiation fails
	 */
	static <ModelChoice extends GBaseModelChoice> ModelChoice newInstance(Class<ModelChoice> choiceType) {
		try {
			return choiceType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
