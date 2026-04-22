package ai.gebo.ranker.standard.client;

import org.springframework.web.client.RestTemplate;

import ai.gebo.ranker.model.RankerModel;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeboStandardRankerClient implements RankerModel {
	private final String apiKey;
	private final String serviceUrl;
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public RankingOutput call(RankingInput input) {
		// TODO Auto-generated method stub
		return null;
	}

}
