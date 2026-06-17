package ai.gebo.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ai.gebo.userflows")
@Data
public class GeboUserWorkflowsConfig {
	
	private boolean activationWorkflowEnabled = false;
	private boolean forgotPasswordWorkflowEnabled = false;
	private int ticketValidityTimeoutMS=60*1000*10;//10 minutes
	private String mailServer;
	private Integer mailPort;
	private String mailUserName;
	private String mailPassword;
	private String mailSender;
	private String geboReachableBaseAddress;
}
