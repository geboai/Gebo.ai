package ai.gebo.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class ReactiveIdentityUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ReactiveIdentityUtil.class);
	private final Authentication authentication;

	private ReactiveIdentityUtil() {
		authentication = SecurityContextHolder.getContext() != null
				? SecurityContextHolder.getContext().getAuthentication()
				: null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Sampled Authentication = " + (authentication != null ? authentication.getName() : " NULL")
					+ " in thread=>" + Thread.currentThread().getName());
		}
	}

	public static ReactiveIdentityUtil create() {
		return new ReactiveIdentityUtil();
	}

	@FunctionalInterface
	public static interface RunAs {
		public void run();
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

	@FunctionalInterface
	public static interface RunAsWithException<E extends Exception> {
		public void run() throws E;
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

	@FunctionalInterface
	public static interface RunAsWith2Exceptions<E extends Exception, E1 extends Exception> {
		public void run() throws E, E1;
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

	@FunctionalInterface
	public static interface RunAsWith3Exceptions<E extends Exception, E1 extends Exception, E2 extends Exception> {
		public void run() throws E, E1, E2;
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

	@FunctionalInterface
	public static interface RunAsWithReturn<T> {
		public T apply();
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

	@FunctionalInterface
	public static interface RunAsWithReturnAndException<T, E extends Exception> {
		public T apply() throws E;
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
