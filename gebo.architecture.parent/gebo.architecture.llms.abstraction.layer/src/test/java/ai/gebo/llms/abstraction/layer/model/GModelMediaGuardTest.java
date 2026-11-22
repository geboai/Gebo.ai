/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.AUDIO;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.DOCUMENT;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.VIDEO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GModelMediaGuard.Outcome;

/**
 * A file goes to a model only when its configuration declares the file's modality and
 * the provider's integration carries its type.
 */
class GModelMediaGuardTest {
	static final GModelCarriedMediaTypes ANTHROPIC_LIKE = GModelCarriedMediaTypes.none()
			.images("image/png", "image/jpeg", "image/gif", "image/webp").documents("application/pdf");
	static final GModelCarriedMediaTypes IMAGES_ONLY = GModelCarriedMediaTypes.none().images("image/*");

	@Test
	void aFileIsSentWhenDeclaredAndCarried() {
		assertEquals(Outcome.SENT, GModelMediaGuard.decide(List.of(TEXT, IMAGE), ANTHROPIC_LIKE, "image/png"));
		assertEquals(Outcome.SENT,
				GModelMediaGuard.decide(List.of(TEXT, IMAGE, DOCUMENT), ANTHROPIC_LIKE, "Application/PDF; x=1"));
		assertTrue(GModelMediaGuard.sends(List.of(IMAGE), IMAGES_ONLY, "image/tiff"));
	}

	@Test
	void aModelThatDoesNotDeclareTheModalityNeverGetsTheFile() {
		assertEquals(Outcome.MODEL_DOES_NOT_READ_IT, GModelMediaGuard.decide(null, ANTHROPIC_LIKE, "image/png"));
		assertEquals(Outcome.MODEL_DOES_NOT_READ_IT,
				GModelMediaGuard.decide(List.of(TEXT), ANTHROPIC_LIKE, "image/png"));
		assertEquals(Outcome.MODEL_DOES_NOT_READ_IT,
				GModelMediaGuard.decide(List.of(TEXT, IMAGE), ANTHROPIC_LIKE, "application/pdf"));
	}

	@Test
	void aTypeTheIntegrationDoesNotCarryStaysOut() {
		// a picture type Anthropic's integration raises an error on
		assertEquals(Outcome.INTEGRATION_DOES_NOT_CARRY_IT,
				GModelMediaGuard.decide(List.of(TEXT, IMAGE), ANTHROPIC_LIKE, "image/tiff"));
		// audio declared, on an integration that would send it as a picture
		assertEquals(Outcome.INTEGRATION_DOES_NOT_CARRY_IT,
				GModelMediaGuard.decide(List.of(TEXT, IMAGE, AUDIO), IMAGES_ONLY, "audio/wav"));
		// an integration that states nothing carries nothing
		assertEquals(Outcome.INTEGRATION_DOES_NOT_CARRY_IT,
				GModelMediaGuard.decide(List.of(TEXT, IMAGE), GModelCarriedMediaTypes.none(), "image/png"));
		assertEquals(Outcome.INTEGRATION_DOES_NOT_CARRY_IT,
				GModelMediaGuard.decide(List.of(TEXT, IMAGE), null, "image/png"));
		// an office file is a document, sent only where its exact type is carried
		assertEquals(Outcome.INTEGRATION_DOES_NOT_CARRY_IT, GModelMediaGuard.decide(List.of(TEXT, DOCUMENT),
				ANTHROPIC_LIKE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
	}

	@Test
	void aFileWithoutATypeStaysOut() {
		assertEquals(Outcome.UNKNOWN_TYPE, GModelMediaGuard.decide(List.of(TEXT, IMAGE), IMAGES_ONLY, null));
		assertEquals(Outcome.UNKNOWN_TYPE, GModelMediaGuard.decide(List.of(TEXT, IMAGE), IMAGES_ONLY, "image"));
	}

	@Test
	void theEffectiveTypesAreTheCarriedOnesOfTheDeclaredModalities() {
		assertEquals(List.of("image/png", "image/jpeg", "image/gif", "image/webp"),
				GModelMediaGuard.effectiveMimeTypes(List.of(TEXT, IMAGE), ANTHROPIC_LIKE));
		assertEquals(List.of("application/pdf"),
				GModelMediaGuard.effectiveMimeTypes(List.of(TEXT, DOCUMENT), ANTHROPIC_LIKE));
		assertTrue(GModelMediaGuard.effectiveMimeTypes(List.of(TEXT), ANTHROPIC_LIKE).isEmpty());
		assertTrue(GModelMediaGuard.effectiveMimeTypes(null, ANTHROPIC_LIKE).isEmpty());
		assertTrue(GModelMediaGuard.effectiveMimeTypes(List.of(TEXT, IMAGE), GModelCarriedMediaTypes.none()).isEmpty());
	}

	@Test
	void theDeclaredModalitiesNothingIsCarriedForAreTold() {
		assertEquals(List.of(AUDIO, VIDEO),
				GModelMediaGuard.declaredNotCarried(List.of(TEXT, IMAGE, AUDIO, VIDEO), IMAGES_ONLY));
		assertEquals(List.of(IMAGE),
				GModelMediaGuard.declaredNotCarried(List.of(TEXT, IMAGE), GModelCarriedMediaTypes.none()));
		assertTrue(GModelMediaGuard.declaredNotCarried(List.of(TEXT, IMAGE), IMAGES_ONLY).isEmpty());
	}

	@Test
	void theModalityOfAFileComesFromItsType() {
		assertEquals(IMAGE, GModelInputModality.ofMimeType("image/png"));
		assertEquals(AUDIO, GModelInputModality.ofMimeType("audio/mpeg"));
		assertEquals(VIDEO, GModelInputModality.ofMimeType("video/mp4"));
		assertEquals(DOCUMENT, GModelInputModality.ofMimeType("application/pdf"));
		assertEquals(DOCUMENT, GModelInputModality.ofMimeType("text/csv"));
		assertNull(GModelInputModality.ofMimeType(null));
		assertFalse(IMAGES_ONLY.carries("application/pdf"));
		assertTrue(GModelCarriedMediaTypes.none().carriesNothing());
	}
}
