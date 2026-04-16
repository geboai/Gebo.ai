package ai.gebo.bingsearch.handler.service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.bingsearch.handler.model.BingSearchQuery;
import ai.gebo.bingsearch.handler.model.BingSearchResponse;
import ai.gebo.bingsearch.handler.model.ResponseFilters;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BingSearchApi {
	private static Logger LOGGER = LoggerFactory.getLogger(BingSearchApi.class);
	public static final String BING_SEARCH_SERVICE_URL = "https://api.bing.microsoft.com/v7.0/search";
	private final RestTemplateWrapperService restTemplateService;

	private String toWireFilter(ResponseFilters f) {
		// i valori "wire" tipici sono "Webpages" e "News"
		return switch (f) {
		case WEBPAGES -> "Webpages";
		case NEWS -> "News";
		};
	}

	public BingSearchResponse search(String apiKey, BingSearchQuery q) throws GeboRestIntegrationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(...)");
		}
		if (q == null || q.getQuery() == null || q.getQuery().isBlank()) {
			throw new IllegalArgumentException("query must be provided");
		}
		if (apiKey == null || apiKey.isBlank()) {
			throw new IllegalArgumentException("apiKey must be provided");
		}

		// request params
		UriComponentsBuilder b = UriComponentsBuilder.fromHttpUrl(BING_SEARCH_SERVICE_URL).queryParam("q", q.getQuery())

				.queryParam("textDecorations", "false").queryParam("textFormat", "Raw");

		if (q.getTopN() != null) {
			b.queryParam("count", q.getTopN()); // Bing usa "count"
		}

		if (q.getFilters() != null && !q.getFilters().isEmpty()) {
			// Bing usa "responseFilter" come lista comma-separated (es: Webpages,News)
			String responseFilter = q.getFilters().stream().map(this::toWireFilter).collect(Collectors.joining(","));
			b.queryParam("responseFilter", responseFilter);
		}

		// headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Ocp-Apim-Subscription-Key", apiKey);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		HttpEntity<Void> entity = new HttpEntity<>(headers);
		URI uri = b.build(true).toUri();

		ResponseEntity<BingSearchResponse> resp = restTemplateService.exchange(uri.toString(),
				HttpMethod.GET, entity, BingSearchResponse.class);

		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End search(...)");
		}
		return resp.getBody();
	}
}
