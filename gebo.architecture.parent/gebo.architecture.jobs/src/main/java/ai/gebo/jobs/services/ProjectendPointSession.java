package ai.gebo.jobs.services;

import ai.gebo.knlowledgebase.model.projects.AbstractContentConsumingSessionParam;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import lombok.Data;

@Data
public class ProjectendPointSession<EndpointType extends GProjectEndpoint, SessionParamType extends AbstractContentConsumingSessionParam> {
	private EndpointType endpoint;
	private SessionParamType sessionParam;
}