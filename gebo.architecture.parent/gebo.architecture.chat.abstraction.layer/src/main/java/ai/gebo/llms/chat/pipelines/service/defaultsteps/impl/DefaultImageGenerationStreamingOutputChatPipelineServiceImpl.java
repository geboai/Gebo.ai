package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.content.Media;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Streaming output pipeline step that fulfils an IMAGE_GENERATION deliverable.
 *
 * <p>
 * It refines the (already rewritten) user query into a clean text-to-image
 * prompt through the service LLM, invokes the default configured image model and
 * persists the produced picture through the same "LLM generated artifacts"
 * mechanism used by image-capable chat models (see
 * {@code AbstractChatService}): the generated {@link Media} is stored via
 * {@link IGChatStorageAreaService#addMedia(Media, String)} and attached to
 * {@link GeboChatResponse#getGeneratedResources()} so the UI can download/render
 * it exactly like any other generated resource.
 */
@Service
@AllArgsConstructor
public class DefaultImageGenerationStreamingOutputChatPipelineServiceImpl extends BaseLLMSInvokingService
		implements IStreamingOutputChatPipelineService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultImageGenerationStreamingOutputChatPipelineServiceImpl.class);

	public static final String IMAGE_GENERATION_STREAMING_SERVICE = "IMAGE_GENERATION_STREAMING_SERVICE";
	private static final String IMAGE_PROMPT_FIELD = "imagePrompt";
	private static final String GENERATED_IMAGE_BASENAME = "generated-image-";
	private static final String PNG_EXTENSION = ".png";

	private final IGPromptConfigDao promptsDao;
	private final IGImageModelRuntimeConfigurationDao imageModelsDao;
	private final IGChatStorageAreaService chatStorageAreaService;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;

	@Override
	public StepExecutorType getExecutorType() {
		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {
		return IMAGE_GENERATION_STREAMING_SERVICE;
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {
		return List.of();
	}

	/**
	 * Resolves the image model to use for text-to-image generation: the default
	 * configured one when present, otherwise the first configured model. Returns
	 * {@code null} when no image model is configured at all, in which case the image
	 * generation route is not available/routable.
	 */
	public static IGConfigurableImageModel resolveImageModel(IGImageModelRuntimeConfigurationDao imageModelsDao) {
		if (imageModelsDao == null) {
			return null;
		}
		IGConfigurableImageModel model = imageModelsDao.defaultHandler();
		if (model != null) {
			return model;
		}
		List<IGConfigurableImageModel> configured = imageModelsDao.getConfigurations();
		return configured != null && !configured.isEmpty() ? configured.get(0) : null;
	}

	/**
	 * @return true when at least one image model is configured, so an image
	 *         generation request can be fulfilled.
	 */
	public static boolean isImageGenerationAvailable(IGImageModelRuntimeConfigurationDao imageModelsDao) {
		return resolveImageModel(imageModelsDao) != null;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final GeboChatResponse response = runtimeData.getChatResponse();
		sinkUIEmitter.notifyUser("generatingImage", "Generating the requested image", "pi pi-image", 3000l,
				NotificationType.INFO);

		Flux<GeboChatMessageEnvelope> body = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				List<GeboChatMessageEnvelope> envelopes = new ArrayList<>();
				try {
					generateAndAttachImage(runtimeData, sinkUIEmitter, chatModel, serviceModel, response);
				} catch (Throwable th) {
					LOGGER.error("Error while generating image", th);
					envelopes.add(new GeboChatMessageEnvelope(
							GUserMessage.errorMessage("Error while generating the image", th)));
					if (response.getQueryResponse() == null || response.getQueryResponse().isBlank()) {
						response.setQueryResponse("I was not able to generate the requested image.");
					}
				}
				// resources first so the client receives the artifact reference
				GeboChatMessageEnvelope resourcesEnvelope = new GeboChatMessageEnvelope(response);
				resourcesEnvelope.setLastMessage(false);
				envelopes.add(resourcesEnvelope);
				envelopes.add(new GeboChatMessageEnvelope(response.getQueryResponse()));
				GeboChatMessageEnvelope lastEnvelope = new GeboChatMessageEnvelope(response);
				lastEnvelope.setLastMessage(true);
				envelopes.add(lastEnvelope);
				envelopes.add(GeboChatMessageEnvelope.FINAL_MESSAGE);
				return Flux.fromIterable(envelopes);
			});
		});

		return body.onErrorResume(exc -> {
			LOGGER.error("Error while streaming image generation response", exc);
			return Flux.just(new GeboChatMessageEnvelope(
					GUserMessage.errorMessage("Error while streaming image generation response", exc)));
		}).doOnComplete(() -> {
			runAs.doAs(() -> {
				try {
					this.chatSessionLifecycleService.endRequest(runtimeData.getRequestResources().getCurrentRequest(),
							response);
				} catch (Throwable th) {
					LOGGER.error("Error saving user context", th);
				}
				try {
					this.chatSessionLifecycleService
							.chatRequestCompleted(runtimeData.getRequestResources().getCurrentRequest(), chatModel);
				} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
					LOGGER.error("Error closing image generation response flux with chatSessionLifecycle code", e);
				}
			});
		});
	}

	private void generateAndAttachImage(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter sinkUIEmitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, GeboChatResponse response)
			throws IOException {
		IGConfigurableImageModel imageModel = resolveImageModel(imageModelsDao);
		if (imageModel == null) {
			LOGGER.warn("No default image model configured, cannot fulfil an image generation request");
			sinkUIEmitter.notifyUser("noImageModel", "No image generation model is configured", "pi pi-exclamation-triangle",
					4000l, NotificationType.ERROR);
			response.setQueryResponse(
					"No image generation model is currently configured, so I cannot generate the requested image. "
							+ "Please ask an administrator to configure an image model.");
			return;
		}

		String imagePrompt = craftImagePrompt(runtimeData, serviceModel);
		sinkUIEmitter.notifyUser("callingImageModel", "Calling the image model", "pi pi-image", 3000l,
				NotificationType.INFO);

		ImageResponse imageResponse = imageModel.call(new ImagePrompt(imagePrompt));
		Image image = imageResponse != null && imageResponse.getResult() != null
				? imageResponse.getResult().getOutput()
				: null;
		byte[] bytes = image != null ? extractImageBytes(image) : null;
		if (bytes == null || bytes.length == 0) {
			throw new IOException("The image model returned no image data");
		}

		String sessionCode = runtimeData.getRequestResources().getCurrentRequest().getUserChatContextCode();
		MimeType mimeType = MimeTypeUtils.IMAGE_PNG;
		Media media = Media.builder().mimeType(mimeType).data(new ByteArrayResource(bytes))
				.name(GENERATED_IMAGE_BASENAME + UUID.randomUUID() + PNG_EXTENSION).build();
		LLMGeneratedResource resource = chatStorageAreaService.addMedia(media, sessionCode);
		response.getGeneratedResources().add(resource);
		response.setQueryResponse("Here is the image I generated for your request:\r\n\r\n> " + imagePrompt);
	}

	private byte[] extractImageBytes(Image image) throws IOException {
		if (image.getB64Json() != null && !image.getB64Json().isBlank()) {
			return Base64.getDecoder().decode(image.getB64Json());
		}
		if (image.getUrl() != null && !image.getUrl().isBlank()) {
			try (InputStream is = URI.create(image.getUrl()).toURL().openStream()) {
				return IOUtils.toByteArray(is);
			}
		}
		return null;
	}

	private String craftImagePrompt(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel serviceModel) {
		String actualQuery = GeboChatRequest.actualQuery(runtimeData.getRequestResources().getCurrentRequest());
		try {
			GPromptTemplateConfig prompt = promptsDao
					.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_IMAGE_PROMPT_CRAFTING_PROMPT);
			Map<String, Object> params = CommonChatPromptParamsUtil
					.preparePromptParameters(runtimeData.getMinimalChatContext());
			params.put(DefaultRoutingChatPipelineStepServiceImpl.REWRITTEN_QUERY_TEMPLATE_PARAM, actualQuery);
			IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
			Map<String, List<String>> data = callLLMRepeatableFieldEntryOutput(serviceModel, prompt, context, params,
					List.of(IMAGE_PROMPT_FIELD));
			List<String> crafted = data.get(IMAGE_PROMPT_FIELD);
			if (crafted != null && !crafted.isEmpty() && crafted.get(0) != null && !crafted.get(0).isBlank()) {
				return crafted.get(0);
			}
		} catch (Throwable th) {
			LOGGER.error("Error crafting image prompt, falling back to the raw user query", th);
		}
		return actualQuery;
	}

}
