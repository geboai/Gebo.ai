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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.multithreading.IGRunnableFactory;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.model.ProjectEndpointScheduledTask;
import ai.gebo.architecture.scheduling.repository.ProjectEndpointScheduledTaskRepository;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.core.messages.PublishProjectEndpointAckMessagePayload;
import ai.gebo.core.messages.PublishProjectEndpointMessagePayload;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;

/**
 * Implementation of the scheduling time service, handling scheduling tasks for
 * project endpoints.
 * AI generated comments
 */
@Component
@Scope("singleton")
public class GSchedulingTimeServiceImpl implements IGSchedulingTimeService, IGMessageEmitter, IGMessageReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GSchedulingTimeServiceImpl.class);

    @Autowired
    protected JobStatusRepository statusRepository;
    @Autowired
    protected TimesCalculatorService timesCalculationService;
    @Autowired
    protected ProjectEndpointScheduledTaskRepository scheduledProjectEndpointRepo;
    @Autowired
    protected IGPersistentObjectManager persistentObjectManager;
    @Autowired
    protected IGMessageBroker broker;

    /**
     * Default constructor.
     */
    public GSchedulingTimeServiceImpl() {

    }

    /**
     * Retrieves time display values for the given programmed tables.
     * 
     * @param programmedTable The list of programmed tables to retrieve display
     *                        values for.
     * @return A list of display time values for the programmed tables.
     */
    @Override
    public List<String> getDisplayTimeValues(@NotNull List<ReindexingProgrammedTable> programmedTable) {
        return timesCalculationService.getDisplayTimeValues(programmedTable);
    }

    /**
     * Programs the next run for the given project endpoint at the specified date
     * and time.
     * 
     * @param endpoint The project endpoint to be scheduled.
     * @param nextRun  The date and time for the next run.
     */
    private void programNextRun(GProjectEndpoint endpoint, Date nextRun) {
        LOGGER.info("Begin programNextRun(" + endpoint.getCode() + "," + nextRun + ")");
        GObjectRef<GProjectEndpoint> endpointRef = GObjectRef.of(endpoint);
        ProjectEndpointScheduledTask task = new ProjectEndpointScheduledTask();
        task.setScheduledTime(nextRun);
        task.setLaunched(false);
        task.setScheduledObject(endpointRef);
        scheduledProjectEndpointRepo.insert(task);
        LOGGER.info("End programNextRun(" + endpoint.getCode() + "," + nextRun + ")");
    }

    /**
     * Manages the scheduling of a project endpoint for publication.
     * 
     * @param endpoint The project endpoint to manage for scheduling.
     */
    @Override
    public void managePublishScheduling(GProjectEndpoint endpoint) {
        LOGGER.info("Begin manage manageScheduling(...)");
        clearProgramming(endpoint);
        if (endpoint.getPublished() != null && endpoint.getPublished()) {
            LOGGER.info("Manage schedule time for endpoint with code:" + endpoint.getCode());
            boolean runNow = this.isRunningInLate(endpoint);
            Date lastRun = getLastRun(endpoint);
            Date ts = null;
            if (runNow) {
                LOGGER.info("Schedule:" + endpoint.getCode() + " in late because latest scheduled not run");
                GregorianCalendar gc = new GregorianCalendar();
                gc.add(GregorianCalendar.SECOND, 30);
                ts = gc.getTime();
            } else {
                Date nextRun = timesCalculationService.getNextRunDateTime(endpoint, lastRun);
                if (nextRun != null) {
                    ts = nextRun;
                }
            }
            if (ts != null) {
                programNextRun(endpoint, ts);
            }

        } else {
            LOGGER.info("Contents of endpoint :" + endpoint.getCode() + " are not under scheduling for publication");
        }
        LOGGER.info("End manage manageScheduling(...)");
    }

    /**
     * Retrieves the last run date for the given project endpoint.
     * 
     * @param endpoint The project endpoint to get the last run date for.
     * @return The last run date, or null if none found.
     */
    private Date getLastRun(GProjectEndpoint endpoint) {
        Sort sort = Sort.by(Sort.Direction.DESC, "scheduledTime");
        Pageable pageable = PageRequest.of(0, 1, sort);

        Page<ProjectEndpointScheduledTask> found = scheduledProjectEndpointRepo
                .findByLaunchedIsTrueAndScheduledObject(GObjectRef.of(endpoint), pageable);
        return found.isEmpty() ? null : found.getContent().get(0).getScheduledTime();
    }

    /**
     * Clears the programming of tasks for the given project endpoint.
     * 
     * @param endpoint The endpoint for which to clear programming.
     */
    private void clearProgramming(GProjectEndpoint endpoint) {
        GObjectRef<GProjectEndpoint> endpointRef = GObjectRef.of(endpoint);
        scheduledProjectEndpointRepo.deleteByScheduledObjectAndLaunchedIsFalse(endpointRef);
    }

    /**
     * Determines if the project endpoint is running late.
     * 
     * @param endpoint The project endpoint to check.
     * @return True if running late, false otherwise.
     */
    private boolean isRunningInLate(GProjectEndpoint endpoint) {
        Date maximumPastEndRunningSchedule = timesCalculationService
                .getMaximumPastStartDateTimeRunningSchedule(endpoint);
        LOGGER.info("For: " + endpoint.getCode() + " maximum past time of schedule:" + maximumPastEndRunningSchedule);
        if (maximumPastEndRunningSchedule == null)
            return false;
        else
            return hasRunnedAfter(endpoint, maximumPastEndRunningSchedule);
    }

    /**
     * Checks if the project endpoint has run after the specified date.
     * 
     * @param endpoint                      The project endpoint to check.
     * @param maximumPastEndRunningSchedule The maximum past end running schedule
     *                                      date.
     * @return True if it has run after, false otherwise.
     */
    private boolean hasRunnedAfter(GProjectEndpoint endpoint, Date maximumPastEndRunningSchedule) {
        Date latestRun = this.latestRun(endpoint);
        LOGGER.info("For: " + endpoint.getCode() + " last runned at:" + latestRun);
        return latestRun != null && latestRun.before(maximumPastEndRunningSchedule);
    }

    /**
     * Retrieves the latest run date for the given project endpoint.
     * 
     * @param endpoint The project endpoint to get the latest run date for.
     * @return The latest run date.
     */
    private Date latestRun(GProjectEndpoint endpoint) {
        Optional<GJobStatus> data = statusRepository
                .findFirstByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeOrderByStartDateTimeDesc(
                        endpoint.getClass().getName(), endpoint.getCode());
        return data.isPresent() ? data.get().getStartDateTime() : null;
    }

    /**
     * Handles the publishing rescheduling for a given project endpoint.
     * 
     * @param original         The original project endpoint.
     * @param runnableFactory  The runnable factory for handling the scheduling.
     */
    protected void handlePublishingRescheduling(GProjectEndpoint original, IGRunnableFactory runnableFactory) {
        managePublishScheduling(original);
    }

    /**
     * Scheduled method that runs periodically to check and execute scheduled tasks.
     * Runs with an initial delay and a fixed rate interval.
     */
    @Scheduled(initialDelay = 20000, fixedRate = 60000) // 1 minute
    @Override
    public void scheduleTick() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin scheduleTick()");
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int page = 0;
        int nTasks = 2;
        Page<ProjectEndpointScheduledTask> scheduledTasks = null;
        do {
            Pageable pageable = Pageable.ofSize(nTasks);
            pageable.withPage(page);
            scheduledTasks = scheduledProjectEndpointRepo.findByLaunchedIsFalseAndScheduledTimeBefore(timestamp,
                    pageable);
            for (ProjectEndpointScheduledTask projectEndpointScheduledTask : scheduledTasks.getContent()) {
                try {
                    asyncRun(projectEndpointScheduledTask);
                } catch (Throwable e) {
                    LOGGER.error("Error scheduled publication", e);
                }
            }
            page++;
        } while (scheduledTasks.hasNext());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("End scheduleTick()");
        }
    }

    /**
     * Asynchronously runs the given scheduled task.
     * 
     * @param projectEndpointScheduledTask The scheduled task to run.
     * @throws GeboPersistenceException In case of persistence errors.
     */
    private void asyncRun(ProjectEndpointScheduledTask projectEndpointScheduledTask) throws GeboPersistenceException {
        GObjectRef<GProjectEndpoint> ref = projectEndpointScheduledTask.getScheduledObject();
        PublishProjectEndpointMessagePayload payload = new PublishProjectEndpointMessagePayload();
        payload.setProjectEndpoint(ref);
        payload.setCorrelationId(projectEndpointScheduledTask.getId());
        GMessageEnvelope<PublishProjectEndpointMessagePayload> envelope = GMessageEnvelope.newMessageFrom(this,
                payload);
        envelope.setTargetModule(GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_MODULE);
        envelope.setTargetComponent(GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_COMPONENT);
        this.broker.accept(envelope);
    }

    /**
     * Retrieves the messaging module ID for this component.
     * 
     * @return The messaging module ID.
     */
    @Override
    public String getMessagingModuleId() {

        return GStandardModulesConstraints.SCHEDULER_MODULE;
    }

    /**
     * Retrieves the messaging system ID for this component.
     * 
     * @return The messaging system ID.
     */
    @Override
    public String getMessagingSystemId() {
        return GStandardModulesConstraints.SCHEDULER_COMPONENT;
    }

    /**
     * Retrieves the component type of this module.
     * 
     * @return The system component type.
     */
    @Override
    public SystemComponentType getComponentType() {

        return SystemComponentType.APPLICATION_COMPONENT;
    }

    /**
     * Retrieves the list of accepted payload types for this receiver.
     * 
     * @return A list of accepted payload type names.
     */
    @Override
    public List<String> getAcceptedPayloadTypes() {

        return List.of(PublishProjectEndpointAckMessagePayload.class.getName());
    }

    /**
     * Indicates whether this receiver accepts every payload type.
     * 
     * @return False, as it does not accept every payload type.
     */
    @Override
    public boolean isAcceptEveryPayloadType() {

        return false;
    }

    /**
     * Accepts and processes a message envelope.
     * 
     * @param msg The message envelope to process.
     */
    @Override
    public void accept(GMessageEnvelope msg) {
        if (msg.getPayload() instanceof PublishProjectEndpointAckMessagePayload) {
            try {
                PublishProjectEndpointAckMessagePayload payload = (PublishProjectEndpointAckMessagePayload) msg
                        .getPayload();

                GProjectEndpoint endpoint = this.persistentObjectManager.findByReference(payload.getProjectEndpoint(),
                        GProjectEndpoint.class);

                LOGGER.info("Handling PublishProjectEndpointAckMessagePayload for correlationId: "
                        + payload.getCorrelationId());
                Optional<ProjectEndpointScheduledTask> task = this.scheduledProjectEndpointRepo
                        .findById(payload.getCorrelationId());
                if (task.isPresent()) {
                    ProjectEndpointScheduledTask taskObject = task.get();
                    taskObject.setLaunched(true);
                    taskObject.setLaunchedTime(new Timestamp(System.currentTimeMillis()));
                    taskObject.setJobId(payload.getJobId());
                    this.scheduledProjectEndpointRepo.save(task.get());
                    LOGGER.info("Scheduled task with id: " + payload.getCorrelationId() + " marked as launched");
                    this.managePublishScheduling(endpoint);
                }
            } catch (GeboPersistenceException e) {
                LOGGER.error("Exception while handling PublishProjectEndpointAckMessagePayload", e);
            }
        }
    }

    /**
     * Retrieves the list of emitted payload types by this emitter.
     * 
     * @return A list of emitted payload type names.
     */
    @Override
    public List<String> getEmittedPayloadTypes() {

        return List.of(PublishProjectEndpointMessagePayload.class.getName());
    }

}