/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reactor.core.publisher.Flux;

/**
 * The verdict line the deep search consolidation prompt asks to end the consolidated
 * report with, saying whether the report is enough to answer the question:
 * {@code <DEEP-SEARCH-VERDICT complete="true"/>} or
 * {@code <DEEP-SEARCH-VERDICT complete="false" missing="aspect; aspect"/>}.
 * <p>
 * A report without a verdict (a customized prompt without the instruction, a model
 * forgetting it) is not complete: the deep search then goes on as it did before the
 * verdict existed. Every verdict line is removed from the report, which is what the
 * user and the next fold read.
 */
public record DeepSearchVerdict(String report, boolean complete, String missing) {

	private static final Pattern VERDICT = Pattern.compile(
			"<\\s*DEEP-SEARCH-VERDICT\\s+complete\\s*=\\s*\"\\s*(true|false)\\s*\"(?:\\s+missing\\s*=\\s*\"([^\"]*)\")?\\s*/?\\s*>",
			Pattern.CASE_INSENSITIVE);

	/** The report without its verdict lines, and the verdict of its last one. */
	public static DeepSearchVerdict of(String text) {
		if (text == null) {
			return new DeepSearchVerdict(null, false, null);
		}
		final Matcher matcher = VERDICT.matcher(text);
		boolean complete = false;
		String missing = null;
		boolean found = false;
		while (matcher.find()) {
			found = true;
			complete = "true".equalsIgnoreCase(matcher.group(1));
			missing = matcher.group(2) != null && !matcher.group(2).isBlank() ? matcher.group(2).trim() : null;
		}
		final String report = found ? VERDICT.matcher(text).replaceAll("").strip() : text.strip();
		return new DeepSearchVerdict(report, found && complete, found && !complete ? missing : null);
	}

	/**
	 * A streamed consolidation without its verdict lines: the text is passed on as it
	 * comes, except what could be the beginning of a verdict, held back until it is
	 * known (the verdict is dropped, anything else is passed on).
	 */
	public static Flux<String> withoutVerdict(Flux<String> chunks) {
		return Flux.defer(() -> {
			final StringBuilder held = new StringBuilder();
			return chunks.concatMap(chunk -> {
				held.append(chunk);
				final int safe = safeLength(held);
				if (safe <= 0) {
					return Flux.<String>empty();
				}
				final String out = held.substring(0, safe);
				held.delete(0, safe);
				return Flux.just(out);
			}).concatWith(Flux.defer(() -> {
				final String rest = VERDICT.matcher(held).replaceAll("");
				held.setLength(0);
				return rest.isEmpty() ? Flux.<String>empty() : Flux.just(rest);
			}));
		});
	}

	/** How much of the text cannot be part of a verdict: up to where one starts, or may start. */
	static int safeLength(CharSequence text) {
		final Matcher started = VERDICT_START.matcher(text);
		if (started.find()) {
			return started.start();
		}
		final String tail;
		final int lastOpening = text.toString().lastIndexOf('<');
		if (lastOpening < 0) {
			return text.length();
		}
		tail = "<" + text.subSequence(lastOpening + 1, text.length()).toString().stripLeading().toUpperCase();
		return VERDICT_START_TEXT.startsWith(tail) ? lastOpening : text.length();
	}

	private static final String VERDICT_START_TEXT = "<DEEP-SEARCH-VERDICT";
	private static final Pattern VERDICT_START = Pattern.compile("<\\s*DEEP-SEARCH-VERDICT", Pattern.CASE_INSENSITIVE);
}
