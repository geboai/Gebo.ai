package gebo.microservices.api.client.factory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.web.client.RestTemplate;

/**
 * The one entry point for a Java consumer of the Gebo.ai stubs: give it the
 * base url of an installation, get every generated {@code ApiClient} already
 * pointing at the right address.
 *
 * <pre>
 * GeboMicroservicesClientsFactory clients = GeboMicroservicesClientsFactory.of("https://gebo.example.com");
 *
 * ChatModelsControllerApi chatModels = new ChatModelsControllerApi(clients.brain());
 * UsersAdminControllerApi users      = new UsersAdminControllerApi(clients.heimdall());
 * </pre>
 *
 * <p>
 * The SAME code addresses a monolithic and a microservices installation. Each
 * generated client hardcodes the address its spec was scraped from
 * ({@code http://localhost:13001/brain} and friends), and which suffix a service
 * actually answers on depends on the deployment shape: behind a gateway every
 * service owns a web context ({@code /brain}, {@code /heimdall}), on a monolith
 * they all answer at the root. This factory asks the installation itself - the
 * {@code /public/ClientsTopologyProviderController} endpoint every shape
 * publishes at the same relative url - and applies the answer, so the caller
 * only ever knows ONE url.
 * </p>
 *
 * <h2>Behaviour worth knowing</h2>
 * <ul>
 * <li>The topology call happens ONCE, lazily, on the first accessor, and is
 * cached (see {@link GeboClientsTopologyResolver}); {@link #refresh()} re-asks.</li>
 * <li>Each {@code ApiClient} is created once and reused, so mutating the
 * instance an accessor returns ({@code setUserAgent}, authentication, ...)
 * sticks for every later caller. That is the point: hand the factory around
 * rather than the individual clients.</li>
 * <li>{@link #addDefaultHeader(String, String)} applies to every client, the
 * ones not created yet included - this is where an {@code Authorization} bearer
 * belongs.</li>
 * <li>Asking for a service a microservices installation does not publish throws
 * rather than guessing a url; {@link #eureka()} is the standing example, see its
 * javadoc.</li>
 * </ul>
 *
 * <p>
 * Instances are thread-safe.
 * </p>
 */
public class GeboMicroservicesClientsFactory {

	private final GeboClientsTopologyResolver resolver;

	/** Applied to every ApiClient, including the ones not created yet. */
	private final Map<String, String> defaultHeaders = new LinkedHashMap<>();

	/** serviceId -> the single ApiClient instance handed out for it. */
	private final Map<String, Object> clientsByServiceId = new LinkedHashMap<>();

