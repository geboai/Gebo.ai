package gebo.microservices.api.client.factory;

import java.util.List;

/**
 * The service ids the Gebo.ai stubs address, in the canonical dot-free form the
 * clients topology keys its entries by.
 *
 * <p>
 * These are the {@code spring.application.name}s of the deployables with
 * {@code '.'} replaced by {@code '_'} - the same normalisation the backend's
 * {@code GeboMicroservice.normalizeName(String)} applies - so a caller may pass
 * either form anywhere a service id is accepted.
 * </p>
 */
public final class GeboMicroservices {

	public static final String GATEWAY = "gateway_gebo_ai";
	public static final String EUREKA = "eureka_gebo_ai";
	public static final String HEIMDALL = "heimdall_gebo_ai";
	public static final String BRAIN = "brain_gebo_ai";
	public static final String VECTORIZATOR = "vectorizator_gebo_ai";
	public static final String GRAPHICATOR = "graphicator_gebo_ai";
	public static final String CHUNKER = "chunker_gebo_ai";
	public static final String GIT = "git_gebo_ai";
	public static final String FILESYSTEM = "filesystem_gebo_ai";
	public static final String UPLOADS = "uploads_gebo_ai";
	public static final String USERSPACE = "userspace_gebo_ai";
	public static final String SHAREPOINT = "sharepoint_gebo_ai";
	public static final String CONFLUENCE = "confluence_gebo_ai";
	public static final String JIRA = "jira_gebo_ai";
	public static final String AWS_S3 = "aws_s3_gebo_ai";
	public static final String GOOGLEDRIVE = "googledrive_gebo_ai";
	public static final String MCPCLIENT = "mcpclient_gebo_ai";
	public static final String WEBDAV = "webdav_gebo_ai";
	public static final String INTEGRATION = "integration_gebo_ai";
	public static final String FULLTEXTOR = "fulltextor_gebo_ai";
	public static final String TYR = "tyr_gebo_ai";

	/** Every service this module ships a client for, in declaration order. */
	public static final List<String> ALL = List.of(GATEWAY, EUREKA, HEIMDALL, BRAIN, VECTORIZATOR, GRAPHICATOR,
			CHUNKER, GIT, FILESYSTEM, UPLOADS, USERSPACE, SHAREPOINT, CONFLUENCE, JIRA, AWS_S3, GOOGLEDRIVE,
			MCPCLIENT, WEBDAV, INTEGRATION, FULLTEXTOR, TYR);

	private GeboMicroservices() {
	}

	/**
	 * Canonicalises a service name: {@code '.'} to {@code '_'}, so
	 * {@code brain.gebo.ai} and {@code brain_gebo_ai} are the same key.
	 *
	 * @param serviceId a service name in either form; may be null
	 * @return the canonical id, or {@code null}
	 */
	public static String normalizeServiceId(String serviceId) {
		if (serviceId == null) {
			return null;
		}
		String trimmed = serviceId.trim();
		return trimmed.isEmpty() ? null : trimmed.replace('.', '_');
	}
}
