package ai.gebo.workflows.compute.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import lombok.AllArgsConstructor;

/**
 * Closes a job and broadcasts its end, once.
 * <p>
 * Two components close jobs: the end of workflow receiver, as soon as the
 * workflow of a job computes finished, and the status daemon on its rounds
 * (including the jobs past their timeout). The job is closed by an update that
 * only matches it while it is still processing, so whichever comes first closes
 * it and is the only one to broadcast that it ended; the other one finds it
 * closed and does nothing. This holds with several instances of the service
 * too.
 */
@Component
@Scope("singleton")
@AllArgsConstructor
public class GWorkflowJobCloser {
	private static final Logger LOGGER = LoggerFactory.getLogger(GWorkflowJobCloser.class);
	private final MongoTemplate mongoTemplate;
	private final WorkflowStatusEmitter statusEmitter;

	/**
	 * Closes the job if it is still processing, and then broadcasts that it ended.
	 *
	 * @param jobCode the code of the job
	 * @return true when this call closed the job and broadcast its end, false when
	 *         the job was already closed or no longer exists
	 */
	public boolean closeAndBroadcastEnded(String jobCode) {
		final Date now = new Date();
		final Query stillProcessing = Query.query(Criteria.where("_id").is(jobCode).and("processing").is(true));
		final Update close = new Update().set("processing", false).set("finished", true).set("endDateTime", now)
				.set("dateModified", now);
		final GJobStatus closed = mongoTemplate.findAndModify(stillProcessing, close,
				FindAndModifyOptions.options().returnNew(true), GJobStatus.class);
		if (closed == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Job " + jobCode + " already closed or gone, its end is not broadcast again");
			}
			return false;
		}
		statusEmitter.broadcastEnded(closed);
		return true;
	}
}
