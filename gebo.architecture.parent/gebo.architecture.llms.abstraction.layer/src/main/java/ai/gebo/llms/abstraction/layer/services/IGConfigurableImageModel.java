package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;

public interface IGConfigurableImageModel<ModelConfig extends GBaseImageModelConfig>
		extends IGConfigurableModel<ModelConfig, GImageModelType> {
		public ImageModel getImageModel();
		public default ImageResponse call(ImagePrompt request) {
			return getImageModel().call(request);
		}
}
