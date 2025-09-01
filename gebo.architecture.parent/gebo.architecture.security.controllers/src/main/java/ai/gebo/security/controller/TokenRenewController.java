package ai.gebo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN','APPLICATION')")
@RequestMapping("/api/users/TokenRenewController")
public class TokenRenewController {
	@Autowired
	LocalJwtTokenProvider tokenProvider;
	@GetMapping(value = "renew",produces = MediaType.APPLICATION_JSON_VALUE)
	public SecurityHeaderData renew(HttpServletRequest request) {
		SecurityHeaderData data = SecurityHeaderUtil.getSecurityHeaderData(request);
		return tokenProvider.renew(data);
	}
}
