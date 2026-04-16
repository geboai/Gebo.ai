package ai.gebo.llms.deepsearch.model.ratings;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("document references output wth rating")
public class RatedDocumentsList {
	@JsonPropertyDescription("rated document references")
	List<RatedDocumentRefOutput> ratedDocumentRefs=new ArrayList<RatedDocumentRefOutput>();
}