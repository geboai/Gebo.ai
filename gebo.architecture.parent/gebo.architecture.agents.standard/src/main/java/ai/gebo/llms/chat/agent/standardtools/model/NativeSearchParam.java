package ai.gebo.llms.chat.agent.standardtools.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

@Data
public class NativeSearchParam<NativeQueryObject extends INativeQueryObject> {
	private NativeQueryObject query = null;
	@JsonPropertyDescription("Number of elements returned")
	private int topK;
	@JsonPropertyDescription("Number of tokens for each document content sample")
	private int textSampleTokens = 250;
}
