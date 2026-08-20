/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.base.GeboComponentInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * One place data comes from, or goes to, as configured on the component
 * reporting it.
 *
 * <p>
 * The {@link #id} is unique only inside the reporting component's own
 * {@link GDataFlowMetaInfos}; use
 * {@link GDataFlowMetaInfos#qualifiedId(String)} when referencing it from a
 * {@link DataTransformationInfo}, since the flows this models cross components
 * and microservices.
 * </p>
 */
@Data
public class DataEndpoint {
	@NotNull
	private String id = null;
	@NotNull
	private String description = null;
	@NotNull
	private String product = null;

	/**
	 * The sanitized locator - scheme, host, port and path only. Never assign a raw
	 * connection string expecting it to survive: {@link #setEndpoint(String)} runs
	 * it through {@link DataEndpointLocator}, so userinfo, query and fragment are
	 * stripped before it is stored.
	 */
	@NotNull
	private String endpoint = null;

	private boolean input = false;
	private boolean output = false;
	@NotNull @NotEmpty
	private List<MetaEndpointType> types = new ArrayList<MetaEndpointType>();

	/**
	 * How far the data travels to get here. Left null when the reporting component
	 * genuinely cannot tell; the screen must then show it as undetermined rather
	 * than assume it is local.
	 */
	private DataEndpointLocality locality = null;

	/**
	 * The <em>code</em> of the secret guarding this endpoint, resolvable against
	 * {@code IGeboSecretsAccessService} - never the secret itself. An auditor needs
	 * to know which credential protects a store, not its value.
	 */
	private String secretReference = null;

	/**
	 * True when this endpoint holds or carries personal data, and so falls inside
	 * the scope of a GDPR record of processing activities.
	 */
	private boolean personalData = false;

	/**
	 * The component able to erase this endpoint's data, when one is wired - the
	 * {@code mongo-dispose-documents-component},
	 * {@code vectorization-dispose-component},
	 * {@code dispose-chunking-session-for-jobs},
	 * {@code resources-dispose-component} or {@code session-shrinker} responsible
	 * for it. Null means no disposer is registered for this endpoint, which is the
	 * expected state for a read-only source and a finding for a store this
	 * installation writes to.
	 */
	private GeboComponentInfo disposer = null;

	/**
	 * Creates an endpoint with the fields every reporting component has to supply.
	 *
	 * <p>
	 * The locator goes through {@link #setEndpoint(String)}, so passing a raw
	 * connection string here is safe - it is sanitized on the way in.
	 * </p>
	 *
	 * @param id          the id, unique within the reporting component's report
	 * @param description what an administrator should see this endpoint called
	 * @param product     the technology behind it, e.g. {@code MongoDB}, {@code Qdrant}
	 * @param locator     the raw URI or connection string; sanitized before storage
	 * @param types       the kinds of data held or exchanged here
	 */
	public static DataEndpoint of(String id, String description, String product, String locator,
			MetaEndpointType... types) {
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId(id);
		endpoint.setDescription(description);
		endpoint.setProduct(product);
		endpoint.setEndpoint(locator);
		endpoint.setTypes(new ArrayList<MetaEndpointType>(List.of(types)));
		return endpoint;
	}

	/**
	 * Stores the credential-free form of the given URI or connection string.
	 *
	 * <p>
	 * Hand-written rather than Lombok-generated so that the sanitization cannot be
	 * bypassed: the configuration objects components read from
	 * ({@code MongoConfig.getConnectionString()},
	 * {@code QdrantConfig}, {@code OpenSearchConfig}) carry credentials inline, and
	 * this model is rendered on an admin screen.
	 * </p>
	 *
	 * @param endpoint the raw configured value
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = DataEndpointLocator.sanitize(endpoint);
	}

	/**
	 * Stores a locator assembled from separate configuration fields, which is how
	 * most stores are configured - {@code OpenSearchConfig} and {@code QdrantConfig}
	 * both keep host and port apart rather than as a URI.
	 *
	 * @param scheme the protocol, e.g. {@code https} or {@code bolt}; may be null
	 * @param host   the hostname or address
	 * @param port   the port; omitted when null
	 * @param path   the database, index or collection name; may be null
	 */
	public void setEndpoint(String scheme, String host, Integer port, String path) {
		this.endpoint = DataEndpointLocator.of(scheme, host, port, path);
	}
}
