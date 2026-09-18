package ai.gebo.architecture.neo4j.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.neo4j.GeboNeo4jModuleConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GeboNeo4jModuleSetupController")
@AllArgsConstructor
public class GeboNeo4jModuleSetupController {
	private final GeboNeo4jModuleConfig moduleConfig;

	@AllArgsConstructor
	@Getter
	public static class GeboNeo4jModuleConfigDto {
		private final boolean enabled;

		public static GeboNeo4jModuleConfigDto of(GeboNeo4jModuleConfig c) {
			return new GeboNeo4jModuleConfigDto(c.isEnabled());
		}
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboNeo4jModuleConfigDto getNeo4jModuleSetupConfig() {
		return GeboNeo4jModuleConfigDto.of(moduleConfig);
	}

}
