package ai.gebo.llms.chat.client.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.chat.client.rest.model.ChatUIOptions;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chatui")
@Data
public class GeboChatUIConfig extends ChatUIOptions{
}
