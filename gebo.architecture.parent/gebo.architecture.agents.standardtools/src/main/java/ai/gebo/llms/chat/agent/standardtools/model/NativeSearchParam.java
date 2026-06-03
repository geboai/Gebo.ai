package ai.gebo.llms.chat.agent.standardtools.model;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

@Data
public class NativeSearchParam<NativeQueryObject extends INativeQueryObject> {
	private NativeQueryObject query = null;
	private int topK = 0;
	private Integer textSampleTokens=null;
}
