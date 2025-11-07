package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.llms.vendors.setup.config")
@PropertySource(value = "classpath:/llms-fast-setup-library/library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class LLMSVendorsSetupConfig {
	List<LLMSVendor> vendors=new ArrayList<LLMSVendor>();
}
