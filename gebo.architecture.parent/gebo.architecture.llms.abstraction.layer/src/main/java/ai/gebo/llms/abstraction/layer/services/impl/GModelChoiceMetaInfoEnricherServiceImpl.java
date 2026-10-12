/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * AI generated comments
 * Implementation of the IGModelChoiceMetaInfoEnricherService that enriches model metadata information. 
 */
@Service
public class GModelChoiceMetaInfoEnricherServiceImpl implements IGModelChoiceMetaInfoEnricherService {
    @Autowired
    IGModelsLibraryDao dao;

    /**
     * Enriches metadata information for chat model choices.
     *
     * @param providerId the provider ID
     * @param choice the model choice
     * @param defaultMetainfoFactory the default meta info factory
     */
    @Override
    public <ModelChoiceType extends GBaseChatModelChoice> void enrichChatModelMetaInfos(String providerId,
            ModelChoiceType choice, Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {
        // Invoke general metadata enrichment method
        this.enrichMetaInfos(providerId, choice, defaultMetainfoFactory);

        // Set additional properties based on metadata
        if (choice.getMetaInfos() != null && choice.getSupportsFunctionCalls() == null) {
            choice.setSupportsFunctionCalls(choice.getMetaInfos().getSupportsFunctionCalls());
        }
        if (choice.getMetaInfos() != null && choice.getSupportsStructuredOutput() == null) {
            choice.setSupportsStructuredOutput(choice.getMetaInfos().getSupportsStructuredOutput());
        }
    }

    /**
     * Enriches general metadata information for model choices.
     * <p>
     * A choice that already carries meta infos got them from the provider's models api:
     * those values win, the models library and the default factory only fill what the
     * provider did not tell. A choice without meta infos gets a copy of the library
     * entry, or the default factory's, as before.
     *
     * @param providerId the provider ID
     * @param choice the model choice
     * @param defaultMetainfoFactory the default meta info factory
     */
    private <ModelChoiceType extends GBaseModelChoice> void enrichMetaInfos(String providerId, ModelChoiceType choice,
            Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {
        ModelMetaInfo library = dao.findByProviderIdAndModelId(providerId, choice.getCode());
        if (library == null) {
            library = dao.findByModelId(choice.getCode());
        }
        ModelMetaInfo live = choice.getMetaInfos();
        if (live != null) {
            live.fillMissingFrom(library);
            live.fillMissingFrom(defaultMetainfoFactory.apply(choice));
            if (choice.getDescription() == null || choice.getDescription().isBlank()) {
                choice.setDescription(live.getDescription());
            }
        } else {
            ModelMetaInfo meta = library != null ? library.copy() : defaultMetainfoFactory.apply(choice);
            if (meta == null) {
                return;
            }
            choice.setMetaInfos(meta);
            if (meta.getDescription() != null && meta.getDescription().trim().length() > 0) {
                choice.setDescription(meta.getDescription());
            }
        }
        // Update choice properties based on the resulting metadata
        ModelMetaInfo meta = choice.getMetaInfos();
        if (choice.getInformativeUrl() == null) {
            choice.setInformativeUrl(meta.getInformativeUrl());
        }
        if (choice.getContextLength() == null) {
            choice.setContextLength(meta.getContextLength());
        }
    }

    /**
     * Enriches metadata information for embedding model choices.
     *
     * @param providerId the provider ID
     * @param choice the model choice
     * @param defaultMetainfoFactory the default meta info factory
     */
    @Override
    public <ModelChoiceType extends GBaseEmbeddingModelChoice> void enrichEmbeddingModelMetaInfos(String providerId,
            ModelChoiceType choice, Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {
        // Invoke general metadata enrichment method
        enrichMetaInfos(providerId, choice, defaultMetainfoFactory);

        // Set additional properties based on metadata
        if (choice.getOptimalTokenizationParam() == null && choice.getMetaInfos() != null) {
            choice.setOptimalTokenizationParam(choice.getMetaInfos().getTokenizingThreashold());
        }
    }

}