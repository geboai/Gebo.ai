package ai.gebo.llms.chat.agent.standardtools.model;

import java.util.ArrayList;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchResultSample {
	private final String id = UUID.randomUUID().toString();
	private final String code;
	private final String title;
	private final String sample;

	public static class SearchResultSampleList extends ArrayList<SearchResultSample> {
	}
}
