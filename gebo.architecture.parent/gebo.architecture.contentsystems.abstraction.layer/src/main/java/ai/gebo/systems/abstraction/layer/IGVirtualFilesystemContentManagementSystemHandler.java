package ai.gebo.systems.abstraction.layer;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

public interface IGVirtualFilesystemContentManagementSystemHandler<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
		extends
		IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType, RemoteVirtualFileSystemContentConsumingSessionParam> {

}
