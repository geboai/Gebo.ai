package ai.gebo.fastsetup.llms.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.fastsetup.config")
@PropertySource(value = "classpath:/llms-fast-setup-library/library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class FastLLMSSetupConfig {
	List<LLMSVendor> vendors=new ArrayList<LLMSVendor>();
}
