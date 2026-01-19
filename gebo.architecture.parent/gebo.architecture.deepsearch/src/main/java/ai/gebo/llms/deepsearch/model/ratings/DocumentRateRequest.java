package ai.gebo.llms.deepsearch.model.ratings;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("set of document references rated")
public class DocumentRateRequest {
	@JsonPropertyDescription("list of document references")
	List<DocumentRefToRate> documentRefs = new ArrayList<DocumentRefToRate>();
}