package ai.gebo.security.services;

public class SecurityAuditConstraints {

	public static final String CORRELATION_ID = "correlationId";
	public static final String CLIENT_IP = "clientIp";
	public static final String HTTP_METHOD = "httpMethod";
	public static final String REQUEST_URI = "requestUri";
	public static final String USERID = "userId";
	public static final String SECURITY_LOG = "security-log";
	/**
	 * MDC keys under which Spring Boot's tracing (Micrometer Tracing's
	 * {@code Slf4JEventListener}) publishes the current trace and span; the same
	 * keys the application log pattern prints.
	 */
	public static final String TRACE_ID = "traceId";
	public static final String SPAN_ID = "spanId";
	/** Response header returning the request's correlation id to the caller. */
	public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

}
