package ai.gebo.architecture.opensearch.config;

import lombok.AllArgsConstructor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.HostnameVerificationPolicy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.function.Factory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.reactor.ssl.TlsDetails;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.TransportOptions;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Configuration
@AllArgsConstructor
public class OpenSearchInitialization {
	private final OpenSearchConfig config;
	private final static Logger LOGGER=LoggerFactory.getLogger(OpenSearchInitialization.class);

	@Bean(destroyMethod = "close")
    public OpenSearchTransport openSearchTransport() throws Exception {
		LOGGER.info("OpenSearch: transport: "+config.getProtocol()+" host:" + config.getHost()+" port:"+config.getPort());
        // IMPORTANT: con docker "latest" spesso è HTTPS
        HttpHost host = new HttpHost(
                config.getProtocol().name().toLowerCase(), // "https"
                config.getHost(),
                config.getPort()
        );

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(host),
                new UsernamePasswordCredentials(config.getUsername(), config.getPassword().toCharArray())
        );

        // DEV ONLY: trust-all (demo cert / self-signed)
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, (chains, authType) -> true)
                .build();

        ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(host);

        builder.setHttpClientConfigCallback(httpClientBuilder -> {

            ClientTlsStrategyBuilder tlsStrategyBuilder = ClientTlsStrategyBuilder.create()
                    .setSslContext(sslContext)
                    // workaround richiesto in alcuni casi con ALPN (come da esempio ufficiale)
                    .setTlsDetailsFactory(new Factory<SSLEngine, TlsDetails>() {
                        @Override
                        public TlsDetails create(SSLEngine sslEngine) {
                            return new TlsDetails(sslEngine.getSession(), sslEngine.getApplicationProtocol());
                        }
                    });

            // Skip TLS hostname (SAN) verification when configured (default true):
            // reaching OpenSearch by a docker service name (https://opensearch:9200)
            // whose demo cert has no matching SAN would otherwise fail the handshake
            // with "No subject alternative DNS name matching opensearch found".
            //
            // httpclient5 5.4+ verifies the hostname at TWO points, and BOTH must be
            // turned off - the NoopHostnameVerifier alone is not enough:
            //   * BUILTIN - inside the TLS handshake, via the SSLEngine's endpoint
            //     identification algorithm. This is the one that actually threw here.
            //     Setting the policy to CLIENT removes it.
            //   * CLIENT  - a post-handshake HostnameVerifier. NoopHostnameVerifier
            //     makes that stage accept every name.
            if (config.isNoopHostnameVerifier()) {
                LOGGER.warn("OpenSearch: TLS hostname verification DISABLED "
                        + "(ai.gebo.opensearch.noop-hostname-verifier=true) - set it to false in production");
                tlsStrategyBuilder.setHostnameVerificationPolicy(HostnameVerificationPolicy.CLIENT);
                tlsStrategyBuilder.setHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            }

            TlsStrategy tlsStrategy = tlsStrategyBuilder.build();

            PoolingAsyncClientConnectionManager connectionManager =
                    PoolingAsyncClientConnectionManagerBuilder.create()
                            .setTlsStrategy(tlsStrategy)
                            .build();

            return httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setConnectionManager(connectionManager)
                    // httpclient5 5.5+ enables automatic gzip response decompression by
                    // default. opensearch-java's transport already manages Accept-Encoding
                    // and gzip decoding itself, so letting httpclient5 also inflate the body
                    // double-handles it and throws "java.util.zip.ZipException: Not in GZIP
                    // format". Disable the client-side content compression so only the
                    // opensearch-java transport handles encoding.
                    .disableContentCompression();
        });

        return builder.build();
    }


	@Bean
	public OpenSearchClient openSearchClient(OpenSearchTransport transport) {
		return new OpenSearchClient(transport);
	}
}
