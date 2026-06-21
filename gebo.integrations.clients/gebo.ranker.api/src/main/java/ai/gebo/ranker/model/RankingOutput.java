package ai.gebo.ranker.model;

import java.util.List;

import org.springframework.ai.document.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RankingOutput {

	@AllArgsConstructor
	@Getter
	public static class RankingItem {
		private final Document document;
		private final Double ranking;
	}

	private final List<RankingItem> ranked;
}