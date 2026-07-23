package ai.gebo.architecture.replicator.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GeboReplicatorProperties.class)
public class ReplicatorModelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IGReplicationsMap.class)
    public IGReplicationsMap defaultReplicationsMap(GeboReplicatorProperties properties) {
        return () -> properties.toRoutingMap();
    }
}