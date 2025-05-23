/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.heavy_workload_integration_tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTestsWithFakeLLMS;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.monolithic.app.Main;

@SpringBootTest(classes = Main.class)
public class HeavyDutyMonolithicIntegrationTests extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {
	static final Logger LOGGER = LoggerFactory.getLogger(HeavyDutyMonolithicIntegrationTests.class);
	static final String GEBO_VECTORIZABLE_FOLDERS = "GEBO_VECTORIZABLE_FOLDERS";

	public HeavyDutyMonolithicIntegrationTests() {

	}

	class TesterRunnable implements Runnable {
		GFilesystemProjectEndpoint endpoint = null;
		boolean testsOk = false;

		@Override
		public void run() {
			try {
				runAndWaitDoneCheckingResults(endpoint, 100, true);
				testsOk = true;
			} catch (Throwable th) {
				LOGGER.error("TesterRunnable error", th);
				testsOk = false;
			}
		}
	}

	@Test
	public void testHeavyDutyParallelEmbeddingFlow()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException {
		String folders = System.getenv(GEBO_VECTORIZABLE_FOLDERS);
		if (folders == null)
			folders = System.getProperty(GEBO_VECTORIZABLE_FOLDERS);
		if (folders == null) {
			LOGGER.error(GEBO_VECTORIZABLE_FOLDERS + " variable not present for heavy tests");
			return;
		}
		StringTokenizer tokanizer = new StringTokenizer(folders, File.pathSeparator);
		List<String> pathsList = new ArrayList<String>();
		while (tokanizer.hasMoreTokens()) {
			pathsList.add(tokanizer.nextToken());
		}
		if (pathsList.isEmpty()) {
			LOGGER.error(GEBO_VECTORIZABLE_FOLDERS + " is " + folders + " no paths in list");
			return;
		}
		List<GFilesystemProjectEndpoint> endpoints = new ArrayList<GFilesystemProjectEndpoint>();
		for (String path : pathsList) {
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				GFilesystemProjectEndpoint endpoint = createAndPersist("Shared folder: " + path,
						GFilesystemProjectEndpoint.class);
				endpoint.setOpenZips(true);
				endpoint.setPath(List.of(VFilesystemReference.from(Path.of(path))));
				endpoint = persistentObjectManager.update(endpoint);
				endpoints.add(endpoint);
			} else {
				LOGGER.error(GEBO_VECTORIZABLE_FOLDERS + " is " + folders + " but the path " + path
						+ " does not exist or is not a directory");
				return;
			}
		}
		List<TesterRunnable> testRunnables = endpoints.stream().map(x -> {
			TesterRunnable runnable = new TesterRunnable();
			runnable.endpoint = x;
			return runnable;
		}).toList();
		List<Thread> threads = new ArrayList<Thread>();
		for (TesterRunnable testerRunnable : testRunnables) {
			Thread thread = new Thread(testerRunnable);
			thread.start();
			threads.add(thread);
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (TesterRunnable testerRunnable : testRunnables) {
			String msg = "The test on folder:" + testerRunnable.endpoint.getPath() + " was expected to be ok";
			assertTrue(msg, testerRunnable.testsOk);
		}
	}
}
