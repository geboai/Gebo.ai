package ai.gebo.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import lombok.Data;

/**
 * AI generated comments
 * Configuration class for loading OAuth2 provider settings from a custom
 * YAML configuration file.
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.oauth2.library")
@PropertySource(value = "classpath:/oauth2-library/library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class Oauth2ProvidersLibrary {

    /**
     * List to store the configuration settings for each OAuth2 provider available.
     */
    List<Oauth2ProviderConfig> providers = new ArrayList<>();
}
