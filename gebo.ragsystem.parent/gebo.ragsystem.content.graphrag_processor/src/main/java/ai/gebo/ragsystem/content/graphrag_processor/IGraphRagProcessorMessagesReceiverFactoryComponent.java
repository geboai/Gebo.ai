package ai.gebo.ragsystem.content.graphrag_processor;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGTimedOutMessageReceiverFactory;

public interface IGraphRagProcessorMessagesReceiverFactoryComponent extends IGMessageEmitter,IGTimedOutMessageReceiverFactory{
	
}
