package ai.gebo.llms.chat.abstraction.layer.services;

import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import reactor.core.publisher.Flux;

public interface IAISerchDataSource {
	public Flux<Document> search(IChatRequestContext context, ISinkUIEmitter emitter, int topK);
}
