package ai.gebo.security.model.oauth2;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Oauth2DeliveryData {
	@Id
	private String id = null;
	private String data = null;
	private Date expires = null;
	private String remoteAddress=null;
	private String nextUri=null;
	private String tenantId=null;
	private String registrationId=null;
}
