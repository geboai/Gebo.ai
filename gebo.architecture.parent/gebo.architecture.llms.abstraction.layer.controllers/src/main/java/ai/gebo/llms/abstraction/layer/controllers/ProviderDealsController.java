package ai.gebo.llms.abstraction.layer.controllers;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.controllers.model.CreateProviderDealRequest;
import ai.gebo.llms.abstraction.layer.controllers.model.ProviderDealSpendingLimitsRequest;
import ai.gebo.llms.abstraction.layer.controllers.model.ProviderDealDescriptionRequest;
import ai.gebo.llms.abstraction.layer.controllers.model.ProviderDealFlatConditionsRequest;
import ai.gebo.llms.abstraction.layer.controllers.model.ProviderDealKeyRequest;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderApiKey;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderModelPriceInfo;
import ai.gebo.llms.abstraction.layer.controllers.model.ProviderDealModelPricingRequest;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.llms.abstraction.layer.services.IGProviderModelPricesService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

/**
 * Admin maintenance of the {@link GProviderDeal}s: the deals made with each real
 * provider and the API keys each covers. The API keys of a provider are the
 * secrets listed under the provider's context code, the same codes the model
 * configuration editors look them up by.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/ProviderDealsController")
@AllArgsConstructor
public class ProviderDealsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderDealsController.class);
	private final IGProviderDealService dealService;
	private final IGProviderModelPricesService modelPricesService;
	private final IGChatModelConfigurationSupportServiceRepositoryPattern chatTypes;
	private final IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embeddingTypes;
	private final IGImageModelConfigurationSupportServiceRepositoryPattern imageTypes;
	private final IGRankerModelConfigurationSupportServiceRepositoryPattern rankerTypes;
	private final IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern textToSpeechTypes;
	private final IGTranscriptModelConfigurationSupportServiceRepositoryPattern transcriptTypes;

	/** The real providers of the model types this installation offers, sorted. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "getDealProviders", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDealProviders() {
		TreeSet<String> providers = new TreeSet<>();
		Stream.of(chatTypes.map(x -> x.getType()), embeddingTypes.map(x -> x.getType()),
				imageTypes.map(x -> x.getType()), rankerTypes.map(x -> x.getType()),
				textToSpeechTypes.map(x -> x.getType()), transcriptTypes.map(x -> x.getType()))
				.flatMap(x -> ((List) x).stream()).map(x -> ((GModelType) x).getProviderId())
				.filter(Objects::nonNull).forEach(x -> providers.add((String) x));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getDealProviders() found " + providers);
		}
		return List.copyOf(providers);
	}

	/** The deals of a provider, or every deal when no provider is given. */
	@GetMapping(value = "getProviderDeals", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GProviderDeal> getProviderDeals(
			@RequestParam(name = "providerId", required = false) String providerId) {
		return dealService.findDeals(providerId);
	}

	@GetMapping(value = "getProviderDeal", produces = MediaType.APPLICATION_JSON_VALUE)
	public GProviderDeal getProviderDeal(@RequestParam(name = "dealId") String dealId) {
		return dealService.findDeal(dealId);
	}

	/**
	 * The API keys of a provider, looked up by the provider's context code, each with
	 * the deal covering it.
	 */
	@GetMapping(value = "getProviderApiKeys", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GProviderApiKey>> getProviderApiKeys(@RequestParam(name = "providerId") String providerId) {
		return run("getProviderApiKeys", () -> dealService.getProviderApiKeys(providerId));
	}

	/** Creates a deal for a provider, moving the given API keys into it. */
	@PostMapping(value = "createProviderDeal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> createProviderDeal(@RequestBody CreateProviderDealRequest request) {
		return run("createProviderDeal", () -> dealService.createDeal(request.getProviderId(), request.getSecretCodes(),
				request.getDescription()));
	}

	/**
	 * Assigns an API key of the deal's provider to the deal, transferring it from the
	 * provider's other deal covering it.
	 */
	@PostMapping(value = "assignApiKey", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> assignApiKey(@RequestBody ProviderDealKeyRequest request) {
		return run("assignApiKey", () -> dealService.assignApiKey(request.getDealId(), request.getSecretCode()));
	}

	/** Removes an API key from a deal. */
	@PostMapping(value = "removeApiKey", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> removeApiKey(@RequestBody ProviderDealKeyRequest request) {
		return run("removeApiKey", () -> dealService.removeApiKey(request.getDealId(), request.getSecretCode()));
	}

	/**
	 * Deletes a deal covering no API key; the last deal of a provider with configured
	 * models is kept.
	 */
	@PostMapping(value = "deleteProviderDeal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteProviderDeal(@RequestBody ProviderDealKeyRequest request) {
		return run("deleteProviderDeal", () -> {
			dealService.deleteDeal(request.getDealId());
			return Boolean.TRUE;
		});
	}

	/** Changes the description of a deal. */
	@PostMapping(value = "updateDescription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> updateDescription(@RequestBody ProviderDealDescriptionRequest request) {
		return run("updateDescription",
				() -> dealService.updateDescription(request.getDealId(), request.getDescription()));
	}

	/**
	 * Sets the flat conditions of a deal (monthly price, monthly and daily traffic
	 * limits), or clears them to make the deal pay per use.
	 */
	@PostMapping(value = "updateFlatConditions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> updateFlatConditions(@RequestBody ProviderDealFlatConditionsRequest request) {
		return run("updateFlatConditions",
				() -> dealService.updateFlatConditions(request.getDealId(), request.getFlatConditions()));
	}

	/**
	 * Imports again the deal's spending limits from its provider's API, when the
	 * provider exposes them and the limits were not set by hand.
	 */
	@PostMapping(value = "refreshImportedLimits", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> refreshImportedLimits(@RequestBody ProviderDealKeyRequest request) {
		return run("refreshImportedLimits", () -> dealService.refreshImportedLimits(request.getDealId()));
	}

	/**
	 * Sets the deal's spending limits by hand, which the imports then leave
	 * untouched, or clears them to import them again from the provider.
	 */
	@PostMapping(value = "updateSpendingLimits", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> updateSpendingLimits(@RequestBody ProviderDealSpendingLimitsRequest request) {
		return run("updateSpendingLimits",
				() -> dealService.updateSpendingLimits(request.getDealId(), request.getSpendingLimits()));
	}

	/**
	 * The models of a provider, per deal and model code, as the runtime models run
	 * them: the price configured with each model and the one its deal gives it.
	 */
	@GetMapping(value = "getProviderModelPrices", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GProviderModelPriceInfo>> getProviderModelPrices(
			@RequestParam(name = "providerId") String providerId) {
		return run("getProviderModelPrices", () -> modelPricesService.getProviderModelPrices(providerId));
	}

	/**
	 * Sets the price a deal gives to a model, overriding or completing the
	 * configured one, or removes it (null pricing) to go back to the configured one.
	 */
	@PostMapping(value = "updateModelPricing", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GProviderDeal> updateModelPricing(@RequestBody ProviderDealModelPricingRequest request) {
		return run("updateModelPricing", () -> dealService.updateModelPricing(request.getDealId(),
				request.getModelCode(), request.getPricingConditions()));
	}

	/**
	 * Runs a maintenance operation, turning a broken rule into an error message for
	 * the admin rather than a server error.
	 */
	private <T> OperationStatus<T> run(String operation, Supplier<T> action) {
		try {
			return OperationStatus.of(action.get());
		} catch (IllegalArgumentException | IllegalStateException e) {
			LOGGER.warn(operation + " refused: " + e.getMessage());
			return OperationStatus.ofError("Provider deal", e.getMessage());
		}
	}
}
