package ai.gebo.llms.chat.agent.standardtools.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class SearchResultSample {
	private final String id = UUID.randomUUID().toString();
	private final String code;
	private final String title;
	private final String sample;

	@NoArgsConstructor
	public static class SearchResultSampleList extends ArrayList<SearchResultSample> {
		public SearchResultSampleList(List<SearchResultSample> list) {
			super(list);
		}
	}
}
