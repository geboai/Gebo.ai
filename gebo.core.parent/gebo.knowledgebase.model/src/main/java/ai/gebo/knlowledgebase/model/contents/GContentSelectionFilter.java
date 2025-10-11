package ai.gebo.knlowledgebase.model.contents;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GContentSelectionFilter{
	private List<GContentSelectionFilterCriteria> criterias = new ArrayList<>();
	
}
