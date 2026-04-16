package ai.gebo.security.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class IdentityUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ReactiveIdentityUtil.class);
	private final Authentication authentication;

	private IdentityUtil(String userName, List<String> roles) {

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, // principal
				null, // credentials
				roles != null
						? roles.stream().filter(x -> x != null)
								.map(r -> new SimpleGrantedAuthority(
										r.endsWith("_ROLE") ? r.toUpperCase() : r.toUpperCase() + "_ROLE"))
								.toList()
						: List.of());
		this.authentication = auth;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Sampled Authentication = " + (authentication != null ? authentication.getName() : " NULL")
					+ " in thread=>" + Thread.currentThread().getName());
		}
	}

	public static IdentityUtil create(String userName, List<String> roles) {
		return new IdentityUtil(userName, roles);
	}

	public void doAs(RunAs runnable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin doAs(...)");
		}
		final SecurityContext savedContext = SecurityContextHolder.getContext();
		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			ctx.setAuthentication(authentication);
			SecurityContextHolder.setContext(ctx);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("entering runnable with changed identity "
						+ (authentication != null ? authentication.getName() : " NULL") + " in thread=>"
						+ Thread.currentThread().getName());
			}
			runnable.run();
		} finally {
			SecurityContextHolder.setContext(savedContext);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End doAs(...)");
			}
		}
	}

	public <E extends Exception> void doAsWithException(RunAsWithException<E> runnable) throws E {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin doAs(...)");
		}
		final SecurityContext savedContext = SecurityContextHolder.getContext();
		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			ctx.setAuthentication(authentication);
			SecurityContextHolder.setContext(ctx);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("entering runnable with changed identity "
						+ (authentication != null ? authentication.getName() : " NULL") + " in thread=>"
						+ Thread.currentThread().getName());
			}
			runnable.run();
		} finally {
			SecurityContextHolder.setContext(savedContext);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End doAs(...)");
			}
		}
	}

	public <E extends Exception, E1 extends Exception> void doAsWith2Exceptions(RunAsWith2Exceptions<E, E1> runnable)
			throws E, E1 {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin doAs(...)");
		}
		final SecurityContext savedContext = SecurityContextHolder.getContext();
		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			ctx.setAuthentication(authentication);
			SecurityContextHolder.setContext(ctx);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("entering runnable with changed identity "
						+ (authentication != null ? authentication.getName() : " NULL") + " in thread=>"
						+ Thread.currentThread().getName());
			}
			runnable.run();
		} finally {
			SecurityContextHolder.setContext(savedContext);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End doAs(...)");
			}
		}
	}

	public <E extends Exception, E1 extends Exception, E2 extends Exception> void doAsWith3Exceptions(
			RunAsWith3Exceptions<E, E1, E2> runnable) throws E, E1, E2 {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin doAs(...)");
		}
		final SecurityContext savedContext = SecurityContextHolder.getContext();
		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			ctx.setAuthentication(authentication);
			SecurityContextHolder.setContext(ctx);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("entering runnable with changed identity "
						+ (authentication != null ? authentication.getName() : " NULL") + " in thread=>"
						+ Thread.currentThread().getName());
			}
			runnable.run();
		} finally {
			SecurityContextHolder.setContext(savedContext);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End doAs(...)");
			}
		}
	}

	public <T> T doRunAsWithReturn(RunAsWithReturn<T> runnable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin doRunAsWithReturn(...)");
		}
		final SecurityContext savedContext = SecurityContextHolder.getContext();
		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			ctx.setAuthentication(authentication);
			SecurityContextHolder.setContext(ctx);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("entering runnable with changed identity "
						+ (authentication != null ? authentication.getName() : " NULL") + " in thread=>"
						+ Thread.currentThread().getName());
			}
			return runnable.apply();
		} finally {
			SecurityContextHolder.setContext(savedContext);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End doRunAsWithReturn(...)");
			}
		}
	}

	public <T, E extends Exception> T doRunAsWithReturnAndException(RunAsWithReturnAndException<T, E> runnable)
			throws E {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin doRunAsWithReturnAndException(...)");
		}
		final SecurityContext savedContext = SecurityContextHolder.getContext();
		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			ctx.setAuthentication(authentication);
			SecurityContextHolder.setContext(ctx);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("entering runnable with changed identity "
						+ (authentication != null ? authentication.getName() : " NULL") + " in thread=>"
						+ Thread.currentThread().getName());
			}
			return runnable.apply();
		} finally {
			SecurityContextHolder.setContext(savedContext);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End doRunAsWithReturnAndException(...)");
			}
		}
	}
}
