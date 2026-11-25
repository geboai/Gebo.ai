/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.model.SystemMessageAudience;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import ai.gebo.system.messages.services.SystemMessagePublication;
import lombok.RequiredArgsConstructor;

/**
 * Tells, through the system messages, of the files a model provider refused and a
 * request was made again without (see
 * {@link ai.gebo.llms.abstraction.layer.services.RefusedFilesRetryingChatModel}): the
 * person the request was made for is told which files the model did not get, the
 * administrators which model refused which kinds of file and why.
 * <p>
 * One message for each person and model, and one for the administrators for each
 * model: a refusal replaces the one told before, and one that changes nothing - the
 * same files refused again at the next round of the same request - tells nobody twice.
 * Best effort: a deployment without the system messages tells nobody, and a failure is
 * logged and never affects the request.
 */
@Component
@RequiredArgsConstructor
public class RefusedFilesNotifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefusedFilesNotifier.class);
	/** The source of the system messages published by this notifier. */
	public static final String MESSAGES_SOURCE = "llms-refused-files";
	static final String PERSON_KEY_PREFIX = "person:";
	static final String MODEL_KEY_PREFIX = "model:";
	/** The days after the refusal's the person is shown what happened to a request of theirs. */
	static final int PERSON_MESSAGE_DAYS = 1;
	/** The days after the refusal's the administrators are shown what a model refused. */
	static final int ADMINISTRATORS_MESSAGE_DAYS = 7;
	/** The most of the provider's own words the administrators are shown. */
	private static final int REASON_MAX_LENGTH = 600;

	private final ObjectProvider<IGSystemMessagesService> messagesProvider;

	/**
	 * @param modelCode        the code of the model configuration the request was made to
	 * @param modelDescription its description, as the people of the installation know it
	 * @param providerId       the provider of the model, may be null
	 * @param files            the files left out
	 * @param refusal          what the provider answered the request carrying them
	 * @param asking           the username of the person the request was made for, null
	 *                         when it was made for nobody
	 */
	public void filesLeftOut(String modelCode, String modelDescription, String providerId, List<Media> files,
			Throwable refusal, String asking) {
		final IGSystemMessagesService messages = messagesProvider.getIfAvailable();
		if (messages == null || files == null || files.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Files left out of a request to model code=" + modelCode + " told to nobody: system messages="
						+ (messages != null) + " files=" + (files != null ? files.size() : 0));
			}
			return;
		}
		final String model = modelDescription != null && !modelDescription.isBlank() ? modelDescription : modelCode;
		// shown until the end of a day: a message told again the same day changes nothing
		final LocalDate today = LocalDate.now();
		if (asking != null && !asking.isBlank()) {
			try {
				messages.publish(SystemMessagePublication.forPeople(MESSAGES_SOURCE,
						PERSON_KEY_PREFIX + asking + ":" + modelCode, MsgServerity.warn,
						"Files left out of your request",
						"The model \"" + model + "\" could not read these files and answered without them: "
								+ namesOf(files) + ".",
						true, endOf(today.plusDays(PERSON_MESSAGE_DAYS)), List.of(asking)));
			} catch (Throwable e) {
				LOGGER.error("Cannot tell " + asking + " of the files left out of a request to model code=" + modelCode,
						e);
			}
		}
		try {
			messages.publish(new SystemMessagePublication(MESSAGES_SOURCE, MODEL_KEY_PREFIX + modelCode,
					MsgServerity.warn, "A model provider refused the files of a request",
					"The model \"" + model + "\"" + (providerId != null ? " of the provider " + providerId : "")
							+ " refused a request" + (asking != null && !asking.isBlank() ? " of " + asking : "")
							+ " carrying files of type " + typesOf(files)
							+ ", and the request was made again without them. The provider answered: "
							+ reasonOf(refusal),
					SystemMessageAudience.ADMINS, true, endOf(today.plusDays(ADMINISTRATORS_MESSAGE_DAYS))));
		} catch (Throwable e) {
			LOGGER.error("Cannot tell the administrators of the files left out of a request to model code=" + modelCode,
					e);
		}
	}

	private static Date endOf(LocalDate day) {
		return Date.from(day.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private static String namesOf(List<Media> files) {
		final List<String> names = new ArrayList<>();
		for (Media file : files) {
			names.add((file.getName() != null ? file.getName() : "file") + " (" + typeOf(file) + ")");
		}
		return String.join(", ", names);
	}

	private static String typesOf(List<Media> files) {
		final Set<String> types = new LinkedHashSet<>();
		for (Media file : files) {
			types.add(typeOf(file));
		}
		return String.join(", ", types);
	}

	private static String typeOf(Media file) {
		return file.getMimeType() != null ? file.getMimeType().toString() : "unknown type";
	}

	/** The provider's own words, from the error at the root of the refusal. */
	private static String reasonOf(Throwable refusal) {
		if (refusal == null) {
			return "nothing";
		}
		Throwable root = refusal;
		while (root.getCause() != null && root.getCause() != root) {
			root = root.getCause();
		}
		final String text = root.getMessage() != null ? root.getMessage() : root.getClass().getSimpleName();
		return text.length() > REASON_MAX_LENGTH ? text.substring(0, REASON_MAX_LENGTH) + "..." : text;
	}
}
