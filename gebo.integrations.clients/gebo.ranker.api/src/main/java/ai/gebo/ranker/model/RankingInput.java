package ai.gebo.ranker.model;

import java.util.List;

import org.springframework.ai.document.Document;

import lombok.Data;

@Data
public class RankingInput {
	List<Document> documents = null;
}
