/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.architecture.scheduling.model.AbstractScheduledTask;

/**
 * Gebo.ai comment agent
 * 
 * Repository interface for scheduled tasks. This interface defines methods for querying
 * tasks that are scheduled in the future but have not been launched yet.
 * It extends MongoRepository to leverage CRUD operations on the collection of type
 * Schedulable.
 *
 * @param <Schedulable> the type of entity to handle, which must extend AbstractScheduledTask
 */
@NoRepositoryBean
public interface AbstractScheduledTaskRepository<Schedulable extends AbstractScheduledTask>
		extends MongoRepository<Schedulable, String> {
	
	/**
	 * Finds scheduled tasks that have not been launched and have a scheduled time before
	 * the given current time.
	 *
	 * @param currentTime the current date and time to compare against each scheduled task's time
	 * @param pageable    the pagination information
	 * @return a page of scheduled tasks matching the criteria
	 */
	public Page<Schedulable> findByLaunchedIsFalseAndScheduledTimeBefore(Date currentTime, Pageable pageable);
}