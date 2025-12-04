package ai.gebo.llms.setup.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.setup.config.LLMSModelsPresets;
import ai.gebo.llms.setup.config.LLMSVendor;
import ai.gebo.llms.setup.config.LLMSVendorsSetupConfig;
import ai.gebo.llms.setup.config.ModelType;
import ai.gebo.llms.setup.config.SystemInitializationLLMConfiguration;
import ai.gebo.llms.setup.config.SystemInitializationLLMConfiguration.ChatModelConfiguration;
import ai.gebo.llms.setup.config.SystemInitializationLLMConfiguration.EmbeddingModelConfiguration;
import ai.gebo.llms.setup.config.SystemInitializationLLMConfiguration.VendorConfiguration;
import ai.gebo.llms.setup.model.LLMCreateModelData;
import ai.gebo.llms.setup.model.LLMCredentialsCreationData;
import ai.gebo.llms.setup.model.LLMModelsLookupParameter;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationData;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.SecretInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Component
@Scope("singleton")
public class SystemInitializationLLMService {
	private final GeboLLMSSetupService llmsSetupService;
	private final SystemInitializationLLMConfiguration systemInitializationLLMConfiguration;
	private final LLMSVendorsSetupConfig vendorsLibraryConfiguration;
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemInitializationLLMService.class);

	public SystemInitializationLLMService(GeboLLMSSetupService llmsSetupService,
			@Autowired(required = false) SystemInitializationLLMConfiguration systemInitializationLLMConfiguration,
			LLMSVendorsSetupConfig vendorsLibraryConfiguration) {
		this.llmsSetupService = llmsSetupService;
		this.systemInitializationLLMConfiguration = systemInitializationLLMConfiguration;
		this.vendorsLibraryConfiguration = vendorsLibraryConfiguration;
	}

	@Scheduled(initialDelay = 20000)
	public void onTick() {
		if (systemInitializationLLMConfiguration != null && systemInitializationLLMConfiguration.getProviders() != null
				&& !systemInitializationLLMConfiguration.getProviders().isEmpty()) {
			try {
				// if there are already setupped llms then quit

				LLMSSetupConfigurationData actualConfiguration = this.llmsSetupService.getActualConfiguration();
				final List actualConfigs = new ArrayList<>();
				if (actualConfiguration.getConfigurations() != null
						&& !actualConfiguration.getConfigurations().isEmpty()) {
					actualConfiguration.getConfigurations().stream().forEach(x -> {
						if (x.getRuntimeConfigs() != null && !x.getRuntimeConfigs().isEmpty()) {
							actualConfigs.addAll(x.getRuntimeConfigs());
						}
					});
					if (actualConfigs.isEmpty()) {
						LOGGER.info("Initializing LLMS");
						for (VendorConfiguration vendor : systemInitializationLLMConfiguration.getProviders()) {
							if (vendor.getProviderId() == null || vendor.getProviderId().trim().length() == 0) {
								LOGGER.error("There is a llms provider with empty providerId in configuration");
							} else {
								LOGGER.info("Setting up provider:" + vendor.getProviderId());
								Optional<LLMSVendor> foundvendor = vendorsLibraryConfiguration.getVendors().stream()
										.filter(x -> x.getVendorInfo() != null
												&& x.getVendorInfo().getVendorId() != null && x.getVendorInfo()
														.getVendorId().equalsIgnoreCase(vendor.getProviderId()))
										.findFirst();
								if (foundvendor.isEmpty()) {
									LOGGER.error("The providerId:" + vendor.getProviderId()
											+ " is not found in the actual llms provider library");
								} else {
									LLMSVendor vendorConfigurationData = foundvendor.get();
									boolean requiresApiKey = vendorConfigurationData.getVendorInfo().isRequiresApiKey();
									boolean requiresCustomUrl = vendorConfigurationData.getVendorInfo()
											.isRequiresCustomUrl();
									SecretInfo createdSecret = null;
									ChatModelConfiguration chatModelConfig = vendor.getChatModel();
									EmbeddingModelConfiguration embeddingModelConfig = vendor.getEmbeddingModel();
									String chatModelCode = chatModelConfig != null ? chatModelConfig.getModelCode()
											: null;
									String embeddingModelCode = embeddingModelConfig != null
											? embeddingModelConfig.getModelCode()
											: null;
									if ((chatModelConfig == null || chatModelConfig.getModelCode() == null)
											&& (embeddingModelConfig == null
													|| embeddingModelConfig.getModelCode() == null)) {
										LOGGER.error("The provider configuration:" + vendor.getProviderId()
												+ " does not include chatModel or embeddingModel configurations");
										continue;
									}
									String baseUrl = vendor.getUrl();
									if (requiresCustomUrl && (baseUrl == null || baseUrl.trim().length() == 0)) {
										LOGGER.error("The provider: " + vendor.getProviderId()
												+ " requires you to configure an url for the server api(s) to be reached");
										continue;
									}
									String apiKey = vendor.getApiKey();
									String username = vendor.getUsername();
									Optional<LLMSModelsPresets> chatPreset = vendorConfigurationData.getPresets()
											.stream().filter(x -> x.getType() == ModelType.CHAT).findFirst();
									Optional<LLMSModelsPresets> embeddingPreset = vendorConfigurationData.getPresets()
											.stream().filter(x -> x.getType() == ModelType.EMBEDDING).findFirst();
									if (apiKey != null && username != null && apiKey.trim().length() > 0
											&& username.trim().length() > 0) {

										LLMCredentialsCreationData credentials = new LLMCredentialsCreationData();
										credentials.setApiKeySecretContext(
												vendorConfigurationData.getVendorInfo().getApiKeySecretContext());
										credentials.setBaseUrl(baseUrl);
										credentials.setNewUserName(username);
										credentials.setNewApiSecret(apiKey);
										String serviceHandler = null;
										boolean doModelsLookup = false;
										if (chatModelConfig != null && chatModelConfig.getModelCode() != null) {

											if (chatPreset.isPresent()) {
												serviceHandler = chatPreset.get().getServiceHandler();
												doModelsLookup = chatPreset.get().isDoModelsLookup();
											}
										} else if (embeddingModelConfig != null
												&& embeddingModelConfig.getModelCode() != null) {

											if (embeddingPreset.isPresent()) {
												serviceHandler = embeddingPreset.get().getServiceHandler();
												doModelsLookup = embeddingPreset.get().isDoModelsLookup();
											}
										}
										credentials.setServiceHandler(serviceHandler);
										credentials.setDoModelsLookup(doModelsLookup);
										OperationStatus<SecretInfo> status = this.llmsSetupService
												.createLLMCredentials(credentials);
										if (status.isHasErrorMessages()) {
											status.getMessages().stream().forEach(x -> {
												LOGGER.error(x.getSummary() + " - " + x.getDetail());
											});
											continue;
										} else {
											createdSecret = status.getResult();
										}
									}
									if (requiresApiKey && createdSecret == null) {
										LOGGER.error("The provider: " + vendor.getProviderId()
												+ " needs an apiKey and username to be accessed");
										continue;
									}
									// doing a lookup on models if lookup is enabled
									if (chatPreset.isPresent() && chatModelCode != null) {
										if (chatPreset.get().isDoModelsLookup()) {
											LLMModelsLookupParameter credentials = new LLMModelsLookupParameter();
											credentials.setBaseUrl(baseUrl);
											credentials.setServiceHandler(chatPreset.get().getServiceHandler());
											credentials.setType(ModelType.CHAT);
											credentials.setSecretId(
													createdSecret != null ? createdSecret.getCode() : null);
											OperationStatus<List<GBaseModelChoice>> result = this.llmsSetupService
													.verifyCredentialsAndDownloadModels(credentials);
											if (result.isHasErrorMessages()) {
												result.getMessages().stream().forEach(x -> {
													LOGGER.error(x.getSummary() + " - " + x.getDetail());
												});
												continue;
											}
											result.getResult().forEach(x -> {
												LOGGER.info("Found chat model:" + x.getCode());
											});

										}
									}
									if (embeddingPreset.isPresent() && embeddingModelCode != null) {
										if (embeddingPreset.get().isDoModelsLookup()) {
											LLMModelsLookupParameter credentials = new LLMModelsLookupParameter();
											credentials.setBaseUrl(baseUrl);
											credentials.setServiceHandler(embeddingPreset.get().getServiceHandler());
											credentials.setType(ModelType.EMBEDDING);
											credentials.setSecretId(
													createdSecret != null ? createdSecret.getCode() : null);
											OperationStatus<List<GBaseModelChoice>> result = this.llmsSetupService
													.verifyCredentialsAndDownloadModels(credentials);
											if (result.isHasErrorMessages()) {
												result.getMessages().stream().forEach(x -> {
													LOGGER.error(x.getSummary() + " - " + x.getDetail());
												});
												continue;
											}
											result.getResult().forEach(x -> {
												LOGGER.info("Found embedding model:" + x.getCode());
											});

										}
									}
									List<LLMCreateModelData> newLLmsConfigs = new ArrayList<>();
									if (chatModelCode != null) {
										if (chatPreset.isEmpty()) {
											LOGGER.error("No chat models library informations for provider:"
													+ vendor.getProviderId());
											continue;
										}
									}
									if (embeddingModelCode != null) {
										if (embeddingPreset.isEmpty()) {
											LOGGER.error("No embedding models library informations for provider:"
													+ vendor.getProviderId());
											continue;
										}
									}
									if (chatModelCode != null) {
										String serviceHandler = chatPreset.get().getServiceHandler();
										boolean doModelsLookup = chatPreset.get().isDoModelsLookup();
										LLMCreateModelData chatData = new LLMCreateModelData();
										chatData.setBaseUrl(baseUrl);
										chatData.setDoModelsLookup(doModelsLookup);
										chatData.setEnableAllFunctions(chatModelConfig.isToolsEnabled());
										chatData.setSecretId(createdSecret != null ? createdSecret.getCode() : null);
										chatData.setModelCode(chatModelCode);
										chatData.setServiceHandler(serviceHandler);
										chatData.setSetAsDefaultModel(chatModelConfig.isDefaultModel());
										chatData.setType(ModelType.CHAT);
										newLLmsConfigs.add(chatData);

									}
									if (embeddingModelCode != null) {
										String serviceHandler = embeddingPreset.get().getServiceHandler();
										boolean doModelsLookup = embeddingPreset.get().isDoModelsLookup();
										LLMCreateModelData embeddingData = new LLMCreateModelData();
										embeddingData.setBaseUrl(baseUrl);
										embeddingData.setDoModelsLookup(doModelsLookup);
										embeddingData
												.setSecretId(createdSecret != null ? createdSecret.getCode() : null);
										embeddingData.setModelCode(embeddingModelCode);
										embeddingData.setServiceHandler(serviceHandler);
										embeddingData.setSetAsDefaultModel(embeddingModelConfig.isDefaultModel());
										embeddingData.setType(ModelType.EMBEDDING);
										newLLmsConfigs.add(embeddingData);
									}
									if (!newLLmsConfigs.isEmpty()) {
										OperationStatus<List<GBaseModelConfig>> result = this.llmsSetupService
												.createLLMS(newLLmsConfigs);
										if (result.isHasErrorMessages()) {
											result.getMessages().stream().forEach(x -> {
												LOGGER.error(x.getSummary() + " - " + x.getDetail());
											});
										}
										if (result.getResult() != null) {
											result.getResult().forEach(model -> {
												LOGGER.info("Successfully created model:" + model.getCode());
											});
										}
									}

								}
							}
						}
					}
				}
			} catch (Throwable th) {
				LOGGER.error("Error in llms system initialization by config", th);
			}
		}
	}

}
