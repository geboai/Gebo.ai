package ai.gebo.security.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeneratedApiKey extends GBaseObject {
	@NotNull
	private String apiKey = null;
	@NotNull
	private Date expiration = null;
	@NotNull
	private String impersonatedUser = null;
	/**
	 * The id the key's token carries as {@code jti}: the token is accepted while
	 * a record with this id exists, so deleting the record revokes the key. Kept
	 * out of the REST representation: the client only needs the key itself.
	 */
	@Indexed
	@JsonIgnore
	private String tokenId = null;
}
