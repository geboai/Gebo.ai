package ai.gebo.llms.mistralai.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MistralBaseModelCards {
	private String object;
	private List<MistralBaseModelCard> data = new ArrayList<>();
}