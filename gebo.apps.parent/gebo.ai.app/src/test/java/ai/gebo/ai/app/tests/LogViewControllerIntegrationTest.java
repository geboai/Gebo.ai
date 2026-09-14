/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import ai.gebo.architecture.utils.DataPage;
import ai.gebo.core.controllers.LogViewController;
import ai.gebo.core.controllers.LogViewController.JobsEntriesFilter;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * Covers the single, searchable and sortable job entries listing backing the
 * administration logs page: the entries of several datasource types are listed
 * together, restricted by a free text search, ordered on the chosen property
 * and cut in pages, all of it resolved server side.
 */
public class LogViewControllerIntegrationTest extends AbstractBaseIntegrationTest {

	// Datasource types the seeded jobs belong to, named after this test so that
	// jobs left over by other tests never interfere with the assertions.
	private static final String FIRST_TYPE = "ai.gebo.tests.logview.FirstProjectEndpoint";
	private static final String SECOND_TYPE = "ai.gebo.tests.logview.SecondProjectEndpoint";
	private static final String THIRD_TYPE = "ai.gebo.tests.logview.ThirdProjectEndpoint";

	@Autowired
	private LogViewController logViewController;

	// Codes of the jobs seeded by the test, removed once it is over.
	private final List<String> seededJobCodes = new ArrayList<>();

	@BeforeEach
	public void seedJobEntries() {
		seededJobCodes.clear();
		// Start dates are deliberately unordered so that an ordering assertion
		// cannot pass just because of the insertion order.
		seedJob("LOGVIEW-TEST-001", "Alpha ingestion of the sales archive", FIRST_TYPE, 3_000L);
		seedJob("LOGVIEW-TEST-002", "Charlie ingestion of the sales archive", FIRST_TYPE, 1_000L);
		seedJob("LOGVIEW-TEST-003", "Bravo ingestion of the legal archive", SECOND_TYPE, 2_000L);
		seedJob("LOGVIEW-TEST-004", "Delta ingestion of the legal archive", SECOND_TYPE, 4_000L);
		seedJob("LOGVIEW-TEST-005", "Echo ingestion of the technical archive", THIRD_TYPE, 5_000L);
	}

	@AfterEach
	public void removeSeededJobEntries() {
		statusRepo.deleteAllById(seededJobCodes);
		seededJobCodes.clear();
	}

	@Test
	public void listsTheEntriesOfEverySelectedDatasourceType() {
		Page<GJobStatusItem> page = logViewController.getJobsEntries(filterOn(List.of(FIRST_TYPE, SECOND_TYPE)));

		assertEquals(4, page.getTotalElements(), "The two selected datasource types own four jobs");
		assertEquals(List.of("LOGVIEW-TEST-001", "LOGVIEW-TEST-002", "LOGVIEW-TEST-003", "LOGVIEW-TEST-004"),
				sortedCodesOf(page), "Only the jobs of the selected datasource types are listed");
	}

	@Test
	public void listsTheEntriesOfASingleDatasourceType() {
		Page<GJobStatusItem> page = logViewController.getJobsEntries(filterOn(List.of(THIRD_TYPE)));

		assertEquals(1, page.getTotalElements(), "The third datasource type owns a single job");
		assertEquals(List.of("LOGVIEW-TEST-005"), sortedCodesOf(page), "The job of the selected type is listed");
	}

	@Test
	public void ordersTheEntriesOnTheChosenProperty() {
		JobsEntriesFilter ascending = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		ascending.sortField = "description";
		ascending.sortDescending = false;

		assertEquals(List.of("LOGVIEW-TEST-001", "LOGVIEW-TEST-003", "LOGVIEW-TEST-002", "LOGVIEW-TEST-004",
				"LOGVIEW-TEST-005"), codesOf(logViewController.getJobsEntries(ascending)),
				"The entries are ordered by ascending description");

		JobsEntriesFilter descending = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		descending.sortField = "description";
		descending.sortDescending = true;

		assertEquals(List.of("LOGVIEW-TEST-005", "LOGVIEW-TEST-004", "LOGVIEW-TEST-002", "LOGVIEW-TEST-003",
				"LOGVIEW-TEST-001"), codesOf(logViewController.getJobsEntries(descending)),
				"The entries are ordered by descending description");
	}

	@Test
	public void ordersTheEntriesByMostRecentWhenNothingIsChosen() {
		JobsEntriesFilter filter = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		filter.sortField = null;

		assertEquals(List.of("LOGVIEW-TEST-005", "LOGVIEW-TEST-004", "LOGVIEW-TEST-001", "LOGVIEW-TEST-003",
				"LOGVIEW-TEST-002"), codesOf(logViewController.getJobsEntries(filter)),
				"The most recently started entries come first");
	}

