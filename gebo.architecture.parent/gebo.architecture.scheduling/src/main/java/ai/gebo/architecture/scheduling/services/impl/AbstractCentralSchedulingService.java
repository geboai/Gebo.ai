/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.scheduling.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.scheduling.model.ProjectEndpointScheduledTask;
import ai.gebo.architecture.scheduling.repository.ProjectEndpointScheduledTaskRepository;
import ai.gebo.core.messages.GRescheduleProjectEndpointMessagePayload;
import ai.gebo.core.messages.PublishProjectEndpointAckMessagePayload;
import ai.gebo.core.messages.PublishProjectEndpointMessagePayload;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * The single, centralized publish scheduler: one live instance system-wide
 * (the monolith, or tyr under microservices - see
 * {@link MonolithicCentralSchedulingService}/{@link ClusteredCentralSchedulingService}),
 * addressed at the fixed {@code scheduler-module.scheduler-component} identity
 * every content-handler already targets when it sends a
 * {@link GRescheduleProjectEndpointMessagePayload} (see
 * {@code GAbstractSystemsArchitectureController.processReschedule}).
 *
 * <p>
 * Replaces the previous per-content-handler {@code GSchedulingTimeServiceImpl}
 * self-loop design, fixing two problems it had: (1) each content-handler ran
 * its own scheduler against its own local Mongo with no coordination, and (2)
 * the *next* run was only ever computed when the *previous* run's ack arrived
 * - so a single lost ack silently and permanently stalled all future
 * scheduling for that endpoint. Here, the next run is computed and persisted
 * immediately at dispatch time (in {@link #scheduleTick()}); the ack ({@link
 * #handleAck}) is now purely observational bookkeeping.
 * </p>
 */
public abstract class AbstractCentralSchedulingService implements IGMessageEmitter, IGMessageReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCentralSchedulingService.class);

	protected final TimesCalculatorService timesCalculationService;
	protected final ProjectEndpointScheduledTaskRepository scheduledProjectEndpointRepo;
	protected final IGMessageBroker broker;
	protected final IMessageEnvelopeFactory envelopeFactory;
	private final int tickPageSize;

	// tickPageSize is resolved via @Value on each concrete subclass's own
	// constructor (Spring only honours @Value on the constructor it actually
	// invokes), then threaded through here.
	protected AbstractCentralSchedulingService(TimesCalculatorService timesCalculationService,
			ProjectEndpointScheduledTaskRepository scheduledProjectEndpointRepo, IGMessageBroker broker,
			IMessageEnvelopeFactory envelopeFactory, int tickPageSize) {
		this.timesCalculationService = timesCalculationService;
		this.scheduledProjectEndpointRepo = scheduledProjectEndpointRepo;
		this.broker = broker;
		this.envelopeFactory = envelopeFactory;
		this.tickPageSize = tickPageSize;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.SCHEDULER_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.SCHEDULER_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(PublishProjectEndpointMessagePayload.class.getName());
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(GRescheduleProjectEndpointMessagePayload.class.getName(),
				PublishProjectEndpointAckMessagePayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void accept(GMessageEnvelope envelope) {
		Object payload = envelope.getPayload();
		if (payload instanceof GRescheduleProjectEndpointMessagePayload reschedule) {
			handleReschedule(reschedule.getCentralizedProjectEndpoint(), envelope.getSourceModule());
		} else if (payload instanceof PublishProjectEndpointAckMessagePayload ack) {
			handleAck(ack);
		}
	}

	/**
	 * Handles a reschedule request: clears any not-yet-launched task for this
	 * endpoint (superseded by this newer request), then - if the endpoint is
	 * published - computes and persists its next due run, denormalizing the
	 * flattened endpoint and the requesting handler's module id onto the task so
	 * {@link #scheduleTick()} needs no further lookups.
	 */
	private void handleReschedule(GCentralizedProjectEndpoint endpoint, String sourceModule) {
		if (endpoint == null) {
			return;
		}
		LOGGER.info("Begin handleReschedule({})", endpoint.getCode());
		GObjectRef<GProjectEndpoint> ref = endpoint.getRemoteProjectReference();
		clearProgramming(ref);
		if (endpoint.getPublished() != null && endpoint.getPublished()) {
			Date lastRun = getLastScheduledRun(ref);
			Date ts = computeNextRun(endpoint, lastRun);
			if (ts != null) {
				programNextRun(endpoint, sourceModule, ts);
			}
		} else {
			LOGGER.info("Endpoint {} is not under scheduling for publication", endpoint.getCode());
		}
		LOGGER.info("End handleReschedule({})", endpoint.getCode());
	}

	/**
	 * Mirrors the previous design's catch-up behaviour: if the endpoint's
	 * schedule has a run that should already have started, program a run 30s
	 * out; otherwise defer to the endpoint's own programmed frequency.
	 */
	private Date computeNextRun(GCentralizedProjectEndpoint endpoint, Date lastRun) {
		Date maximumPastStart = timesCalculationService.getMaximumPastStartDateTimeRunningSchedule(endpoint);
		boolean runningLate = maximumPastStart != null && (lastRun == null || lastRun.before(maximumPastStart));
		if (runningLate) {
			LOGGER.info("Endpoint {} is late - scheduling a catch-up run", endpoint.getCode());
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(GregorianCalendar.SECOND, 30);
			return gc.getTime();
		}
		return timesCalculationService.getNextRunDateTime(endpoint, lastRun);
	}

	private Date getLastScheduledRun(GObjectRef<GProjectEndpoint> ref) {
		Sort sort = Sort.by(Sort.Direction.DESC, "scheduledTime");
		Pageable pageable = PageRequest.of(0, 1, sort);
		Page<ProjectEndpointScheduledTask> found = scheduledProjectEndpointRepo.findByLaunchedIsTrueAndScheduledObject(ref,
				pageable);
		return found.isEmpty() ? null : found.getContent().get(0).getScheduledTime();
	}

	private void clearProgramming(GObjectRef<GProjectEndpoint> ref) {
		scheduledProjectEndpointRepo.deleteByScheduledObjectAndLaunchedIsFalse(ref);
	}

	private void programNextRun(GCentralizedProjectEndpoint endpoint, String sourceModule, Date nextRun) {
		LOGGER.info("Begin programNextRun({},{})", endpoint.getCode(), nextRun);
		ProjectEndpointScheduledTask task = new ProjectEndpointScheduledTask();
		task.setScheduledTime(nextRun);
		task.setLaunched(false);
		task.setScheduledObject(endpoint.getRemoteProjectReference());
		task.setSourceModule(sourceModule);
		task.setCentralizedProjectEndpoint(endpoint);
		scheduledProjectEndpointRepo.insert(task);
		LOGGER.info("End programNextRun({},{})", endpoint.getCode(), nextRun);
	}

	/**
	 * Purely observational: records that a dispatched run actually launched a
	 * job. No longer drives scheduling - the next run is already programmed by
	 * {@link #scheduleTick()} at dispatch time, so a lost or delayed ack no
	 * longer stalls future scheduling for the endpoint.
	 */
	private void handleAck(PublishProjectEndpointAckMessagePayload payload) {
		Optional<ProjectEndpointScheduledTask> task = scheduledProjectEndpointRepo.findById(payload.getCorrelationId());
		if (task.isPresent()) {
			ProjectEndpointScheduledTask taskObject = task.get();
			taskObject.setJobId(payload.getJobId());
			scheduledProjectEndpointRepo.save(taskObject);
			LOGGER.info("Recorded ack for scheduled task {} - jobId {}", payload.getCorrelationId(),
					payload.getJobId());
		}
	}

	/**
	 * The periodic tick. Concrete subclasses decide whether it may actually run
	 * on this instance (see {@link #canRunTick()}) - the monolith always can (a
	 * single JVM), while the clustered microservices variant only runs it while
	 * holding the leader lease.
	 */
	@Scheduled(initialDelay = 20000, fixedRate = 60000)
	public void scheduleTick() {
		if (!canRunTick()) {
			return;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin scheduleTick()");
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int page = 0;
		Page<ProjectEndpointScheduledTask> dueTasks;
		do {
			Pageable pageable = PageRequest.of(page, tickPageSize);
			dueTasks = scheduledProjectEndpointRepo.findByLaunchedIsFalseAndScheduledTimeBefore(now, pageable);
			for (ProjectEndpointScheduledTask task : dueTasks.getContent()) {
				try {
					fireTask(task);
				} catch (Throwable e) {
					LOGGER.error("Error dispatching scheduled publication for task " + task.getId(), e);
				}
			}
			page++;
		} while (dueTasks.hasNext());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End scheduleTick()");
		}
	}

	/**
	 * Whether this instance is allowed to actually execute the tick body.
	 */
	protected abstract boolean canRunTick();

	private void fireTask(ProjectEndpointScheduledTask task) {
		// Mark launched immediately (not on ack) so the next tick doesn't
		// re-dispatch it, then reprogram the *following* run right away - the fix
		// for the old design's lost-ack stall.
		task.setLaunched(true);
		task.setLaunchedTime(new Timestamp(System.currentTimeMillis()));
		scheduledProjectEndpointRepo.save(task);

		dispatchPublish(task);

		GCentralizedProjectEndpoint endpoint = task.getCentralizedProjectEndpoint();
		if (endpoint != null && endpoint.getPublished() != null && endpoint.getPublished()) {
			Date next = timesCalculationService.getNextRunDateTime(endpoint, task.getScheduledTime());
			if (next != null) {
				programNextRun(endpoint, task.getSourceModule(), next);
			}
		}
	}

	private void dispatchPublish(ProjectEndpointScheduledTask task) {
		PublishProjectEndpointMessagePayload payload = new PublishProjectEndpointMessagePayload();
		GCentralizedProjectEndpoint endpoint = task.getCentralizedProjectEndpoint();
		payload.setProjectEndpoint(endpoint != null ? endpoint.getRemoteProjectReference() : task.getScheduledObject());
		payload.setCorrelationId(task.getId());
		GMessageEnvelope<PublishProjectEndpointMessagePayload> envelope = envelopeFactory.newMessageFrom(this, payload);
		envelope.setTargetModule(task.getSourceModule());
		envelope.setTargetComponent(GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_COMPONENT);
		this.broker.accept(envelope);
	}

}
