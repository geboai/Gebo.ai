package ai.gebo.architecture.documents.cache.service;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGTimedOutMessageReceiverFactory;

public interface IChunkingMessagingComponent extends IGMessageEmitter,IGTimedOutMessageReceiverFactory{
	
}
