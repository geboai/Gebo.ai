package ai.gebo.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.security.model.Oauth2ProviderConfig;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ai.gebo.oauth2.library")
@PropertySource(value = "classpath:/oauth2-library/library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class Oauth2ProvidersLibrary {
	List<Oauth2ProviderConfig> providers = new ArrayList<>();
}
