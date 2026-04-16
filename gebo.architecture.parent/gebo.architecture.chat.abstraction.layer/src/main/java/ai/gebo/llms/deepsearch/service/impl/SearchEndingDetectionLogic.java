package ai.gebo.llms.deepsearch.service.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.llms.deepsearch.model.DeepSearchState;

public class SearchEndingDetectionLogic {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchEndingDetectionLogic.class);
	public static final String SATISFACTORY_DOCUMENT_TAG = "<IS-COMPLETELY-SATISFACTORY/>";

	public static String cleanFromTag(String text) {
		if (text == null)
			return "";
		String upper = text.toUpperCase();
		int index = upper.indexOf(SATISFACTORY_DOCUMENT_TAG);
		if (index >= 0) {
			text = text.substring(0, index) + text.substring(index + SATISFACTORY_DOCUMENT_TAG.length());
		}
		return text;
	}

	public static <CustomContentExtractionType extends BaseSearchResultsExtractionDataType> boolean manageTrigger(
			AtomicInteger totalSteps, AtomicInteger doneSteps, AtomicInteger satisfactoryDocuments,
			AtomicBoolean completed, final int satisfactoryDocumentsThreashold,
			CustomContentExtractionType actualDocumentAnalisysOutput) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageTrigger(...)");
		}
		boolean trigger = false;
		if (!completed.get()) {
			boolean actualIsSatisfactory = actualDocumentAnalisysOutput.getSatisfactoryAnswer() != null
					&& actualDocumentAnalisysOutput.getSatisfactoryAnswer();

			if (actualIsSatisfactory) {
				int nSatisfactories = satisfactoryDocuments.incrementAndGet();
				if (nSatisfactories >= satisfactoryDocumentsThreashold) {
					completed.set(true);
					trigger = true;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Search ending threashold reached!!! " + nSatisfactories);
					}
				}
			}

		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageTrigger(...) trigger fired=>" + trigger);
		}
		return trigger;
	}

	public static boolean manageTrigger(AtomicInteger totalSteps, AtomicInteger doneSteps,
			AtomicInteger satisfactoryDocuments, AtomicBoolean completed, final int satisfactoryDocumentsThreashold,
			String actualDocumentAnalisysOutput) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageTrigger(...)");
		}
		boolean trigger = false;
		if (!completed.get()) {
			boolean actualIsSatisfactory = actualDocumentAnalisysOutput.toUpperCase()
					.indexOf(SATISFACTORY_DOCUMENT_TAG) >= 0;

			if (actualIsSatisfactory) {
				int nSatisfactories = satisfactoryDocuments.incrementAndGet();
				if (nSatisfactories >= satisfactoryDocumentsThreashold) {
					completed.set(true);
					trigger = true;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Search ending threashold reached!!! " + nSatisfactories);
					}
				}
			}

		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageTrigger(...) trigger fired=>" + trigger);
		}
		return trigger;
	}
	public static boolean manageTrigger(DeepSearchState deepSearchState,
			String actualDocumentAnalisysOutput) {
		AtomicInteger totalSteps = deepSearchState.getTotalSteps();
		AtomicInteger doneSteps = deepSearchState.getDoneSteps();
		AtomicInteger satisfactoryDocuments = deepSearchState.getSatisfactoryDocuments();
		AtomicBoolean completed = deepSearchState.getCompleted();
		final int satisfactoryDocumentsThreashold = deepSearchState.getSatisfactoryDocumentsThreashold();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageTrigger(...)");
		}
		boolean trigger = false;
		if (!completed.get()) {
			boolean actualIsSatisfactory = actualDocumentAnalisysOutput.toUpperCase()
					.indexOf(SATISFACTORY_DOCUMENT_TAG) >= 0;

			if (actualIsSatisfactory) {
				int nSatisfactories = satisfactoryDocuments.incrementAndGet();
				if (nSatisfactories >= satisfactoryDocumentsThreashold) {
					completed.set(true);
					trigger = true;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Search ending threashold reached!!! " + nSatisfactories);
					}
				}
			}

		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageTrigger(...) trigger fired=>" + trigger);
		}
		return trigger;
	}
	public static <CustomContentExtractionType extends BaseSearchResultsExtractionDataType> boolean manageTrigger(
			DeepSearchState deepSearchState, CustomContentExtractionType currentConsolidation) {
		AtomicInteger totalSteps = deepSearchState.getTotalSteps();
		AtomicInteger doneSteps = deepSearchState.getDoneSteps();
		AtomicInteger satisfactoryDocuments = deepSearchState.getSatisfactoryDocuments();
		AtomicBoolean completed = deepSearchState.getCompleted();
		final int satisfactoryDocumentsThreashold = deepSearchState.getSatisfactoryDocumentsThreashold();
		boolean trigger = false;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageTrigger(...)");
		}

		if (!completed.get()) {
			boolean actualIsSatisfactory = currentConsolidation.getSatisfactoryAnswer() != null
					&& currentConsolidation.getSatisfactoryAnswer();

			if (actualIsSatisfactory) {
				int nSatisfactories = satisfactoryDocuments.incrementAndGet();
				if (nSatisfactories >= satisfactoryDocumentsThreashold) {
					completed.set(true);
					trigger = true;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Search ending threashold reached!!! " + nSatisfactories);
					}
				}
			}

		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageTrigger(...) trigger fired=>" + trigger);
		}
		return trigger;
	}
}
