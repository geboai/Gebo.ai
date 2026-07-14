package ai.gebo.security.services;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;

public class ReactiveIdentityUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ReactiveIdentityUtil.class);
	private final Authentication authentication;

	private ReactiveIdentityUtil() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		this.authentication = securityContext != null ? securityContext.getAuthentication() : null;

		if (this.authentication == null || !this.authentication.isAuthenticated()) {
			LOGGER.error("ReactiveIdentityUtil.create() called without authenticated SecurityContext. thread={}",
					Thread.currentThread().getName());
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sampled Authentication = {} in thread={}", this.authentication.getName(),
						Thread.currentThread().getName());
			}
		}
	}

	public static ReactiveIdentityUtil create() {
		return new ReactiveIdentityUtil();
	}

	public Runnable wrap(Runnable runnable) {
		return () -> doAs(runnable::run);
	}

	public <T> Callable<T> wrap(Callable<T> callable) {
		return () -> doRunAsWithReturnAndException(() -> callable.call());
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

	public Scheduler wrap(Scheduler delegate) {
		return new RunAsScheduler(delegate, this);
	}

	static class RunAsScheduler implements Scheduler {

		private final Scheduler delegate;
		private final ReactiveIdentityUtil runAs;

		public RunAsScheduler(Scheduler delegate, ReactiveIdentityUtil runAs) {
			this.delegate = delegate;
			this.runAs = runAs;
		}

		@Override
		public Disposable schedule(Runnable task) {
			return delegate.schedule(runAs.wrap(task));
		}

		@Override
		public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
			return delegate.schedule(runAs.wrap(task), delay, unit);
		}

		@Override
		public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
			return delegate.schedulePeriodically(runAs.wrap(task), initialDelay, period, unit);
		}

		@Override
		public Worker createWorker() {
			return new RunAsWorker(delegate.createWorker(), runAs);
		}

		@Override
		public void dispose() {
			/*
			 * IMPORTANTE: se questo wrapper incapsula uno Scheduler condiviso del
			 * ThreadManager, NON devi disporre il delegate qui.
			 *
			 * Il ciclo di vita del delegate resta del ThreadManager.
			 */
		}

		@Override
		public boolean isDisposed() {
			return delegate.isDisposed();
		}

		private static class RunAsWorker implements Worker {

			private final Worker delegate;
			private final ReactiveIdentityUtil runAs;

			private RunAsWorker(Worker delegate, ReactiveIdentityUtil runAs) {
				this.delegate = delegate;
				this.runAs = runAs;
			}

			@Override
			public Disposable schedule(Runnable task) {
				return delegate.schedule(runAs.wrap(task));
			}

			@Override
			public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
				return delegate.schedule(runAs.wrap(task), delay, unit);
			}

			@Override
			public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
				return delegate.schedulePeriodically(runAs.wrap(task), initialDelay, period, unit);
			}

			@Override
			public void dispose() {
				delegate.dispose();
			}

			@Override
			public boolean isDisposed() {
				return delegate.isDisposed();
			}
		}
	}

}
