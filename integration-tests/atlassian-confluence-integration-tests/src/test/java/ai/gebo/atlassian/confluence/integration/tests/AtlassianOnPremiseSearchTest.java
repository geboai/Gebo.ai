package ai.gebo.atlassian.confluence.integration.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceContentApi;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSearchPageResponseSearchResult;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;

public class AtlassianOnPremiseSearchTest {

	public static String CONFLUENCE_ONPREMISE_PASSWD = System.getenv("CONFLUENCE_ONPREMISE_PASSWD");
	public static String CONFLUENCE_ONPREMISE_SEARCH_STRING = System.getenv("CONFLUENCE_ONPREMISE_SEARCH_STRING");
	public static String CONFLUENCE_ONPREMISE_URL = System.getenv("CONFLUENCE_ONPREMISE_URL");
	public static String CONFLUENCE_ONPREMISE_USER = System.getenv("CONFLUENCE_ONPREMISE_USER");
	private static final Logger LOGGER = LoggerFactory.getLogger(AtlassianOnPremiseConfluenceIntegrationTests.class);
	RestTemplateWrapperService wrapper = new RestTemplateWrapperService();

	@org.junit.jupiter.api.Test
	public void doSearchTest() throws GeboRestIntegrationException {
		if (CONFLUENCE_ONPREMISE_SEARCH_STRING != null && CONFLUENCE_ONPREMISE_PASSWD != null
				&& CONFLUENCE_ONPREMISE_USER != null && CONFLUENCE_ONPREMISE_SEARCH_STRING != null) {
			OnPremiseConfluenceConnection connection = new OnPremiseConfluenceConnection(wrapper);
			connection.setUsername(CONFLUENCE_ONPREMISE_USER);
			connection.setPassword(CONFLUENCE_ONPREMISE_PASSWD);
			connection.setBaseUrl(CONFLUENCE_ONPREMISE_URL);
			OnPremiseConfluenceContentApi contentApi = new OnPremiseConfluenceContentApi(connection);
			OnPremiseConfluenceSearchPageResponseSearchResult data = contentApi
					.searchFullText(CONFLUENCE_ONPREMISE_SEARCH_STRING, 100);
			data.getResults().forEach(x -> {
				LOGGER.info(x.getTitle());
			});
			assertFalse(data.getResults().isEmpty(), "Found contents cannot be empty");
		}
	}

}
