package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PipelineRoutingInfos {
	List<String> stepIds = new ArrayList<String>();
}