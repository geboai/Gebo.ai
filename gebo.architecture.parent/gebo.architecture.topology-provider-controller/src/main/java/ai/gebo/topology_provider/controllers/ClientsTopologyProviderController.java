package ai.gebo.topology_provider.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.environment.GeboClientsTopologyInfo;
import ai.gebo.architecture.environment.IGClientsTopologyProvider;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/public/ClientsTopologyProviderController")
@AllArgsConstructor
public class ClientsTopologyProviderController {
	private final IGClientsTopologyProvider topologyProvider;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboClientsTopologyInfo getClientsTopology() {
		return topologyProvider.getTopology();
	}

}
