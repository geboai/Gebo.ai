package ai.gebo.architecture.agents.services;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.SearchAgentCommand;

public interface IGDocumentsSearchNetworkAgentService extends IGNetworkAgentService<SearchAgentCommand, List<Document>> {

}
