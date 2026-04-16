package ai.gebo.architecture.opensearch.service.impl;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Configuration
public class OpenSearchHealthCheckConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenSearchHealthCheckConfig.class);

    @Bean
    public ApplicationRunner openSearchConnectionCheck(OpenSearchClient client) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                try {
                    // Light call: cluster/node info
                    var info = client.info();

                    LOGGER.info("OpenSearch connected: clusterName={}, clusterUuid={}, version={}",
                            info.clusterName(),
                            info.clusterUuid(),
                            info.version() != null ? info.version().number() : null
                    );
                } catch (Exception e) {
                    // Fail fast (startup fails)
                    LOGGER.error("OpenSearch connection check FAILED. Startup will abort.", e);
                    throw e;
                }
            }
        };
    }
}
