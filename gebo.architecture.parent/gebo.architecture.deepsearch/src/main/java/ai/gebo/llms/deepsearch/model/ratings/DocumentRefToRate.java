package ai.gebo.llms.deepsearch.model.ratings;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("document reference input to be rated")
public class DocumentRefToRate {
	@JsonPropertyDescription("unique id")
	private String id = null;
	@JsonPropertyDescription("document title or name")
	private String title = null;
	@JsonPropertyDescription("short document snippet")
	private String snippet = null;
}