package ai.gebo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.Oauth2ModuleStatus;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/Oauth2ModuleStatusController")
public class Oauth2ModuleStatusController {
	@Autowired
	GeboSecurityConfig geboSecurityConfig;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2ModuleStatus getStatus() {
		return new Oauth2ModuleStatus(
				geboSecurityConfig.getOauth2UISetupEnabled() != null && geboSecurityConfig.getOauth2UISetupEnabled(),
				geboSecurityConfig.getOauth2Enabled() != null && geboSecurityConfig.getOauth2UISetupEnabled());
	}
}
