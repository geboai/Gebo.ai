/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Repository interface for managing job statuses in a MongoDB data source.
 * Provides methods to handle CRUD operations and custom queries related to job statuses.
 */
public interface JobStatusRepository extends IGBaseMongoDBRepository<GJobStatus> {
	
	/**
	 * Returns the managed entity type by the repository.
	 * @return the class type of GJobStatus.
	 */
	@Override
	default Class<GJobStatus> getManagedType() {
		return GJobStatus.class;
	}

	/**
	 * Finds job status items by project endpoint reference and job type, with pagination support.
	 * @param ref the project endpoint reference.
	 * @param jobType the type of the job.
	 * @param pageable pagination information.
	 * @return a page of job status items.
	 */
	public default Page<GJobStatusItem> findByProjectEndpointReferenceAndJobType(GObjectRef<GProjectEndpoint> ref,
			JobType jobType, Pageable pageable) {
		return findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndJobType(ref.getClassName(),
				ref.getCode(), jobType, pageable);
	}

	/**
	 * Finds job status items by class name, code, and job type with pagination support.
	 * @param className the class name of the project endpoint reference.
	 * @param code the code of the project endpoint reference.
	 * @param jobType the type of the job.
	 * @param pageable pagination information.
	 * @return a page of job status items.
	 */
	public Page<GJobStatusItem> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndJobType(
			String className, String code, JobType jobType, Pageable pageable);

	/**
	 * Deletes job status items by project endpoint reference.
	 * @param ref the project endpoint reference.
	 */
	public default void deleteByProjectEndpointReference(GObjectRef<GProjectEndpoint> ref) {
		this.deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(ref.getClassName(),
				ref.getCode());
	}

	/**
	 * Deletes job status items based on class name and code.
	 * @param className the class name of the project endpoint reference.
	 * @param code the code of the project endpoint reference.
	 */
	public void deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(String className, String code);

	/**
	 * Finds job status items by class name and job type with pagination support.
	 * @param className the class name of the project endpoint reference.
	 * @param jobType the type of the job.
	 * @param pageable pagination information.
	 * @return a page of job status items.
	 */
	public Page<GJobStatusItem> findByProjectEndpointReferenceClassNameAndJobType(String className, JobType jobType,
			Pageable pageable);

	/**
	 * Finds job status items by job type with pagination support.
	 * @param jobType the type of the job.
	 * @param pageable pagination information.
	 * @return a page of job status items.
	 */
	public Page<GJobStatusItem> findByJobType(JobType jobType, Pageable pageable);

	/**
	 * Finds job status items that are currently being processed.
	 * @return a stream of job status items.
	 */
	public Stream<GJobStatusItem> findByProcessingTrue();

	/**
	 * Finds job status items by class name, code, and where processing is true.
	 * @param className the class name of the project endpoint reference.
	 * @param code the code of the project endpoint reference.
	 * @return a list of job status items.
	 */
	public List<GJobStatusItem> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndProcessingTrue(
			String className, String code);

	/**
	 * Finds job statuses by example.
	 * @param example an example entity to match against.
	 * @return a stream of job statuses.
	 */
	public Stream<GJobStatus> findBy(Example<GJobStatus> example);

	/**
	 * Finds job statuses by class name and code.
	 * @param name the class name of the project endpoint reference.
	 * @param code the code of the project endpoint reference.
	 * @return a stream of job statuses.
	 */
	public Stream<GJobStatus> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(String name,
			String code);

	/**
	 * Finds the latest job status by class name and code, ordered by descending start date.
	 * @param name the class name of the project endpoint reference.
	 * @param code the code of the project endpoint reference.
	 * @return an optional job status.
	 */
	public Optional<GJobStatus> findFirstByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeOrderByStartDateTimeDesc(
			String name, String code);
}