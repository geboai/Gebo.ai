package gebo.microservices.api.client.factory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * One entry of the clients topology: what to append to the common base url to
 * reach one service.
 *
 * <p>
 * Hand-written rather than reused from {@code ai.gebo.architecture.environment}
 * on purpose - the client stubs are standalone artifacts, consumable without a
 * single Gebo.ai backend jar on the classpath. Unknown properties are ignored so
 * a newer server can add fields without breaking an older client.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeboServiceWebContextInfo {

	private String serviceId;
	private String relativeContextUrl;

	public GeboServiceWebContextInfo() {
	}

	public GeboServiceWebContextInfo(String serviceId, String relativeContextUrl) {
		this.serviceId = serviceId;
		this.relativeContextUrl = relativeContextUrl;
	}

	/** @return the service this entry addresses, e.g. {@code brain_gebo_ai} or {@code default} */
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the web context to append to the common base url, e.g.
	 *         {@code /brain}, or {@code ""} when the service answers at the base
	 *         url itself
	 */
	public String getRelativeContextUrl() {
		return relativeContextUrl;
	}

	public void setRelativeContextUrl(String relativeContextUrl) {
		this.relativeContextUrl = relativeContextUrl;
	}

	@Override
	public String toString() {
		return serviceId + " -> '" + relativeContextUrl + "'";
	}
}
