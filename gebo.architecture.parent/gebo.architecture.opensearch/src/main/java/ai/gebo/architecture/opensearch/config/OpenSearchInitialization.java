package ai.gebo.architecture.opensearch.config;
import lombok.AllArgsConstructor;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.core5.http.HttpHost;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Configuration
@AllArgsConstructor
public class OpenSearchInitialization {

    private final OpenSearchConfig config;

    @Bean(destroyMethod = "close")
    public OpenSearchTransport openSearchTransport() {

        HttpHost host = new HttpHost(
                config.getProtocol().name().toLowerCase(),
                config.getHost(),
                config.getPort()
        );

        BasicCredentialsProvider creds = new BasicCredentialsProvider();
        creds.setCredentials(
                new AuthScope(host),
                new UsernamePasswordCredentials(
                        config.getUsername(),
                        config.getPassword().toCharArray()
                )
        );

        return ApacheHttpClient5TransportBuilder.builder(host)
                .setMapper(new JacksonJsonpMapper())
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(creds)
                )
                .build();
    }

    @Bean
    public OpenSearchClient openSearchClient(OpenSearchTransport transport) {
        return new OpenSearchClient(transport);
    }
}