	protected GeboMicroservicesClientsFactory(GeboClientsTopologyResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * A factory against one installation, using a default {@link RestTemplate}
	 * for the topology call.
	 *
	 * @param baseUrl the ONE base url of the installation - the gateway public url
	 *            in a microservices deployment, the server url in a monolithic one
	 *            (e.g. {@code https://gebo.example.com})
	 * @return the factory
	 */
	public static GeboMicroservicesClientsFactory of(String baseUrl) {
		return of(baseUrl, new RestTemplate());
	}

	/**
	 * A factory against one installation, using a caller-supplied
	 * {@link RestTemplate} for the topology call - to carry a proxy, a timeout or
	 * an interceptor the plain one does not have.
	 *
	 * <p>
	 * This template is used ONLY to read the topology. The generated clients keep
	 * building their own, because the no-arg {@code ApiClient()} constructor is
	 * what installs the message converters they need.
	 * </p>
	 *
	 * @param baseUrl the ONE base url of the installation
	 * @param restTemplate the template for the topology call
	 * @return the factory
	 */
	public static GeboMicroservicesClientsFactory of(String baseUrl, RestTemplate restTemplate) {
		return new GeboMicroservicesClientsFactory(new GeboClientsTopologyResolver(baseUrl, restTemplate));
	}

	// --- Topology ------------------------------------------------------------

	/** @return the resolver behind this factory, for base urls of services it has no accessor for */
	public GeboClientsTopologyResolver resolver() {
		return resolver;
	}

	/** @return the topology this installation published (fetched on first use) */
	public GeboClientsTopologyInfo topology() {
		return resolver.getTopology();
	}

	/** @return the common base url every client of this factory is built from */
	public String baseUrl() {
		return resolver.getBaseUrl();
	}

	/**
	 * The complete base url of one service.
	 *
	 * @param serviceId a service id, dotted or underscore form (see
	 *            {@link GeboMicroservices})
	 * @return the base url, no trailing slash
	 * @throws IllegalStateException if the installation publishes no address for it
	 */
	public String baseUrlFor(String serviceId) {
		return resolver.baseUrlFor(serviceId);
	}

	/**
	 * Re-reads the topology and re-bases every ApiClient already handed out, so
	 * references the caller is holding follow the new shape. Only needed when the
	 * installation is re-shaped while the client runs.
	 */
	public synchronized void refresh() {
		resolver.refresh();
		clientsByServiceId.forEach((serviceId, client) -> applyBasePath(serviceId, client));
	}

	/**
	 * Adds a header sent by every client of this factory - the
	 * {@code Authorization} bearer, typically. Applied to the clients already
	 * created and remembered for the ones created later.
	 *
	 * @param name the header name
	 * @param value the header value
	 * @return this factory
	 */
	public synchronized GeboMicroservicesClientsFactory addDefaultHeader(String name, String value) {
		defaultHeaders.put(name, value);
		clientsByServiceId.values().forEach(client -> applyDefaultHeaders(client));
		return this;
	}

	// --- Per-service ApiClients ----------------------------------------------
	//
	// One accessor per generated client. Each returns that service's OWN
	// ApiClient type (they are 21 unrelated classes, one per
	// gebo.microservices.api.client.<name>.invoker package), so the result plugs
	// straight into that service's Api constructors and nothing else - the
	// compiler rejects handing brain's client to a heimdall Api.

	/**
	 * @return the gateway client, based at the common base url itself - the
	 *         gateway IS the edge. It exposes no controllers of its own, so this
	 *         is scaffolding rather than something to call.
	 */
	public gebo.microservices.api.client.gateway.invoker.ApiClient gateway() {
		return client(GeboMicroservices.GATEWAY, gebo.microservices.api.client.gateway.invoker.ApiClient::new);
	}

	/**
	 * @return the eureka registry client
	 * @throws IllegalStateException in a microservices installation: the registry
	 *             sits INSIDE the deployment and is deliberately not routed at the
	 *             edge, so it has no address relative to the common base url.
	 *             Reach it by its own url instead. On a monolith it resolves like
	 *             everything else, to the base url - where it equally has nothing
	 *             to answer.
	 */
	public gebo.microservices.api.client.eureka.invoker.ApiClient eureka() {
		return client(GeboMicroservices.EUREKA, gebo.microservices.api.client.eureka.invoker.ApiClient::new);
	}

	/** @return the heimdall (AuthN/AuthZ, users, secrets) client */
	public gebo.microservices.api.client.heimdall.invoker.ApiClient heimdall() {
		return client(GeboMicroservices.HEIMDALL, gebo.microservices.api.client.heimdall.invoker.ApiClient::new);
	}

	/** @return the brain (LLMs, chat, knowledge bases, projects) client */
	public gebo.microservices.api.client.brain.invoker.ApiClient brain() {
		return client(GeboMicroservices.BRAIN, gebo.microservices.api.client.brain.invoker.ApiClient::new);
	}

	/** @return the vectorizator (embeddings, vector store) client */
	public gebo.microservices.api.client.vectorizator.invoker.ApiClient vectorizator() {
		return client(GeboMicroservices.VECTORIZATOR,
				gebo.microservices.api.client.vectorizator.invoker.ApiClient::new);
	}

	/** @return the graphicator (knowledge graph) client */
	public gebo.microservices.api.client.graphicator.invoker.ApiClient graphicator() {
		return client(GeboMicroservices.GRAPHICATOR,
				gebo.microservices.api.client.graphicator.invoker.ApiClient::new);
	}

	/** @return the chunker (tokenizer) client */
	public gebo.microservices.api.client.chunker.invoker.ApiClient chunker() {
		return client(GeboMicroservices.CHUNKER, gebo.microservices.api.client.chunker.invoker.ApiClient::new);
	}

	/** @return the git content-handler client */
	public gebo.microservices.api.client.git.invoker.ApiClient git() {
		return client(GeboMicroservices.GIT, gebo.microservices.api.client.git.invoker.ApiClient::new);
	}

	/** @return the shared-filesystem content-handler client */
	public gebo.microservices.api.client.filesystem.invoker.ApiClient filesystem() {
		return client(GeboMicroservices.FILESYSTEM, gebo.microservices.api.client.filesystem.invoker.ApiClient::new);
	}

	/** @return the uploads content-handler client */
	public gebo.microservices.api.client.uploads.invoker.ApiClient uploads() {
		return client(GeboMicroservices.UPLOADS, gebo.microservices.api.client.uploads.invoker.ApiClient::new);
	}

	/** @return the userspace content-handler client */
	public gebo.microservices.api.client.userspace.invoker.ApiClient userspace() {
		return client(GeboMicroservices.USERSPACE, gebo.microservices.api.client.userspace.invoker.ApiClient::new);
	}

	/** @return the SharePoint content-handler client */
	public gebo.microservices.api.client.sharepoint.invoker.ApiClient sharepoint() {
		return client(GeboMicroservices.SHAREPOINT, gebo.microservices.api.client.sharepoint.invoker.ApiClient::new);
	}

	/** @return the Confluence content-handler client */
	public gebo.microservices.api.client.confluence.invoker.ApiClient confluence() {
		return client(GeboMicroservices.CONFLUENCE, gebo.microservices.api.client.confluence.invoker.ApiClient::new);
	}

	/** @return the Jira content-handler client */
	public gebo.microservices.api.client.jira.invoker.ApiClient jira() {
		return client(GeboMicroservices.JIRA, gebo.microservices.api.client.jira.invoker.ApiClient::new);
	}

	/** @return the AWS S3 content-handler client */
	public gebo.microservices.api.client.awss3.invoker.ApiClient awsS3() {
		return client(GeboMicroservices.AWS_S3, gebo.microservices.api.client.awss3.invoker.ApiClient::new);
	}

	/** @return the Google Drive content-handler client */
	public gebo.microservices.api.client.googledrive.invoker.ApiClient googledrive() {
		return client(GeboMicroservices.GOOGLEDRIVE,
				gebo.microservices.api.client.googledrive.invoker.ApiClient::new);
	}

	/** @return the MCP-client content-handler client */
	public gebo.microservices.api.client.mcpclient.invoker.ApiClient mcpclient() {
		return client(GeboMicroservices.MCPCLIENT, gebo.microservices.api.client.mcpclient.invoker.ApiClient::new);
	}

	/** @return the WebDAV/CMS content-handler client */
	public gebo.microservices.api.client.webdav.invoker.ApiClient webdav() {
		return client(GeboMicroservices.WEBDAV, gebo.microservices.api.client.webdav.invoker.ApiClient::new);
	}

	/** @return the integration content-handler client */
	public gebo.microservices.api.client.integration.invoker.ApiClient integration() {
		return client(GeboMicroservices.INTEGRATION,
				gebo.microservices.api.client.integration.invoker.ApiClient::new);
	}

	/** @return the fulltextor (full-text search) client */
	public gebo.microservices.api.client.fulltextor.invoker.ApiClient fulltextor() {
		return client(GeboMicroservices.FULLTEXTOR, gebo.microservices.api.client.fulltextor.invoker.ApiClient::new);
	}

	/** @return the tyr (workflows, usage, job tracking) client */
	public gebo.microservices.api.client.tyr.invoker.ApiClient tyr() {
		return client(GeboMicroservices.TYR, gebo.microservices.api.client.tyr.invoker.ApiClient::new);
	}

	// --- Internals -----------------------------------------------------------

	/**
	 * Creates (once) and returns the ApiClient of one service, based and carrying
	 * the default headers.
	 *
	 * <p>
	 * The 21 ApiClient classes share no supertype - they are independent
	 * generated copies - so the cache is typed {@code Object} and the accessor
	 * casts back. The cast is safe by construction: a serviceId is only ever
	 * paired with its own supplier, in the accessor right above.
	 * </p>
	 *
	 * @param <T> the service ApiClient type
	 * @param serviceId the service this client addresses
	 * @param constructor the no-arg constructor of that service ApiClient - the
	 *            one that installs the message converters, unlike the
	 *            RestTemplate-taking overload
	 * @return the shared, based client
	 */
	@SuppressWarnings("unchecked")
	protected synchronized <T> T client(String serviceId, Supplier<T> constructor) {
		Object existing = clientsByServiceId.get(serviceId);
		if (existing != null) {
			return (T) existing;
		}
		T created = constructor.get();
		// Resolve BEFORE caching: an unpublished service must throw on every call,
		// not hand back a half-configured client the second time round.
		applyBasePath(serviceId, created);
		applyDefaultHeaders(created);
		clientsByServiceId.put(serviceId, created);
		return created;
	}

	/**
	 * Points one ApiClient at its resolved base url.
	 *
	 * <p>
	 * Reflective because the 21 generated {@code ApiClient} classes are unrelated
	 * types with no shared interface to call {@code setBasePath} through - the
	 * alternative would be 21 near-identical private overloads. The method is
	 * part of every generated client, so a failure here means the stubs were
	 * regenerated with a template that dropped it, which is worth surfacing
	 * loudly rather than working around.
	 * </p>
	 *
	 * @param serviceId the service the client addresses
	 * @param client that service ApiClient instance
	 */
	private void applyBasePath(String serviceId, Object client) {
		String basePath = resolver.baseUrlFor(serviceId);
		invoke(client, "setBasePath", basePath);
	}

	private void applyDefaultHeaders(Object client) {
		defaultHeaders.forEach((name, value) -> invoke(client, "addDefaultHeader", name, value));
	}

	private static void invoke(Object client, String method, String... arguments) {
		Class<?>[] signature = new Class<?>[arguments.length];
		java.util.Arrays.fill(signature, String.class);
		try {
			client.getClass().getMethod(method, signature).invoke(client, (Object[]) arguments);
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException("The generated Gebo.ai client " + client.getClass().getName()
					+ " has no usable " + method + "(String...) - the stubs were generated with an incompatible"
					+ " template.", e);
		}
	}
}
