/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { ComponentLLMSStatus, LLMCreateModelData } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * The seven model "classes" the easy wizard drives, in the order they are shown. A class is a
 * single configurable model slot: two of them (CHAT and SERVICE) share the backend ModelType
 * CHAT but differ by their {@link ModelClassDescriptor.uses}, the others map one-to-one to a
 * backend ModelType. This one descriptor table replaces the seven times repeated per-kind code
 * of the original easy/expert components: every step in the wizard iterates over
 * {@link MODEL_CLASSES} instead of hand-listing each kind.
 */
export type ModelClassId = "CHAT" | "SERVICE" | "EMBEDDING" | "RANKING" | "TTS" | "TRANSCRIPT" | "IMAGESGEN";

export interface ModelClassDescriptor {
    /** Internal per-slot id, also the DOM id / i18n component id prefix of its controls. */
    id: ModelClassId;
    /** The backend model type this slot creates (CHAT for both the CHAT and the SERVICE slot). */
    type: LLMCreateModelData.TypeEnum;
    /** The uses tag sent on creation; distinguishes the two chat slots. Undefined for non-chat kinds. */
    uses?: Array<LLMCreateModelData.UsesEnum>;
    /** Required slots (default chat, embedding) gate the whole setup; the rest are suggested. */
    required: boolean;
    /** i18n field id used both as the DOM id and the resource componentId across the step templates. */
    fieldId: string;
    /** Reads whether a default of this class already exists, from the setup status. */
    statusSetup: (s?: ComponentLLMSStatus) => boolean | undefined;
    /** Reads the code of the current default of this class, from the setup status. */
    statusCode: (s?: ComponentLLMSStatus) => string | undefined;
    /** Reads the provider (model type handler code) that owns the current default of this class. */
    statusProvider: (s?: ComponentLLMSStatus) => string | undefined;
}

export const MODEL_CLASSES: ModelClassDescriptor[] = [
    {
        id: "CHAT", type: "CHAT", uses: ["CHAT"], required: true, fieldId: "ClassChat",
        statusSetup: s => s?.chatModelSetup,
        statusCode: s => s?.chatModelCode,
        statusProvider: s => s?.chatModelProviderId
    },
    {
        id: "EMBEDDING", type: "EMBEDDING", required: true, fieldId: "ClassEmbedding",
        statusSetup: s => s?.embeddedModelSetup,
        statusCode: s => s?.embeddedModelCode,
        statusProvider: s => s?.embeddedModelProviderId
    },
    {
        id: "SERVICE", type: "CHAT", uses: ["INTERNAL_SERVICES"], required: false, fieldId: "ClassService",
        statusSetup: s => s?.internalServicesChatModelSetup,
        statusCode: s => s?.internalServicesChatModelCode,
        statusProvider: s => s?.internalServicesChatModelProviderId
    },
    {
        id: "RANKING", type: "RANKING", required: false, fieldId: "ClassRanking",
        statusSetup: s => s?.rankingModelSetup,
        statusCode: s => s?.rankingModelCode,
        statusProvider: s => s?.rankingModelProviderId
    },
    {
        id: "TRANSCRIPT", type: "TRANSCRIPT", required: false, fieldId: "ClassTranscript",
        statusSetup: s => s?.transcriptModelSetup,
        statusCode: s => s?.transcriptModelCode,
        statusProvider: s => s?.transcriptModelProviderId
    },
    {
        id: "TTS", type: "TTS", required: false, fieldId: "ClassTts",
        statusSetup: s => s?.ttsModelSetup,
        statusCode: s => s?.ttsModelCode,
        statusProvider: s => s?.ttsModelProviderId
    },
    {
        id: "IMAGESGEN", type: "IMAGESGEN", required: false, fieldId: "ClassImages",
        statusSetup: s => s?.imagesModelSetup,
        statusCode: s => s?.imagesModelCode,
        statusProvider: s => s?.imagesModelProviderId
    }
];

/**
 * Human readable name and purpose of each model class, shown in the intro, choice and summary
 * steps. Kept here so the three steps describe a class identically; each step still registers its
 * own i18n entry (componentId = descriptor.fieldId) under its own component entity.
 */
export const CLASS_TEXT: Record<ModelClassId, { label: string; description: string }> = {
    CHAT: {
        label: "Default chat model",
        description: "Answers users and runs the workflows that have no model of their own. It should be capable and reliable, with a good balance of cost, speed and quality."
    },
    EMBEDDING: {
        label: "Embedding model",
        description: "Turns your documents and questions into vectors for semantic indexing and retrieval. Required for the software to work."
    },
    SERVICE: {
        label: "Internal services chat model",
        description: "A fast and cheap chat model used for internal work: extracting, summarizing and running internal steps. Suggested to keep the main chat model free for users."
    },
    RANKING: {
        label: "Ranker model",
        description: "Re-ranks retrieved fragments and keeps the ones most relevant to the question. Improves answer quality."
    },
    TRANSCRIPT: {
        label: "Transcription model",
        description: "Converts speech to text for the transcription service."
    },
    TTS: {
        label: "Text to speech model",
        description: "Converts text into spoken audio."
    },
    IMAGESGEN: {
        label: "Image generation model",
        description: "Generates images from text prompts."
    }
};

/** A model offered to a slot: code is submitted, description is read, fromPreset leads the list. */
export interface ModelChoiceOption {
    code?: string;
    description?: string;
    fromPreset?: boolean;
}

/**
 * The user's decision for one model class in step 3. A class is either kept off ("don't set it
 * now"), or on with a chosen model code. `alreadyConfigured` records whether a default of this
 * class existed when the step opened, so the UI can show it as a modifiable existing setting
 * rather than a fresh choice, and so an untouched existing class is not needlessly recreated.
 */
export interface ModelClassDecision {
    descriptor: ModelClassDescriptor;
    /** True when the provider's library declares no preset of this class: nothing can be set. */
    offered: boolean;
    /** True when a default of this class already exists (shown as a label + "Modify"). */
    alreadyConfigured: boolean;
    /** Code + description of the current default, when alreadyConfigured. */
    currentCode?: string;
    currentProvider?: string;
    /** Whether the user wants this class applied on "next". */
    enabled: boolean;
    /** For an already configured class: the user opened it to change the model. */
    modifying: boolean;
    /** The model the user picked (or the preselected preset / current code). */
    chosenCode?: string;
    /** What the combo shows: provider catalogue once looked up, library presets until then. */
    choices: ModelChoiceOption[];
    /** The chat "enable all function calls" toggle only applies to the default chat slot. */
    enableAllFunctions?: boolean;
}
