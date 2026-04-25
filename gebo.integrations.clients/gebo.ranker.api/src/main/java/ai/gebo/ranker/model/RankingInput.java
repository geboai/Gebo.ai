package ai.gebo.ranker.model;

import java.util.List;

import org.springframework.ai.document.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingInput {
    private String query;
    private List<Document> documents;
    private Integer topK;
}