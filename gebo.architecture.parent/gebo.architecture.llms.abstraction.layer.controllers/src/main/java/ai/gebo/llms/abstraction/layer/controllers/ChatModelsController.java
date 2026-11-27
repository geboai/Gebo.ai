/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.llms.abstraction.layer.controllers.model.ChatModelInputs;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupportRequest;
import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import io.swagger.v3.oas.annotations.Operation;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.abstraction.layer.model.GModelMediaGuard;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.controllers.model.ConfigurationEntry;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;

/**
 * AI generated comments
 * Controller class responsible for handling chat model configurations.
 * Only accessible by users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/ChatModelsController")
public class ChatModelsController {

    // Injecting the chat models types repository
    @Autowired
    IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsTypesRepo;

    // Injecting the runtime configuration DAO
    @Autowired
    IGChatModelRuntimeConfigurationDao runtimeDao;
    @Autowired
    IGPersistentObjectManager persistentObjectManager;
    /** What a chosen model accepts: its stored metadata, else the models library. */
    @Autowired
    org.springframework.beans.factory.ObjectProvider<ai.gebo.llms.abstraction.layer.services.GChatModelInputModalitiesResolver> inputModalitiesResolver;

    private List<GModelInputModality> knownInputModalities(GBaseChatModelConfig config) {
        ai.gebo.llms.abstraction.layer.services.GChatModelInputModalitiesResolver resolver = inputModalitiesResolver != null ? inputModalitiesResolver.getIfAvailable()
                : null;
        return resolver != null ? resolver.of(config.getModelTypeCode(), config.getChoosedModel())
                : IGChatModelConfigurationSupportService.inputModalitiesOf(config.getChoosedModel());
    }
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ChatModelsController.class);

    /**
     * Default constructor for ChatModelsController.
     */
    public ChatModelsController() {

    }

    /**
     * Endpoint to get chat model types.
     *
     * @return a list of available chat model types as JSON.
     */
    @GetMapping(value = "getChatModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GChatModelType> getChatModelTypes() {
        // Map each model type to its type and return as a list
        List<GModelType> list = chatModelsTypesRepo.map((x) -> {
            return x.getType();
        });
        return new ArrayList(list);
    }

    /**
     * Endpoint to get runtime configured chat models.
     * 
     * @param modelTypeCode optional code to filter chat models by type.
     * @return a list of runtime configured chat models as JSON.
     */
    @GetMapping(value = "getRuntimeConfiguredChatModels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConfigurationEntry<GBaseChatModelConfig>> getRuntimeConfiguredChatModels(
            @RequestParam(name = "modelTypeCode", required = false) String modelTypeCode) {
        
        // If modelTypeCode is provided, filter configurations by type code
        if (modelTypeCode != null) {
            List<ConfigurationEntry<GBaseChatModelConfig>> configs = new ArrayList<ConfigurationEntry<GBaseChatModelConfig>>();
            List<IGConfigurableChatModel> elements = this.runtimeDao.findListByPredicate(x -> {
                return x.getType().getCode().equals(modelTypeCode);
            });
            if (elements != null) {
                // Map and collect configurations
                configs = new ArrayList(elements.stream().map(x -> {
                    return new ConfigurationEntry(x.getConfig());
                }).toList());
            }
            return configs;
        } else { 
            // Return all configurations if no type code is provided
            return new ArrayList(this.runtimeDao.getConfigurations().stream().map(x -> {
                return new ConfigurationEntry(x.getConfig());
            }).toList());
        }
    }

    /**
     * What every configured chat model takes as input: declared, known by its model's
     * metadata, carried by its provider's integration, and effective.
     */
    @GetMapping(value = "getChatModelsInputs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChatModelInputs> getChatModelsInputs() {
        List<ChatModelInputs> inputs = new ArrayList<ChatModelInputs>();
        for (IGConfigurableChatModel model : this.runtimeDao.getConfigurations()) {
            if (model.getConfig() != null) {
                inputs.add(inputsOf(model));
            }
        }
        return inputs;
    }

    /**
     * What the integration of a chat model type can deliver to one of its models besides
     * text, for the editor to show while a model is being chosen.
     */
    @GetMapping(value = "getCarriedMediaTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public GModelCarriedMediaTypes getCarriedMediaTypes(@RequestParam(name = "modelTypeCode") String modelTypeCode,
            @RequestParam(name = "modelCode", required = false) String modelCode) {
        IGChatModelConfigurationSupportService handler = chatModelsTypesRepo.findByCode(modelTypeCode);
        return handler != null ? handler.carriedMediaTypes(modelCode) : GModelCarriedMediaTypes.none();
    }

    /**
     * What a model of a chat model type takes of the parameters its configuration sets
     * (temperature, top_p, thinking), as the type's integration sends them: the chat
     * model and agent editors enable their controls and offer their values by it. A type
     * that does not tell, or none found, gives an unknown one, and the editors keep every
     * control as it is.
     */
    @PostMapping(value = "getChatModelParametersSupport", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(operationId = "getChatModelParametersSupport",
            summary = "What a chat model takes of temperature, top_p and thinking, as its integration sends them")
    public GChatModelParametersSupport getChatModelParametersSupport(
            @RequestBody GChatModelParametersSupportRequest request) {
        final IGChatModelConfigurationSupportService handler = request != null && request.getModelTypeCode() != null
                ? chatModelsTypesRepo.findByCode(request.getModelTypeCode())
                : null;
        if (handler == null) {
            return GChatModelParametersSupport.unknown(null, request != null ? request.getModelCode() : null);
        }
        return handler.parametersSupport(request.getModelCode(), request.getMetaInfos(), request.getModelDetails());
    }

    /**
     * The kinds of file the installation sends to its chat models as they are (the
     * settings ai.gebo.images.chat-enabled, ai.gebo.recordings.chat-enabled,
     * ai.gebo.videos.chat-enabled, ai.gebo.documents.chat-enabled): a file of another
     * kind reaches no model, whatever the model accepts.
     */
    @GetMapping(value = "getFileKindsSentToModels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GModelInputModality> getFileKindsSentToModels() {
        List<GModelInputModality> kinds = new ArrayList<GModelInputModality>();
        for (GModelInputModality kind : GModelInputModality.values()) {
            if (kind != GModelInputModality.TEXT && ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.of(kind).enabled()
                    && ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.of(kind).maxFiles() > 0) {
                kinds.add(kind);
            }
        }
        return kinds;
    }

    /**
     * Gives the configurations that declare no input modalities what the metadata stored
     * with their model know: a configuration made before the modalities existed is text
     * only until someone acts, and this is that act, once for all of them. A
     * configuration that declares something, and one whose metadata know nothing, are
     * left as they are.
     *
     * @return the configurations changed, as they are now
     */
    @PostMapping(value = "applyModelsMetadataInputModalities", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus<List<ChatModelInputs>> applyModelsMetadataInputModalities() {
        List<ChatModelInputs> changed = new ArrayList<ChatModelInputs>();
        List<GUserMessage> messages = new ArrayList<GUserMessage>();
        for (IGConfigurableChatModel model : this.runtimeDao.getConfigurations()) {
            GBaseChatModelConfig config = (GBaseChatModelConfig) model.getConfig();
            if (config == null || config.getInputModalities() != null) {
                continue;
            }
            // the stored metadata of the model, else the models library: a configuration made
            // before the input modalities were recorded knows them only from the library
            List<GModelInputModality> known = knownInputModalities(config);
            if (known == null) {
                continue;
            }
            try {
                config.setInputModalities(new ArrayList<GModelInputModality>(known));
                persistentObjectManager.update(config);
                changed.add(inputsOf(model));
            } catch (Throwable e) {
                LOGGER.error("Cannot give chat model code=" + config.getCode() + " its model's input modalities", e);
                messages.add(GUserMessage.errorMessage("Chat model " + config.getDescription(), e));
            }
        }
        return OperationStatus.of(changed, messages);
    }

    private ChatModelInputs inputsOf(IGConfigurableChatModel model) {
        GBaseChatModelConfig config = (GBaseChatModelConfig) model.getConfig();
        ChatModelInputs inputs = new ChatModelInputs();
        inputs.setCode(config.getCode());
        inputs.setDescription(config.getDescription());
        inputs.setModelTypeCode(config.getModelTypeCode());
        inputs.setModelCode(config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null);
        inputs.setInputModalities(config.getInputModalities());
        inputs.setMetadataInputModalities(knownInputModalities(config));
        inputs.setCarriedMediaTypes(model.getCarriedMediaTypes());
        // what a request may actually carry: carried, declared, and of a kind the installation sends
        inputs.setEffectiveInputMimeTypes(ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.delivered(model.getEffectiveInputMimeTypes()));
        inputs.setDeclaredNotCarried(
                GModelMediaGuard.declaredNotCarried(model.getInputModalities(), model.getCarriedMediaTypes()));
        return inputs;
    }
}