	@Test
	public void searchesTheEntriesOnTheirDescriptionAndCode() {
		JobsEntriesFilter onDescription = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		onDescription.searchText = "legal ARCHIVE";

		assertEquals(List.of("LOGVIEW-TEST-003", "LOGVIEW-TEST-004"),
				sortedCodesOf(logViewController.getJobsEntries(onDescription)),
				"The search is case insensitive and matches a part of the description");

		JobsEntriesFilter onCode = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		onCode.searchText = "TEST-005";

		assertEquals(List.of("LOGVIEW-TEST-005"), sortedCodesOf(logViewController.getJobsEntries(onCode)),
				"The search matches a part of the code too");
	}

	@Test
	public void searchesTheEntriesWithoutInterpretingTheTypedText() {
		JobsEntriesFilter filter = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		filter.searchText = ".*";

		assertTrue(logViewController.getJobsEntries(filter).getTotalElements() == 0,
				"A regular expression typed in the search box is looked for as plain text");
	}

	@Test
	public void cutsTheSearchedEntriesInPages() {
		JobsEntriesFilter firstPage = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		firstPage.sortField = "description";
		firstPage.sortDescending = false;
		firstPage.page.setPage(0);
		firstPage.page.setPageSize(2);

		Page<GJobStatusItem> page = logViewController.getJobsEntries(firstPage);
		assertEquals(5, page.getTotalElements(), "The whole matching set is counted, not just the returned page");
		assertEquals(List.of("LOGVIEW-TEST-001", "LOGVIEW-TEST-003"), codesOf(page), "The first page holds two entries");

		JobsEntriesFilter lastPage = filterOn(List.of(FIRST_TYPE, SECOND_TYPE, THIRD_TYPE));
		lastPage.sortField = "description";
		lastPage.sortDescending = false;
		lastPage.page.setPage(2);
		lastPage.page.setPageSize(2);

		Page<GJobStatusItem> remaining = logViewController.getJobsEntries(lastPage);
		assertEquals(5, remaining.getTotalElements(), "The total does not depend on the shown page");
		assertEquals(List.of("LOGVIEW-TEST-005"), codesOf(remaining), "The last page holds the remaining entry");
	}

	@Test
	public void restrictsTheEntriesToASingleProjectEndpoint() {
		JobsEntriesFilter filter = filterOn(null);
		filter.endpointRef = endpointReferenceOf(SECOND_TYPE, "LOGVIEW-TEST-003");

		assertEquals(List.of("LOGVIEW-TEST-003"), sortedCodesOf(logViewController.getJobsEntries(filter)),
				"Only the jobs of the given project endpoint are listed");
	}

	/**
	 * Builds a filter on the ingestion jobs of the given datasource types.
	 *
	 * @param classNames the datasource types to list, null meaning all of them
	 * @return the filter to hand over to the controller
	 */
	private JobsEntriesFilter filterOn(List<String> classNames) {
		JobsEntriesFilter filter = new JobsEntriesFilter();
		filter.classNames = classNames;
		filter.jobType = JobType.CONTENTS_READING_VECTORIZING;
		filter.page = new DataPage();
		return filter;
	}

	/**
	 * Persists a job entry belonging to the given datasource type.
	 *
	 * @param code         the code identifying the job
	 * @param description  the description the job is searched and ordered by
	 * @param className    the datasource type the job belongs to
	 * @param startedAtEpochMillis the instant the job started at
	 */
	private void seedJob(String code, String description, String className, long startedAtEpochMillis) {
		GJobStatus job = new GJobStatus();
		job.setCode(code);
		job.setDescription(description);
		job.setJobType(JobType.CONTENTS_READING_VECTORIZING);
		job.setWorkflowType("TEST_WORKFLOW");
		job.setWorkflowId(code);
		job.setStartDateTime(new Date(startedAtEpochMillis));
		job.setEndDateTime(new Date(startedAtEpochMillis + 1_000L));
		job.setProcessing(false);
		job.setFinished(true);
		job.setError(false);
		job.setProjectEndpointReference(endpointReferenceOf(className, code));
		job.setKnowledgeBaseCode("LOGVIEW-TEST-KB");
		job.setProjectCode("LOGVIEW-TEST-PROJECT");
		statusRepo.save(job);
		seededJobCodes.add(code);
	}

	private GObjectRef<GProjectEndpoint> endpointReferenceOf(String className, String code) {
		GObjectRef<GProjectEndpoint> reference = new GObjectRef<>();
		reference.setClassName(className);
		reference.setCode(code);
		return reference;
	}

	private List<String> codesOf(Page<GJobStatusItem> page) {
		return page.getContent().stream().map(GJobStatusItem::getCode).toList();
	}

	private List<String> sortedCodesOf(Page<GJobStatusItem> page) {
		return codesOf(page).stream().sorted().toList();
	}
}
